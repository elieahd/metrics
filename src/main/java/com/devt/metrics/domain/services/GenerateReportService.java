package com.devt.metrics.domain.services;

import com.devt.metrics.domain.inbound.GenerateReport;
import com.devt.metrics.domain.inbound.ReportCommandRequest;
import com.devt.metrics.domain.models.entities.Pipeline;
import com.devt.metrics.domain.models.entities.Project;
import com.devt.metrics.domain.models.entities.PullRequest;
import com.devt.metrics.domain.models.entities.Release;
import com.devt.metrics.domain.models.entities.Repository;
import com.devt.metrics.domain.models.metrics.ProjectMetric;
import com.devt.metrics.domain.models.reports.Report;
import com.devt.metrics.domain.outbound.PdfGenerator;
import com.devt.metrics.domain.outbound.PipelineInventory;
import com.devt.metrics.domain.outbound.ProjectInventory;
import com.devt.metrics.domain.outbound.PullRequestInventory;
import com.devt.metrics.domain.outbound.ReleaseInventory;
import com.devt.metrics.domain.outbound.ReportInventory;
import com.devt.metrics.domain.services.metrics.MetricCalculator;
import com.devt.metrics.domain.services.metrics.ProjectMetricCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@DomainService
public class GenerateReportService implements GenerateReport {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenerateReportService.class);
    private static final String TEMPLATE_NAME = "metrics-report";

    private final PullRequestInventory pullRequestInventory;
    private final ReleaseInventory releaseInventory;
    private final PipelineInventory pipelineInventory;
    private final ProjectInventory projectInventory;
    private final MetricCalculator<Project, ProjectMetric> metricCalculator;
    private final PdfGenerator pdfGenerator;
    private final ReportInventory reportInventory;

    public GenerateReportService(PullRequestInventory pullRequestInventory,
                                 ReleaseInventory releaseInventory,
                                 PipelineInventory pipelineInventory,
                                 ProjectInventory projectInventory,
                                 PdfGenerator pdfGenerator,
                                 ReportInventory reportInventory) {
        this.pullRequestInventory = pullRequestInventory;
        this.releaseInventory = releaseInventory;
        this.pipelineInventory = pipelineInventory;
        this.projectInventory = projectInventory;
        this.metricCalculator = new ProjectMetricCalculator();
        this.pdfGenerator = pdfGenerator;
        this.reportInventory = reportInventory;
    }

    @Override
    public void perform(ReportCommandRequest reportCommandRequest) {

        String projectName = reportCommandRequest.projectName();
        List<String> repositoryNames = reportCommandRequest.repositoryNames();

        Project project = projectInventory
                .findByName(projectName)
                .orElseGet(() -> fetchRepositoriesAndStoreProject(projectName, repositoryNames));
        LOGGER.info("Project '{}' loaded successfully!", projectName);

        LOGGER.info("Calculating metrics...");
        ProjectMetric metrics = metricCalculator.apply(project);

        LOGGER.info("Generating report...", projectName);
        Report report = generateReport(projectName, metrics);

        String reportPath = reportInventory.store(report);
        LOGGER.info("Report stored under '{}'!", reportPath);
    }

    private Report generateReport(String projectName, ProjectMetric projectMetrics) {
        Map<String, Object> data = Map.of("project", projectMetrics);
        byte[] content = pdfGenerator.generate(TEMPLATE_NAME, data);
        return new Report(projectName, "pdf", content);
    }

    private Project fetchRepositoriesAndStoreProject(String projectName, List<String> repositoryNames) {
        LOGGER.info("Fetching repositories...");
        List<Repository> repositories = new ArrayList<>(repositoryNames.size());
        for (int i = 0; i < repositoryNames.size(); i++) {
            String repositoryName = repositoryNames.get(i);
            LOGGER.info("  → Fetching repository {}/{} : '{}'...", i + 1, repositoryNames.size(), repositoryName);
            Repository repository = fetchRepository(repositoryName);
            repositories.add(repository);
        }
        Project project = new Project(
                projectName,
                repositories
        );
        projectInventory.store(project);
        return project;
    }

    private Repository fetchRepository(String repositoryName) {
        List<PullRequest> pullRequests = pullRequestInventory.findAllByRepository(repositoryName);
        LOGGER.info("    → {} PRs found", pullRequests.size());
        List<Release> releases = releaseInventory.findAllByRepository(repositoryName);
        LOGGER.info("    → {} releases found", releases.size());
        List<Pipeline> pipelines = pipelineInventory.findAllByRepository(repositoryName);
        LOGGER.info("    → {} pipelines found", pipelines.size());
        return new Repository(
                repositoryName,
                pullRequests,
                releases,
                pipelines
        );
    }
}

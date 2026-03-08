package com.devt.metrics.domain.services;

import com.devt.metrics.domain.inbound.GenerateReport;
import com.devt.metrics.domain.models.Metric;
import com.devt.metrics.domain.models.Project;
import com.devt.metrics.domain.models.ProjectContext;
import com.devt.metrics.domain.models.PullRequest;
import com.devt.metrics.domain.models.Release;
import com.devt.metrics.domain.models.Repository;
import com.devt.metrics.domain.outbound.PdfGenerator;
import com.devt.metrics.domain.outbound.PullRequestsInventory;
import com.devt.metrics.domain.outbound.ReleasesInventory;
import com.devt.metrics.domain.outbound.ReportInventory;
import com.devt.metrics.domain.services.metrics.MetricCalculators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@DomainService
public class GenerateReportService implements GenerateReport {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenerateReportService.class);
    private static final String REPORT_TEMPLATE = "metrics-report";
    private static final String REPORT_OUTPUT_FOLDER = "reports";

    private final PullRequestsInventory prsInventory;
    private final ReleasesInventory releasesInventory;
    private final MetricCalculators calculators;
    private final PdfGenerator pdfGenerator;
    private final ReportInventory reportInventory;

    public GenerateReportService(PullRequestsInventory prsInventory,
                                 ReleasesInventory releasesInventory,
                                 MetricCalculators calculators,
                                 PdfGenerator pdfGenerator,
                                 ReportInventory reportInventory) {
        this.prsInventory = prsInventory;
        this.releasesInventory = releasesInventory;
        this.calculators = calculators;
        this.pdfGenerator = pdfGenerator;
        this.reportInventory = reportInventory;
    }

    @Override
    public void perform(Project project) {
        ProjectContext projectContext = loadProjectRepositories(project);

        List<Metric> metrics = calculateMetrics(projectContext);

        byte[] pdf = generatePdf(projectContext, metrics);

        storeReport(projectContext.name(), pdf);
    }

    private ProjectContext loadProjectRepositories(Project project) {
        LOGGER.info("Fetching context for project '{}'", project.name());
        List<Repository> repositories = project.repositories()
                .stream()
                .map(this::loadRepository)
                .toList();
        return new ProjectContext(
                project.name(),
                repositories
        );
    }

    private Repository loadRepository(String repositoryName) {
        LOGGER.info("Loading context for '{}'...", repositoryName);
        List<PullRequest> pullRequests = prsInventory.findAllByRepository(repositoryName);
        LOGGER.info("  → {} PR(s) found", pullRequests.size());
        List<Release> releases = releasesInventory.findAllByRepository(repositoryName);
        LOGGER.info("  → {} release(s) found", releases.size());
        return new Repository(
                repositoryName,
                pullRequests,
                releases
        );
    }

    private List<Metric> calculateMetrics(ProjectContext projectContext) {
        return new ArrayList<>();
    }

    private byte[] generatePdf(ProjectContext projectContext, List<Metric> metrics) {
        LOGGER.info("Generating report of type '{}'...", REPORT_TEMPLATE);
        Map<String, Object> data = Map.of(
                "project", projectContext,
                "metrics", metrics
        );
        return pdfGenerator.generate(REPORT_TEMPLATE, data);
    }

    private void storeReport(String projectName, byte[] pdf) {
        String fileName = "%s-%s.pdf".formatted(REPORT_TEMPLATE, projectName);
        String name = "%s/%s".formatted(REPORT_OUTPUT_FOLDER, fileName);
        String path = reportInventory.store(name, pdf);
        LOGGER.info("Report '{}' stored under '{}'...", REPORT_TEMPLATE, path);
    }
}

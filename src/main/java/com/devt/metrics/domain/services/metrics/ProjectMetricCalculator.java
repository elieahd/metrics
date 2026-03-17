package com.devt.metrics.domain.services.metrics;

import com.devt.metrics.domain.models.entities.Project;
import com.devt.metrics.domain.models.entities.Repository;
import com.devt.metrics.domain.models.metrics.ContributionsMetrics;
import com.devt.metrics.domain.models.metrics.DeploymentMetric;
import com.devt.metrics.domain.models.metrics.ProjectMetric;
import com.devt.metrics.domain.models.metrics.RepositoryMetric;
import com.devt.metrics.domain.services.DomainService;

import java.util.List;

@DomainService
public class ProjectMetricCalculator implements MetricCalculator<Project, ProjectMetric> {

    private final MetricCalculator<Repository, RepositoryMetric> repositoriesCalculator;
    private final MetricCalculator<List<Repository>, ContributionsMetrics> contributorsCalculator;
    private final MetricCalculator<List<RepositoryMetric>, DeploymentMetric> deploymentsCalculator;

    public ProjectMetricCalculator() {
        this.repositoriesCalculator = new RepositoryMetricCalculator();
        this.contributorsCalculator = new ContributionMetricCalculator();
        this.deploymentsCalculator = new DeploymentMetricCalculator();
    }

    @Override
    public ProjectMetric apply(Project project) {
        List<RepositoryMetric> repositories = project.repositories()
                .stream()
                .map(repositoriesCalculator::apply)
                .toList();
        ContributionsMetrics contributions = contributorsCalculator.apply(project.repositories());
        DeploymentMetric deployments = deploymentsCalculator.apply(repositories);
        return new ProjectMetric(
                project.name(),
                repositories,
                contributions,
                deployments
        );
    }

}

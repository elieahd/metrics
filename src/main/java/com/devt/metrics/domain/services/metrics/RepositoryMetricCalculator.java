package com.devt.metrics.domain.services.metrics;

import com.devt.metrics.domain.models.entities.Pipeline;
import com.devt.metrics.domain.models.entities.Release;
import com.devt.metrics.domain.models.entities.Repository;
import com.devt.metrics.domain.models.metrics.PipelineMetric;
import com.devt.metrics.domain.models.metrics.ReleaseMetric;
import com.devt.metrics.domain.models.metrics.RepositoryMetric;

import java.util.List;

public class RepositoryMetricCalculator implements MetricCalculator<Repository, RepositoryMetric> {

    private final MetricCalculator<Pipeline, PipelineMetric> pipelineCalculator;
    private final MetricCalculator<Release, ReleaseMetric> releaseCalculator;

    public RepositoryMetricCalculator() {
        this.pipelineCalculator = new PipelineMetricCalculator();
        this.releaseCalculator = new ReleaseMetricCalculator();
    }

    @Override
    public RepositoryMetric apply(Repository repository) {

        var totalPRs = repository.pullRequests().size();
        var totalReleases = repository.releases().size();
        var totalPipelines = repository.pipelines().size();

        List<ReleaseMetric> releases = repository.releases()
                .stream()
                .map(releaseCalculator::apply)
                .toList();

        List<PipelineMetric> pipelines = repository.pipelines()
                .stream()
                .map(pipelineCalculator::apply)
                .toList();

        return new RepositoryMetric(
                repository.name(),
                totalPRs,
                totalReleases,
                totalPipelines,
                releases,
                pipelines
        );
    }

}

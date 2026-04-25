package com.devt.metrics.domain.models.metrics;

import java.util.List;

public record RepositoryMetric(String name,
                               long totalPRs,
                               long totalReleases,
                               long totalPipelines,
                               List<ReleaseMetric> releases,
                               List<PipelineMetric> pipelines,
                               PullRequestMetric pullRequests) {
}

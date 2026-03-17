package com.devt.metrics.domain.models.metrics;

import java.util.List;

public record RepositoryMetric(String name,
                               int totalPRs,
                               int totalReleases,
                               int totalPipelines,
                               List<ReleaseMetric> releases,
                               List<PipelineMetric> pipelines,
                               PullRequestMetric pullRequests) {
}

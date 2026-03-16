package com.devt.metrics.domain.models.metrics;

import com.devt.metrics.domain.models.entities.PullRequest;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public record ContributorMetricAccumulator(List<PullRequest> pullRequests,
                                           int reviewCount) {

    public ContributorMetricAccumulator() {
        this(new ArrayList<>(), 0);
    }

    public ContributorMetricAccumulator addPr(PullRequest pullRequest) {
        pullRequests.add(pullRequest);
        return this;
    }

    public ContributorMetricAccumulator addReview() {
        return new ContributorMetricAccumulator(pullRequests, reviewCount + 1);
    }

    public ContributorMetric toMetric(String contributorName,
                                      OffsetDateTime cutoff) {

        List<PullRequest> sortedPRs = pullRequests.stream()
                .filter(pr -> pr.mergedAt() != null)
                .sorted(Comparator.comparing(PullRequest::mergedAt))
                .toList();

        if (sortedPRs.isEmpty()) {
            return new ContributorMetric(contributorName, 0, reviewCount, null, null, false, null);
        }

        return new ContributorMetric(
                contributorName,
                sortedPRs.size(),
                reviewCount,
                sortedPRs.getFirst().mergedAt(),
                sortedPRs.getLast().mergedAt(),
                sortedPRs.getLast().mergedAt().isAfter(cutoff),
                sortedPRs.size() >= 10 ? Duration.between(sortedPRs.getFirst().mergedAt(), sortedPRs.get(9).mergedAt()) : null
        );
    }
}
package com.devt.metrics.domain.models.metrics;

import com.devt.metrics.domain.models.entities.PullRequest;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public record CollaboratorMetricAccumulator(List<PullRequest> pullRequests,
                                            int reviewCount) {

    public CollaboratorMetricAccumulator() {
        this(new ArrayList<>(), 0);
    }

    public CollaboratorMetricAccumulator addPr(PullRequest pullRequest) {
        pullRequests.add(pullRequest);
        return this;
    }

    public CollaboratorMetricAccumulator addReview() {
        return new CollaboratorMetricAccumulator(pullRequests, reviewCount + 1);
    }

    public CollaboratorMetric toMetric(String collaboratorName,
                                       OffsetDateTime cutoff) {

        List<PullRequest> sortedPRs = pullRequests.stream()
                .filter(pr -> pr.mergedAt() != null)
                .sorted(Comparator.comparing(PullRequest::mergedAt))
                .toList();

        if (sortedPRs.isEmpty()) {
            return new CollaboratorMetric(collaboratorName, 0, reviewCount, null, null, false, null);
        }

        return new CollaboratorMetric(
                collaboratorName,
                sortedPRs.size(),
                reviewCount,
                sortedPRs.getFirst().mergedAt(),
                sortedPRs.getLast().mergedAt(),
                sortedPRs.getLast().mergedAt().isAfter(cutoff),
                sortedPRs.size() >= 10 ? Duration.between(sortedPRs.getFirst().mergedAt(), sortedPRs.get(9).mergedAt()) : null
        );
    }
}
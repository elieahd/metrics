package com.devt.metrics.domain.services.metrics;

import com.devt.metrics.domain.models.entities.PullRequest;
import com.devt.metrics.domain.models.entities.PullRequestReview;
import com.devt.metrics.domain.models.metrics.PullRequestMetric;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class PullRequestMetricCalculator implements MetricCalculator<List<PullRequest>, PullRequestMetric> {

    @Override
    public PullRequestMetric apply(List<PullRequest> pullRequests) {

        if (pullRequests == null || pullRequests.isEmpty()) {
            return new PullRequestMetric(0, 0, 0, 0, Duration.ZERO, Duration.ZERO);
        }

        int totalPullRequests = 0;
        int totalOpenedPullRequests = 0;
        int totalMergedPullRequests = 0;
        int totalClosedPullRequests = 0;

        Duration totalReviewTurnAroundTime = Duration.ZERO;
        int reviewTurnAroundCount = 0;

        Duration totalCycleTime = Duration.ZERO;
        int cycleTimeCount = 0;

        for (PullRequest pullRequest : pullRequests) {
            totalPullRequests++;

            if (isOpen(pullRequest)) {
                totalOpenedPullRequests++;
            }

            if (isMerged(pullRequest)) {
                totalMergedPullRequests++;
            }

            if (isClosed(pullRequest)) {
                totalClosedPullRequests++;
            }

            Optional<Duration> cycleTime = cycleTime(pullRequest);
            if (cycleTime.isPresent()) {
                totalCycleTime = totalCycleTime.plus(cycleTime.get());
                cycleTimeCount++;
            }

            Optional<Duration> reviewTime = reviewTime(pullRequest);
            if (reviewTime.isPresent()) {
                totalReviewTurnAroundTime = totalReviewTurnAroundTime.plus(reviewTime.get());
                reviewTurnAroundCount++;
            }
        }

        int reallyClosedPullRequests = totalClosedPullRequests - totalMergedPullRequests;

        return new PullRequestMetric(
                totalPullRequests,
                totalOpenedPullRequests,
                totalMergedPullRequests,
                reallyClosedPullRequests,
                reviewTurnAroundCount > 0
                        ? totalReviewTurnAroundTime.dividedBy(reviewTurnAroundCount)
                        : Duration.ZERO,
                cycleTimeCount > 0
                        ? totalCycleTime.dividedBy(cycleTimeCount)
                        : Duration.ZERO
        );
    }

    private boolean isClosed(PullRequest pullRequest) {
        return pullRequest.closedAt() != null;
    }

    private boolean isMerged(PullRequest pullRequest) {
        return pullRequest.mergedAt() != null;
    }

    private boolean isOpen(PullRequest pullRequest) {
        return pullRequest.mergedAt() == null && pullRequest.closedAt() == null;
    }

    private Optional<Duration> cycleTime(PullRequest pullRequest) {
        if (pullRequest.publishedAt() == null) {
            return Optional.empty();
        }

        OffsetDateTime endDate = pullRequest.mergedAt() != null
                ? pullRequest.mergedAt()
                : pullRequest.closedAt();

        if (endDate == null) {
            return Optional.empty();
        }
        return Optional.of(Duration.between(pullRequest.publishedAt(), endDate));
    }

    private Optional<Duration> reviewTime(PullRequest pullRequest) {

        if (pullRequest.mergedAt() == null || pullRequest.reviews() == null || pullRequest.reviews().isEmpty()) {
            return Optional.empty();
        }

        return pullRequest.reviews()
                .stream()
                .filter(review -> review.user() != null && !review.user().equals(pullRequest.author()))
                .min(Comparator.comparing(PullRequestReview::submittedAt))
                .map(firstReview -> Duration.between(firstReview.submittedAt(), pullRequest.mergedAt()));
    }

}

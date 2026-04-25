package com.devt.metrics.domain.services.metrics;

import com.devt.metrics.domain.models.entities.PullRequest;
import com.devt.metrics.domain.models.levels.PullRequestCategory;
import com.devt.metrics.domain.models.metrics.PullRequestMetric;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class PullRequestMetricCalculator implements MetricCalculator<List<PullRequest>, PullRequestMetric> {

    @Override
    public PullRequestMetric apply(List<PullRequest> pullRequests) {

        if (pullRequests == null || pullRequests.isEmpty()) {
            return PullRequestMetric.empty();
        }

        long totalPullRequests = 0;
        long totalOpenedPullRequests = 0;
        long totalMergedPullRequests = 0;
        long totalClosedPullRequests = 0;

        Duration totalReviewTurnAroundTime = Duration.ZERO;
        long reviewTurnAroundCount = 0;

        Duration totalCycleTime = Duration.ZERO;
        long cycleTimeCount = 0;

        for (PullRequest pullRequest : pullRequests) {
            totalPullRequests++;

            if (pullRequest.isOpen()) {
                totalOpenedPullRequests++;
            }

            if (pullRequest.isMerged()) {
                totalMergedPullRequests++;
            }

            if (pullRequest.isClosed()) {
                totalClosedPullRequests++;
            }

            Optional<Duration> cycleTime = pullRequest.cycleTime();
            if (cycleTime.isPresent()) {
                totalCycleTime = totalCycleTime.plus(cycleTime.get());
                cycleTimeCount++;
            }

            Optional<Duration> reviewTime = pullRequest.reviewTime();
            if (reviewTime.isPresent()) {
                totalReviewTurnAroundTime = totalReviewTurnAroundTime.plus(reviewTime.get());
                reviewTurnAroundCount++;
            }
        }

        long reallyClosedPullRequests = totalClosedPullRequests - totalMergedPullRequests;

        Map<PullRequestCategory, Long> categories = pullRequests
                .stream()
                .filter(PullRequest::isMerged)
                .map(PullRequestCategory::map)
                .collect(Collectors.groupingBy(category -> category, Collectors.counting()));

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
                        : Duration.ZERO,
                categories
        );
    }

}

package com.devt.metrics.domain.models.metrics;

import com.devt.metrics.domain.models.levels.PullRequestCategory;
import com.devt.metrics.domain.services.helper.Formatter;

import java.time.Duration;
import java.util.Map;

public record PullRequestMetric(long totalPullRequests,
                                long totalOpenedPullRequests,
                                long totalMergedPullRequests,
                                long totalClosedPullRequests,
                                Duration reviewTurnAroundTime,
                                Duration cycleTime,
                                Map<PullRequestCategory, Long> categories) {

    public static PullRequestMetric empty() {
        return new PullRequestMetric(0, 0, 0, 0, Duration.ZERO, Duration.ZERO, Map.of());
    }

    public String cycleTimeFormatted() {
        return Formatter.format(cycleTime);
    }

    public String reviewTurnAroundTimeFormatted() {
        return Formatter.format(reviewTurnAroundTime);
    }

}

package com.devt.metrics.domain.models.metrics;

import com.devt.metrics.domain.services.helper.Formatter;

import java.time.Duration;

public record PullRequestMetric(int totalPullRequests,
                                int totalOpenedPullRequests,
                                int totalMergedPullRequests,
                                int totalClosedPullRequests,
                                Duration reviewTurnAroundTime,
                                Duration cycleTime) {

    public String cycleTimeFormatted() {
        return Formatter.format(cycleTime);
    }

    public String reviewTurnAroundTimeFormatted() {
        return Formatter.format(reviewTurnAroundTime);
    }

}

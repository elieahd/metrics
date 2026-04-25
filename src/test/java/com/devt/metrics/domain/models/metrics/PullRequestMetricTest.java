package com.devt.metrics.domain.models.metrics;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PullRequestMetricTest {

    @Test
    void empty_shouldInitializeWithValues() {
        // Act
        PullRequestMetric sut = PullRequestMetric.empty();
        // Arrange
        assertThat(sut.totalPullRequests()).isZero();
        assertThat(sut.totalOpenedPullRequests()).isZero();
        assertThat(sut.totalMergedPullRequests()).isZero();
        assertThat(sut.totalClosedPullRequests()).isZero();
        assertThat(sut.reviewTurnAroundTime()).isZero();
        assertThat(sut.cycleTime()).isZero();
        assertThat(sut.categories()).isEmpty();
    }
}

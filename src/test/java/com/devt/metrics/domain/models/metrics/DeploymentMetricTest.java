package com.devt.metrics.domain.models.metrics;

import com.devt.metrics.domain.models.levels.CFRLevel;
import com.devt.metrics.domain.models.levels.DeploymentFrequencyLevel;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DeploymentMetricTest {

    @Test
    void empty_shouldInitializeValues() {
        // Act
        DeploymentMetric sut = DeploymentMetric.empty();
        // Arrange
        assertThat(sut.totalPlanned()).isZero();
        assertThat(sut.totalHotfixes()).isZero();
        assertThat(sut.totalDeployments()).isZero();
        assertThat(sut.hotfixRatio()).isZero();
        assertThat(sut.cfrMetric()).isNotNull();
        assertThat(sut.cfrMetric().value()).isZero();
        assertThat(sut.cfrMetric().level()).isEqualTo(CFRLevel.LOW);
        assertThat(sut.frequencyMetric()).isNotNull();
        assertThat(sut.frequencyMetric().average()).isZero();
        assertThat(sut.frequencyMetric().frequencies()).isEmpty();
        assertThat(sut.frequencyMetric().level()).isEqualTo(DeploymentFrequencyLevel.LOW);
    }
}

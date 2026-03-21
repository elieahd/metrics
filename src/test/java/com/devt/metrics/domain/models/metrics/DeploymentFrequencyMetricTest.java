package com.devt.metrics.domain.models.metrics;

import com.devt.metrics.domain.models.levels.DeploymentFrequencyLevel;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.devt.metrics.domain.models.ModelRandomizer.aDeploymentFrequencyEntry;
import static com.devt.metrics.domain.models.ModelRandomizer.aDeploymentFrequencyMetric;
import static com.devt.randomizer.RandomizerUtils.random;
import static org.assertj.core.api.Assertions.assertThat;

class DeploymentFrequencyMetricTest {

    @Test
    void isElite_shouldReturnTrue_whenLevelIsElite() {
        // Arrange
        DeploymentFrequencyMetric sut = aDeploymentFrequencyMetric(DeploymentFrequencyLevel.ELITE);
        // Act
        boolean isElite = sut.isElite();
        // Assert
        assertThat(isElite).isTrue();
    }

    @Test
    void isElite_shouldReturnFalse_whenLevelIsHigh() {
        // Arrange
        DeploymentFrequencyMetric sut = aDeploymentFrequencyMetric(DeploymentFrequencyLevel.HIGH);
        // Act
        boolean isElite = sut.isElite();
        // Assert
        assertThat(isElite).isFalse();
    }

    @Test
    void isElite_shouldReturnFalse_whenLevelIsMedium() {
        // Arrange
        DeploymentFrequencyMetric sut = aDeploymentFrequencyMetric(DeploymentFrequencyLevel.MEDIUM);
        // Act
        boolean isElite = sut.isElite();
        // Assert
        assertThat(isElite).isFalse();
    }

    @Test
    void isElite_shouldReturnFalse_whenLevelIsLow() {
        // Arrange
        DeploymentFrequencyMetric sut = aDeploymentFrequencyMetric(DeploymentFrequencyLevel.LOW);
        // Act
        boolean isElite = sut.isElite();
        // Assert
        assertThat(isElite).isFalse();
    }

    @Test
    void isHigh_shouldReturnTrue_whenLevelIsHigh() {
        // Arrange
        DeploymentFrequencyMetric sut = aDeploymentFrequencyMetric(DeploymentFrequencyLevel.HIGH);
        // Act
        boolean isHigh = sut.isHigh();
        // Assert
        assertThat(isHigh).isTrue();
    }

    @Test
    void isHigh_shouldReturnFalse_whenLevelIsElite() {
        // Arrange
        DeploymentFrequencyMetric sut = aDeploymentFrequencyMetric(DeploymentFrequencyLevel.ELITE);
        // Act
        boolean isHigh = sut.isHigh();
        // Assert
        assertThat(isHigh).isFalse();
    }

    @Test
    void isHigh_shouldReturnFalse_whenLevelIsMedium() {
        // Arrange
        DeploymentFrequencyMetric sut = aDeploymentFrequencyMetric(DeploymentFrequencyLevel.MEDIUM);
        // Act
        boolean isHigh = sut.isHigh();
        // Assert
        assertThat(isHigh).isFalse();
    }

    @Test
    void isHigh_shouldReturnFalse_whenLevelIsLow() {
        // Arrange
        DeploymentFrequencyMetric sut = aDeploymentFrequencyMetric(DeploymentFrequencyLevel.LOW);
        // Act
        boolean isHigh = sut.isHigh();
        // Assert
        assertThat(isHigh).isFalse();
    }

    @Test
    void isMedium_shouldReturnTrue_whenLevelIsMedium() {
        // Arrange
        DeploymentFrequencyMetric sut = aDeploymentFrequencyMetric(DeploymentFrequencyLevel.MEDIUM);
        // Act
        boolean isMedium = sut.isMedium();
        // Assert
        assertThat(isMedium).isTrue();
    }

    @Test
    void isMedium_shouldReturnFalse_whenLevelIsElite() {
        // Arrange
        DeploymentFrequencyMetric sut = aDeploymentFrequencyMetric(DeploymentFrequencyLevel.ELITE);
        // Act
        boolean isMedium = sut.isMedium();
        // Assert
        assertThat(isMedium).isFalse();
    }

    @Test
    void isMedium_shouldReturnFalse_whenLevelIsHigh() {
        // Arrange
        DeploymentFrequencyMetric sut = aDeploymentFrequencyMetric(DeploymentFrequencyLevel.HIGH);
        // Act
        boolean isMedium = sut.isMedium();
        // Assert
        assertThat(isMedium).isFalse();
    }

    @Test
    void isMedium_shouldReturnFalse_whenLevelIsLow() {
        // Arrange
        DeploymentFrequencyMetric sut = aDeploymentFrequencyMetric(DeploymentFrequencyLevel.LOW);
        // Act
        boolean isMedium = sut.isMedium();
        // Assert
        assertThat(isMedium).isFalse();
    }

    @Test
    void isLow_shouldReturnTrue_whenLevelIsLow() {
        // Arrange
        DeploymentFrequencyMetric sut = aDeploymentFrequencyMetric(DeploymentFrequencyLevel.LOW);
        // Act
        boolean isLow = sut.isLow();
        // Assert
        assertThat(isLow).isTrue();
    }

    @Test
    void isLow_shouldReturnFalse_whenLevelIsElite() {
        // Arrange
        DeploymentFrequencyMetric sut = aDeploymentFrequencyMetric(DeploymentFrequencyLevel.ELITE);
        // Act
        boolean isLow = sut.isLow();
        // Assert
        assertThat(isLow).isFalse();
    }

    @Test
    void isLow_shouldReturnFalse_whenLevelIsHigh() {
        // Arrange
        DeploymentFrequencyMetric sut = aDeploymentFrequencyMetric(DeploymentFrequencyLevel.HIGH);
        // Act
        boolean isLow = sut.isLow();
        // Assert
        assertThat(isLow).isFalse();
    }

    @Test
    void isLow_shouldReturnFalse_whenLevelIsMedium() {
        // Arrange
        DeploymentFrequencyMetric sut = aDeploymentFrequencyMetric(DeploymentFrequencyLevel.MEDIUM);
        // Act
        boolean isLow = sut.isLow();
        // Assert
        assertThat(isLow).isFalse();
    }

    @Test
    void maxDeploymentsPerPeriod_shouldReturnOne_whenFrequenciesIsEmpty() {
        // Arrange
        DeploymentFrequencyMetric sut = new DeploymentFrequencyMetric(random(double.class), List.of(), random(DeploymentFrequencyLevel.class));
        // Act
        int maxDeployments = sut.maxDeploymentsPerPeriod();
        // Assert
        assertThat(maxDeployments).isEqualTo(1);
    }

    @Test
    void maxDeploymentsPerPeriod_shouldReturnCount_whenFrequenciesHasOneEntry() {
        // Arrange
        DeploymentFrequencyMetric sut = aDeploymentFrequencyMetric(
                aDeploymentFrequencyEntry(5)
        );
        // Act
        int maxDeployments = sut.maxDeploymentsPerPeriod();
        // Assert
        assertThat(maxDeployments).isEqualTo(5);
    }

    @Test
    void maxDeploymentsPerPeriod_shouldReturnHighestCount_whenFrequenciesHasMultipleEntries() {
        // Arrange
        DeploymentFrequencyMetric sut = aDeploymentFrequencyMetric(
                aDeploymentFrequencyEntry(3),
                aDeploymentFrequencyEntry(7),
                aDeploymentFrequencyEntry(2)
        );
        // Act
        int maxDeployments = sut.maxDeploymentsPerPeriod();
        // Assert
        assertThat(maxDeployments).isEqualTo(7);
    }

    @Test
    void maxDeploymentsPerPeriod_shouldReturnOne_whenAllEntriesHaveZeroCount() {
        // Arrange
        DeploymentFrequencyMetric sut = aDeploymentFrequencyMetric(
                aDeploymentFrequencyEntry(0),
                aDeploymentFrequencyEntry(0)
        );
        // Act
        int maxDeployments = sut.maxDeploymentsPerPeriod();
        // Assert
        assertThat(maxDeployments).isZero();
    }
}
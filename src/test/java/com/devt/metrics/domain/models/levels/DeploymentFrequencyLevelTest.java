package com.devt.metrics.domain.models.levels;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class DeploymentFrequencyLevelTest {

    static Stream<Arguments> deploymentFrequencyLevelProvider() {
        return Stream.of(
                // ELITE (average > 90)
                Arguments.of(90.1, DeploymentFrequencyLevel.ELITE),
                Arguments.of(91.0, DeploymentFrequencyLevel.ELITE),
                Arguments.of(200.0, DeploymentFrequencyLevel.ELITE),

                // HIGH (13 < average <= 90)
                Arguments.of(90.0, DeploymentFrequencyLevel.HIGH),
                Arguments.of(50.0, DeploymentFrequencyLevel.HIGH),
                Arguments.of(13.1, DeploymentFrequencyLevel.HIGH),

                // MEDIUM (3 < average <= 13)
                Arguments.of(13.0, DeploymentFrequencyLevel.MEDIUM),
                Arguments.of(7.0, DeploymentFrequencyLevel.MEDIUM),
                Arguments.of(3.1, DeploymentFrequencyLevel.MEDIUM),

                // LOW (average <= 3)
                Arguments.of(3.0, DeploymentFrequencyLevel.LOW),
                Arguments.of(1.0, DeploymentFrequencyLevel.LOW),
                Arguments.of(0.0, DeploymentFrequencyLevel.LOW),
                Arguments.of(-5.0, DeploymentFrequencyLevel.LOW)
        );
    }

    @ParameterizedTest
    @MethodSource("deploymentFrequencyLevelProvider")
    void of_shouldReturnLevel(double average, DeploymentFrequencyLevel expectedLevel) {
        // Act
        DeploymentFrequencyLevel result = DeploymentFrequencyLevel.of(average);
        // Assert
        assertThat(result).isEqualTo(expectedLevel);
    }
}
package com.devt.metrics.domain.services.metrics;

import com.devt.metrics.domain.models.entities.Release;
import com.devt.metrics.domain.models.metrics.ReleaseMetric;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.devt.metrics.domain.models.ModelRandomizer.aRelease;
import static org.assertj.core.api.Assertions.assertThat;

class ReleaseMetricCalculatorTest {

    private MetricCalculator<Release, ReleaseMetric> calculator;

    @BeforeEach
    void setup() {
        calculator = new ReleaseMetricCalculator();
    }

    private static Stream<Arguments> providePlannedReleases() {
        return Stream.of(
                Arguments.of("1.0.0-rc", "1.0.0"),
                Arguments.of("2.0.0-rc", "2.0.0"),
                Arguments.of("1.0.0", "1.0.0"),
                Arguments.of("1.1.0-rc", "1.1.0"),
                Arguments.of("1.1.0", "1.1.0")
        );
    }

    @ParameterizedTest
    @MethodSource("providePlannedReleases")
    void apply_shouldReturnPlannedRelease(String version, String expectedVersion) {
        // Arrange
        Release release = aRelease(version);
        // Act
        ReleaseMetric metric = calculator.apply(release);
        // Assert
        assertThat(metric.version()).isEqualTo(expectedVersion);
        assertThat(metric.isPlanned()).isTrue();
        assertThat(metric.version()).isEqualTo(expectedVersion);
    }

    private static Stream<Arguments> provideUnplannedReleases() {
        return Stream.of(
                Arguments.of("1.0.1-rc", "1.0.1"),
                Arguments.of("2.0.3-rc", "2.0.3"),
                Arguments.of("1.0.5", "1.0.5"),
                Arguments.of("1.1.7-rc", "1.1.7"),
                Arguments.of("1.1.9", "1.1.9")
        );
    }

    @ParameterizedTest
    @MethodSource("provideUnplannedReleases")
    void apply_shouldReturnUnplannedRelease(String version, String expectedVersion) {
        // Arrange
        Release release = aRelease(version);
        // Act
        ReleaseMetric metric = calculator.apply(release);
        // Assert
        assertThat(metric.version()).isEqualTo(expectedVersion);
        assertThat(metric.isPlanned()).isFalse();
        assertThat(metric.version()).isEqualTo(expectedVersion);
    }
}

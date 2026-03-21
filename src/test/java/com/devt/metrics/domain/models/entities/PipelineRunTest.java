package com.devt.metrics.domain.models.entities;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.stream.Stream;

import static com.devt.metrics.domain.models.ModelRandomizer.aPipelineRun;
import static org.assertj.core.api.Assertions.assertThat;

class PipelineRunTest {

    private static Stream<Arguments> provideDuration() {
        OffsetDateTime start = OffsetDateTime.now();
        OffsetDateTime end = start.plusMinutes(5);

        return Stream.of(
                Arguments.of(null, end, Duration.ZERO),
                Arguments.of(start, null, Duration.ZERO),
                Arguments.of(null, null, Duration.ZERO),
                Arguments.of(start, end, Duration.ofMinutes(5))
        );
    }

    @ParameterizedTest
    @MethodSource("provideDuration")
    void shouldCalculateDuration(OffsetDateTime startedAt,
                                 OffsetDateTime updatedAt,
                                 Duration expectedDuration) {
        // Arrange
        PipelineRun sut = aPipelineRun(startedAt, updatedAt);
        // Act
        Duration duration = sut.duration();
        // Assert
        assertThat(duration).isEqualTo(expectedDuration);
    }

}

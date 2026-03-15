package com.devt.metrics.domain.services.helper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Duration;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class DurationFormatterTest {

    private static Stream<Arguments> provideDurationTransformations() {
        return Stream.of(
                Arguments.of(Duration.ZERO, "0s"),
                Arguments.of(Duration.ofSeconds(45), "45s"),
                Arguments.of(Duration.ofMinutes(5).plusSeconds(30), "5min 30s"),
                Arguments.of(Duration.ofHours(23).plusMinutes(59).plusSeconds(59), "23h 59min 59s"),
                Arguments.of(Duration.ofMinutes(10), "10min 0s"),
                Arguments.of(Duration.ofHours(2).plusMinutes(15).plusSeconds(10), "2h 15min 10s"),
                Arguments.of(Duration.ofHours(3), "3h 0min 0s"),
                Arguments.of(Duration.ofDays(1).plusHours(4).plusMinutes(30).plusSeconds(20), "1d 4h 30min 20s"),
                Arguments.of(Duration.ofDays(2), "2d 0h 0min 0s"),
                Arguments.of(Duration.ofDays(5).plusHours(23).plusMinutes(59).plusSeconds(59), "5d 23h 59min 59s"),
                Arguments.of(Duration.ofMinutes(59).plusSeconds(59), "59min 59s")
        );
    }

    @ParameterizedTest
    @MethodSource("provideDurationTransformations")
    void shouldTransformDuration(Duration input, String expected) {
        // Act
        String result = Formatter.format(input);
        // Assert
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void shouldReturnBlankWhenTransformingNullDuration() {
        // Arrange
        Duration input = null;
        // Act
        String result = Formatter.format(input);
        // Assert
        assertThat(result)
                .isNotNull()
                .isBlank();
    }

}

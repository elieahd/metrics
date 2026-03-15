package com.devt.metrics.domain.services.helper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class OffsetDateTimeFormatterTest {

    private static Stream<Arguments> provideOffsetDateTimeTransformations() {
        return Stream.of(
                Arguments.of(OffsetDateTime.of(2024, 3, 1, 8, 0, 0, 0, ZoneOffset.ofHours(-8)), "2024-03-01"),
                Arguments.of(OffsetDateTime.of(2024, 12, 31, 23, 59, 59, 0, ZoneOffset.ofHours(5)), "2024-12-31"),
                Arguments.of(OffsetDateTime.of(2024, 1, 5, 0, 0, 0, 0, ZoneOffset.UTC), "2024-01-05"),
                Arguments.of(OffsetDateTime.of(2024, 6, 15, 10, 30, 0, 0, ZoneOffset.UTC), "2024-06-15")
        );
    }

    @ParameterizedTest
    @MethodSource("provideOffsetDateTimeTransformations")
    void shouldTransformOffsetDateTime(OffsetDateTime input, String expected) {
        // Act
        String result = Formatter.format(input);
        // Assert
        assertThat(result)
                .matches("\\d{4}-\\d{2}-\\d{2}")
                .isEqualTo(expected);
    }

    @Test
    void shouldReturnBlankWhenTransformingNullOffsetDateTime() {
        // Arrange
        OffsetDateTime input = null;
        // Act
        String result = Formatter.format(input);
        // Assert
        assertThat(result)
                .isNotNull()
                .isBlank();
    }
}

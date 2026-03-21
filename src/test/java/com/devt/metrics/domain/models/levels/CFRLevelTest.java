package com.devt.metrics.domain.models.levels;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class CFRLevelTest {

    static Stream<Arguments> cfrLevelProvider() {
        return Stream.of(
                // ELITE (cfr <= 5)
                Arguments.of(0.0, CFRLevel.ELITE),
                Arguments.of(1.0, CFRLevel.ELITE),
                Arguments.of(5.0, CFRLevel.ELITE),

                // HIGH (5 < cfr <= 15)
                Arguments.of(5.1, CFRLevel.HIGH),
                Arguments.of(10.0, CFRLevel.HIGH),
                Arguments.of(15.0, CFRLevel.HIGH),

                // MEDIUM (15 < cfr <= 30)
                Arguments.of(15.1, CFRLevel.MEDIUM),
                Arguments.of(20.0, CFRLevel.MEDIUM),
                Arguments.of(30.0, CFRLevel.MEDIUM),

                // LOW (cfr > 30)
                Arguments.of(30.1, CFRLevel.LOW),
                Arguments.of(50.0, CFRLevel.LOW),
                Arguments.of(100.0, CFRLevel.LOW)
        );
    }

    @ParameterizedTest
    @MethodSource("cfrLevelProvider")
    void of_shouldReturnLevel(double cfr, CFRLevel expectedLevel) {
        // Act
        CFRLevel result = CFRLevel.of(cfr);
        // Assert
        assertThat(result).isEqualTo(expectedLevel);
    }
}
package com.devt.metrics.domain.models.levels;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class CFRLevelTest {

    static Stream<Arguments> cfrLevelProvider() {
        return Stream.of(
                // ELITE: cfr <= 0.05
                Arguments.of(0.0,    CFRLevel.ELITE),   // zero / lower bound
                Arguments.of(0.01,   CFRLevel.ELITE),   // well inside
                Arguments.of(0.05,   CFRLevel.ELITE),   // exact upper boundary

                // HIGH: 0.05 < cfr <= 0.15
                Arguments.of(0.051,  CFRLevel.HIGH),    // just above ELITE boundary
                Arguments.of(0.10,   CFRLevel.HIGH),    // midpoint
                Arguments.of(0.15,   CFRLevel.HIGH),    // exact upper boundary

                // MEDIUM: 0.15 < cfr <= 0.30
                Arguments.of(0.151,  CFRLevel.MEDIUM),  // just above HIGH boundary
                Arguments.of(0.20,   CFRLevel.MEDIUM),  // midpoint
                Arguments.of(0.30,   CFRLevel.MEDIUM),  // exact upper boundary

                // LOW: cfr > 0.30
                Arguments.of(0.301,  CFRLevel.LOW),     // just above MEDIUM boundary
                Arguments.of(0.50,   CFRLevel.LOW),     // well inside
                Arguments.of(1.0,    CFRLevel.LOW),     // maximum realistic value (100%)
                Arguments.of(999.0,  CFRLevel.LOW)
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
package com.devt.metrics.domain.models.metrics;

import com.devt.metrics.domain.models.levels.CFRLevel;
import org.junit.jupiter.api.Test;

import static com.devt.metrics.domain.models.ModelRandomizer.aCFRMetric;
import static org.assertj.core.api.Assertions.assertThat;

class CFRMetricTest {

    @Test
    void empty_shouldInitializeCFRMetricWithEmptyValues() {
        // Act
        CFRMetric sut = CFRMetric.empty();
        // Assert
        assertThat(sut.value()).isZero();
        assertThat(sut.level()).isEqualTo(CFRLevel.LOW);
    }

    @Test
    void isElite_shouldReturnTrue_whenLevelIsElite() {
        // Arrange
        CFRMetric sut = aCFRMetric(CFRLevel.ELITE);
        // Act
        boolean isElite = sut.isElite();
        // Assert
        assertThat(isElite).isTrue();
    }

    @Test
    void isElite_shouldReturnFalse_whenLevelIsHigh() {
        // Arrange
        CFRMetric sut = aCFRMetric(CFRLevel.HIGH);
        // Act
        boolean isElite = sut.isElite();
        // Assert
        assertThat(isElite).isFalse();
    }

    @Test
    void isElite_shouldReturnFalse_whenLevelIsMedium() {
        // Arrange
        CFRMetric sut = aCFRMetric(CFRLevel.MEDIUM);
        // Act
        boolean isElite = sut.isElite();
        // Assert
        assertThat(isElite).isFalse();
    }

    @Test
    void isElite_shouldReturnFalse_whenLevelIsLow() {
        // Arrange
        CFRMetric sut = aCFRMetric(CFRLevel.LOW);
        // Act
        boolean isElite = sut.isElite();
        // Assert
        assertThat(isElite).isFalse();
    }

    @Test
    void isHigh_shouldReturnTrue_whenLevelIsHigh() {
        // Arrange
        CFRMetric sut = aCFRMetric(CFRLevel.HIGH);
        // Act
        boolean isHigh = sut.isHigh();
        // Assert
        assertThat(isHigh).isTrue();
    }

    @Test
    void isHigh_shouldReturnFalse_whenLevelIsElite() {
        // Arrange
        CFRMetric sut = aCFRMetric(CFRLevel.ELITE);
        // Act
        boolean isHigh = sut.isHigh();
        // Assert
        assertThat(isHigh).isFalse();
    }

    @Test
    void isHigh_shouldReturnFalse_whenLevelIsMedium() {
        // Arrange
        CFRMetric sut = aCFRMetric(CFRLevel.MEDIUM);
        // Act
        boolean isHigh = sut.isHigh();
        // Assert
        assertThat(isHigh).isFalse();
    }

    @Test
    void isHigh_shouldReturnFalse_whenLevelIsLow() {
        // Arrange
        CFRMetric sut = aCFRMetric(CFRLevel.LOW);
        // Act
        boolean isHigh = sut.isHigh();
        // Assert
        assertThat(isHigh).isFalse();
    }

    @Test
    void isMedium_shouldReturnTrue_whenLevelIsMedium() {
        // Arrange
        CFRMetric sut = aCFRMetric(CFRLevel.MEDIUM);
        // Act
        boolean isMedium = sut.isMedium();
        // Assert
        assertThat(isMedium).isTrue();
    }

    @Test
    void isMedium_shouldReturnFalse_whenLevelIsElite() {
        // Arrange
        CFRMetric sut = aCFRMetric(CFRLevel.ELITE);
        // Act
        boolean isMedium = sut.isMedium();
        // Assert
        assertThat(isMedium).isFalse();
    }

    @Test
    void isMedium_shouldReturnFalse_whenLevelIsHigh() {
        // Arrange
        CFRMetric sut = aCFRMetric(CFRLevel.HIGH);
        // Act
        boolean isMedium = sut.isMedium();
        // Assert
        assertThat(isMedium).isFalse();
    }

    @Test
    void isMedium_shouldReturnFalse_whenLevelIsLow() {
        // Arrange
        CFRMetric sut = aCFRMetric(CFRLevel.LOW);
        // Act
        boolean isMedium = sut.isMedium();
        // Assert
        assertThat(isMedium).isFalse();
    }

    @Test
    void isLow_shouldReturnTrue_whenLevelIsLow() {
        // Arrange
        CFRMetric sut = aCFRMetric(CFRLevel.LOW);
        // Act
        boolean isLow = sut.isLow();
        // Assert
        assertThat(isLow).isTrue();
    }

    @Test
    void isLow_shouldReturnFalse_whenLevelIsElite() {
        // Arrange
        CFRMetric sut = aCFRMetric(CFRLevel.ELITE);
        // Act
        boolean isLow = sut.isLow();
        // Assert
        assertThat(isLow).isFalse();
    }

    @Test
    void isLow_shouldReturnFalse_whenLevelIsHigh() {
        // Arrange
        CFRMetric sut = aCFRMetric(CFRLevel.HIGH);
        // Act
        boolean isLow = sut.isLow();
        // Assert
        assertThat(isLow).isFalse();
    }

    @Test
    void isLow_shouldReturnFalse_whenLevelIsMedium() {
        // Arrange
        CFRMetric sut = aCFRMetric(CFRLevel.MEDIUM);
        // Act
        boolean isLow = sut.isLow();
        // Assert
        assertThat(isLow).isFalse();
    }
}

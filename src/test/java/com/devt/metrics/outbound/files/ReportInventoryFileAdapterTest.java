package com.devt.metrics.outbound.files;

import com.devt.metrics.domain.models.reports.Report;
import com.devt.metrics.domain.outbound.ReportInventory;
import com.devt.metrics.infrastructure.outbound.files.ReportInventoryFileAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.devt.metrics.domain.models.ModelRandomizer.aReport;
import static org.assertj.core.api.Assertions.assertThat;

class ReportInventoryFileAdapterTest {

    private ReportInventory sut;

    @BeforeEach
    void setUp() {
        sut = new ReportInventoryFileAdapter();
    }


    @Test
    void store_shouldReturnExpectedFilePath() throws IOException {
        // Arrange
        Report report = aReport();
        // Act
        String result = sut.store(report);
        // Assert
        assertThat(result).isEqualTo("reports/%s.%s".formatted(report.name(), report.type()));
        Files.delete(Path.of(result));
    }

    @Test
    void store_shouldWriteContentToExpectedPath() throws IOException {
        // Arrange
        Report report = aReport();
        // Act
        String path = sut.store(report);
        // Assert
        Path reportPath = Path.of(path);
        assertThat(Files.exists(reportPath)).isTrue();
        assertThat(Files.readAllBytes(reportPath)).isEqualTo(report.content());
        Files.delete(reportPath);
    }
}
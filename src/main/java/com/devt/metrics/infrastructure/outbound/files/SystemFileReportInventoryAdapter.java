package com.devt.metrics.infrastructure.outbound.files;

import com.devt.metrics.domain.outbound.ReportInventory;
import com.devt.metrics.infrastructure.outbound.OutboundAdapter;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

@OutboundAdapter
public class SystemFileReportInventoryAdapter implements ReportInventory {

    @Override
    public String store(String name, byte[] content) {
        Path outputPath = Path.of(name);
        try {
            Files.createDirectories(outputPath.getParent());
            Files.write(outputPath, content);
            return outputPath.toString();
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to save file '%s'".formatted(name), e);
        }
    }

}

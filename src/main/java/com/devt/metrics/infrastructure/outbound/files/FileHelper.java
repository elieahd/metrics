package com.devt.metrics.infrastructure.outbound.files;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileHelper {

    private FileHelper() {
        // utility class shouldn't be initiated
    }

    public static String store(String name, byte[] content) {
        Path outputPath = Path.of(name);
        try {
            Files.createDirectories(outputPath.getParent());
            Files.write(outputPath, content);
            return outputPath.toString();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static boolean exists(String name) {
        Path path = Path.of(name);
        return Files.exists(path);
    }

    public static byte[] read(String name) {
        Path path = Path.of(name);
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}

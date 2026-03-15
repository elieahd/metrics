package com.devt.metrics.domain.models.reports;

public record Report(String name,
                     String type,
                     byte[] content) {
}

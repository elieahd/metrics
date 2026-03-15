package com.devt.metrics.domain.models.entities;

import java.time.Duration;
import java.time.OffsetDateTime;

public record PipelineRun(boolean success,
                          OffsetDateTime startedAt,
                          OffsetDateTime createdAt,
                          OffsetDateTime updatedAt) {

    public Duration duration() {
        return Duration.between(startedAt, updatedAt);
    }
}

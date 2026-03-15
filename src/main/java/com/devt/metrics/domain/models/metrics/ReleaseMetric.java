package com.devt.metrics.domain.models.metrics;

import com.devt.metrics.domain.services.helper.Formatter;

import java.time.OffsetDateTime;

public record ReleaseMetric(String version,
                            boolean isChange,
                            OffsetDateTime publishedAt) {

    public String publishedAtFormatted() {
        return Formatter.format(publishedAt);
    }

}

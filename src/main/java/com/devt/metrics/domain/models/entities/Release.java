package com.devt.metrics.domain.models.entities;

import java.time.OffsetDateTime;

public record Release(String name,
                      String tagName,
                      OffsetDateTime publishedAt) {
}

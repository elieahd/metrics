package com.devt.metrics.domain.models;

import java.util.Date;

public record Release(String name,
                      String tagName,
                      Date publishedAt) {
}

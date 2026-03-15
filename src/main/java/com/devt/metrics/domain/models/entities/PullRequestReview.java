package com.devt.metrics.domain.models.entities;

import java.time.OffsetDateTime;

public record PullRequestReview(String user,
                                String state,
                                OffsetDateTime submittedAt) {
}

package com.devt.metrics.infrastructure.outbound.github.client.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public record GitHubPullRequestReview(
        GitHubUser user,
        String state,
        @JsonProperty("submitted_at")
        OffsetDateTime submittedAt
) {
}

package com.devt.metrics.infrastructure.outbound.github.client.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public record GitHubPullRequest(

        int number,

        String title,

        String state,

        @JsonProperty("created_at")
        OffsetDateTime createdAt,

        @JsonProperty("merged_at")
        OffsetDateTime mergedAt,

        @JsonProperty("closed_at")
        OffsetDateTime closedAt,

        GitHubUser user) {
}

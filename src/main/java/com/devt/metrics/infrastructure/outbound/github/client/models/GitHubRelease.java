package com.devt.metrics.infrastructure.outbound.github.client.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public record GitHubRelease(
        @JsonProperty("tag_name")
        String tagName,
        String name,
        boolean draft,
        @JsonProperty("prerelease")
        boolean preRelease,
        @JsonProperty("published_at")
        OffsetDateTime publishedAt) {
}

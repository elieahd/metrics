package com.devt.metrics.infrastructure.outbound.github.client.models;

public record GitHubResponse<T>(T data, String nextUrl) {
}
package com.devt.metrics.infrastructure.outbound.github.client.models;

public record GitHubUser(String login,
                         String type) {

    public boolean isUser() {
        return "User".equals(type);
    }
}

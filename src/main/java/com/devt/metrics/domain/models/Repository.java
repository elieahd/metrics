package com.devt.metrics.domain.models;

import java.util.List;

public record Repository(String name,
                         List<PullRequest> pullRequests,
                         List<Release> releases) {
}

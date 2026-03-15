package com.devt.metrics.domain.models.entities;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public record PullRequest(int number,
                          String title,
                          String state,
                          OffsetDateTime publishedAt,
                          OffsetDateTime mergedAt,
                          OffsetDateTime closedAt,
                          String author,
                          boolean isAuthorUser,
                          List<PullRequestReview> reviews) {

    public List<String> reviewers() {
        if (reviews == null || reviews.isEmpty()) {
            return new ArrayList<>();
        }
        return reviews.stream()
                .map(PullRequestReview::user)
                .distinct()
                .toList();
    }

}

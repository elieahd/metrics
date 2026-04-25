package com.devt.metrics.domain.models.entities;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public record PullRequest(int number,
                          String title,
                          String state,
                          OffsetDateTime publishedAt,
                          OffsetDateTime mergedAt,
                          OffsetDateTime closedAt,
                          String author,
                          boolean isAuthorUser,
                          List<PullRequestReview> reviews) {

    public boolean isClosed() {
        return closedAt != null;
    }

    public boolean isMerged() {
        return mergedAt != null;
    }

    public boolean isOpen() {
        return closedAt == null && mergedAt == null;
    }

    public Optional<Duration> cycleTime() {
        if (publishedAt == null) {
            return Optional.empty();
        }
        OffsetDateTime endDate = mergedAt != null
                ? mergedAt
                : closedAt;
        if (endDate == null) {
            return Optional.empty();
        }
        Duration cycleTime = Duration.between(publishedAt, endDate);
        return Optional.of(cycleTime);
    }

    public Optional<Duration> reviewTime() {
        if (mergedAt == null || reviews == null || reviews.isEmpty()) {
            return Optional.empty();
        }
        return reviews.stream()
                .filter(review -> review.user() != null && !review.user().equals(author))
                .min(Comparator.comparing(PullRequestReview::submittedAt))
                .map(firstReview -> Duration.between(firstReview.submittedAt(), mergedAt));
    }

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

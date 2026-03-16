package com.devt.metrics.domain.models.entities;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.devt.metrics.domain.models.ModelRandomizer.aPullRequest;
import static com.devt.metrics.domain.models.ModelRandomizer.aPullRequestReview;
import static org.assertj.core.api.Assertions.assertThat;

class PullRequestTest {

    @Test
    void reviewers_shouldReturnEmptyList_whenReviewsIsNull() {
        // Arrange
        List<PullRequestReview> reviews = null;
        PullRequest pr = aPullRequest(reviews);
        // Act
        List<String> reviewers = pr.reviewers();
        // Assert
        assertThat(reviewers).isEmpty();
    }

    @Test
    void reviewers_shouldReturnEmptyList_whenReviewsIsEmpty() {
        // Arrange
        List<PullRequestReview> reviews = new ArrayList<>();
        PullRequest pr = aPullRequest(reviews);
        // Act
        List<String> reviewers = pr.reviewers();
        // Assert
        assertThat(reviewers).isEmpty();
    }

    @Test
    void reviewers_shouldReturnSingleReviewer_whenOneReviewExists() {
        // Arrange
        PullRequest pr = aPullRequest(
                aPullRequestReview("alice")
        );
        // Act
        List<String> reviewers = pr.reviewers();
        // Assert
        assertThat(reviewers)
                .hasSize(1)
                .containsExactly("alice");
    }

    @Test
    void reviewers_shouldReturnDistinctReviewers_whenSameUserReviewedMultipleTimes() {
        // Arrange
        PullRequest pr = aPullRequest(
                aPullRequestReview("alice"),
                aPullRequestReview("alice")
        );
        // Act
        List<String> reviewers = pr.reviewers();
        // Assert
        assertThat(reviewers)
                .hasSize(1)
                .containsExactly("alice");
    }

    @Test
    void reviewers_shouldReturnAllDistinctReviewers_whenMultipleUsersReviewed() {
        // Arrange
        List<PullRequestReview> reviews = List.of(
                aPullRequestReview("alice"),
                aPullRequestReview("bob"),
                aPullRequestReview("charlie")
        );
        PullRequest pr = aPullRequest(reviews);
        // Act
        List<String> reviewers = pr.reviewers();
        // Assert
        assertThat(reviewers)
                .hasSize(3)
                .containsExactlyInAnyOrder("alice", "bob", "charlie");
    }

    @Test
    void reviewers_shouldDeduplicateAcrossMixedReviewers_whenSomeUsersReviewedMultipleTimes() {
        // Arrange
        List<PullRequestReview> reviews = List.of(
                aPullRequestReview("alice"),
                aPullRequestReview("bob"),
                aPullRequestReview("alice"),
                aPullRequestReview("charlie")
        );
        PullRequest pr = aPullRequest(reviews);
        // Act
        List<String> reviewers = pr.reviewers();
        // Assert
        assertThat(reviewers)
                .hasSize(3)
                .containsExactlyInAnyOrder("alice", "bob", "charlie");
    }
}
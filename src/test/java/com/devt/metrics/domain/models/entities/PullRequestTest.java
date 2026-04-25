package com.devt.metrics.domain.models.entities;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.devt.metrics.domain.models.ModelRandomizer.aPullRequest;
import static com.devt.metrics.domain.models.ModelRandomizer.aPullRequestReview;
import static org.assertj.core.api.Assertions.assertThat;

class PullRequestTest {

    @Test
    void isMerged_shouldReturnTrue_whenMergedAtIsSet() {
        // Arrange
        PullRequest sut = aPullRequest(OffsetDateTime.now(), null);
        // Act
        boolean result = sut.isMerged();
        // Assert
        assertThat(result).isTrue();
    }

    @Test
    void isMerged_shouldReturnFalse_whenMergedAtIsNull() {
        // Arrange
        OffsetDateTime mergedAt = null;
        OffsetDateTime closedAt = null;
        PullRequest sut = aPullRequest(mergedAt, closedAt);
        // Act
        boolean result = sut.isMerged();
        // Assert
        assertThat(result).isFalse();
    }

    @Test
    void isClosed_shouldReturnTrue_whenClosedAtIsSet() {
        // Arrange
        PullRequest sut = aPullRequest(null, OffsetDateTime.now());
        // Act
        boolean result = sut.isClosed();
        // Assert
        assertThat(result).isTrue();
    }

    @Test
    void isClosed_shouldReturnFalse_whenClosedAtIsNull() {
        // Arrange
        OffsetDateTime mergedAt = null;
        OffsetDateTime closedAt = null;
        PullRequest sut = aPullRequest(mergedAt, closedAt);
        // Act
        boolean result = sut.isClosed();
        // Assert
        assertThat(result).isFalse();
    }

    @Test
    void isOpen_shouldReturnTrue_whenNeitherMergedNorClosed() {
        // Arrange
        OffsetDateTime mergedAt = null;
        OffsetDateTime closedAt = null;
        PullRequest sut = aPullRequest(mergedAt, closedAt);
        // Act
        boolean result = sut.isOpen();
        // Assert
        assertThat(result).isTrue();
    }

    @Test
    void isOpen_shouldReturnFalse_whenMerged() {
        // Arrange
        PullRequest sut = aPullRequest(OffsetDateTime.now(), null);
        // Act
        boolean result = sut.isOpen();
        // Assert
        assertThat(result).isFalse();
    }

    @Test
    void isOpen_shouldReturnFalse_whenClosed() {
        // Arrange
        PullRequest sut = aPullRequest(null, OffsetDateTime.now());
        // Act
        boolean result = sut.isOpen();
        // Assert
        assertThat(result).isFalse();
    }

    @Test
    void isOpen_shouldReturnFalse_whenBothMergedAndClosed() {
        // Arrange
        PullRequest sut = aPullRequest(OffsetDateTime.now(), OffsetDateTime.now());
        // Act
        boolean result = sut.isOpen();
        // Assert
        assertThat(result).isFalse();
    }

    @Test
    void cycleTime_shouldReturnEmpty_whenPublishedAtIsNull() {
        // Arrange
        PullRequest pr = aPullRequest(null, OffsetDateTime.now().plusDays(2), null, List.of());
        // Act
        var result = pr.cycleTime();
        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    void cycleTime_shouldReturnEmpty_whenNeitherMergedNorClosed() {
        // Arrange
        PullRequest pr = aPullRequest(OffsetDateTime.now(), null, null, List.of());
        // Act
        var result = pr.cycleTime();
        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    void cycleTime_shouldUseMergedAt_whenPRIsMerged() {
        // Arrange
        OffsetDateTime publishedAt = OffsetDateTime.now();
        PullRequest pr = aPullRequest(publishedAt, publishedAt.plusHours(48), null, List.of());
        // Act
        var result = pr.cycleTime();
        // Assert
        assertThat(result)
                .isPresent()
                .hasValue(Duration.ofHours(48));
    }

    @Test
    void cycleTime_shouldUseClosedAt_whenPRIsClosedButNotMerged() {
        // Arrange
        OffsetDateTime publishedAt = OffsetDateTime.now();
        PullRequest pr = aPullRequest(publishedAt, null, publishedAt.plusHours(24), List.of());
        // Act
        var result = pr.cycleTime();
        // Assert
        assertThat(result)
                .isPresent()
                .hasValue(Duration.ofHours(24));
    }

    @Test
    void cycleTime_shouldPreferMergedAt_whenBothMergedAtAndClosedAtAreSet() {
        // Arrange
        OffsetDateTime publishedAt = OffsetDateTime.now();
        PullRequest pr = aPullRequest(publishedAt, publishedAt.plusHours(10), publishedAt.plusHours(20), List.of());
        // Act
        var result = pr.cycleTime();
        // Assert
        assertThat(result)
                .isPresent()
                .hasValue(Duration.ofHours(10));
    }

    @Test
    void reviewTime_shouldReturnEmpty_whenMergedAtIsNull() {
        // Arrange
        OffsetDateTime publishedAt = OffsetDateTime.now();
        PullRequest pr = aPullRequest(publishedAt, null, null, List.of(
                aPullRequestReview("alice", publishedAt.plusHours(1))
        ));
        // Act
        var result = pr.reviewTime();
        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    void reviewTime_shouldReturnEmpty_whenReviewsIsNull() {
        // Arrange
        OffsetDateTime publishedAt = OffsetDateTime.now();
        PullRequest pr = aPullRequest(publishedAt, publishedAt.plusHours(5), null, null);
        // Act
        var result = pr.reviewTime();
        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    void reviewTime_shouldReturnEmpty_whenReviewsIsEmpty() {
        // Arrange
        OffsetDateTime publishedAt = OffsetDateTime.now();
        PullRequest pr = aPullRequest(publishedAt, publishedAt.plusHours(5), null, List.of());
        // Act
        var result = pr.reviewTime();
        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    void reviewTime_shouldReturnEmpty_whenOnlyAuthorReviewed() {
        // Arrange
        OffsetDateTime publishedAt = OffsetDateTime.now();
        PullRequest pr = aPullRequest(publishedAt, publishedAt.plusHours(5), null, "author", List.of(
                aPullRequestReview("author", publishedAt.plusHours(1))
        ));
        // Act
        var result = pr.reviewTime();
        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    void reviewTime_shouldMeasureFromFirstExternalReviewToMerge() {
        // Arrange
        OffsetDateTime publishedAt = OffsetDateTime.now();
        PullRequest pr = aPullRequest(publishedAt, publishedAt.plusHours(10), null, List.of(
                aPullRequestReview("alice", publishedAt.plusHours(4)),
                aPullRequestReview("bob", publishedAt.plusHours(6))
        ));
        // Act
        var result = pr.reviewTime();
        // Assert
        assertThat(result)
                .isPresent()
                .hasValue(Duration.ofHours(6));
    }

    @Test
    void reviewTime_shouldIgnoreAuthorReview_andUseEarliestExternalReview() {
        // Arrange
        OffsetDateTime publishedAt = OffsetDateTime.now();
        PullRequest pr = aPullRequest(publishedAt, publishedAt.plusHours(10), null, "author", List.of(
                aPullRequestReview("author", publishedAt.plusHours(1)),
                aPullRequestReview("alice", publishedAt.plusHours(5)),
                aPullRequestReview("bob", publishedAt.plusHours(7))
        ));
        // Act
        var result = pr.reviewTime();
        // Assert
        assertThat(result)
                .isPresent()
                .contains(Duration.ofHours(5));
    }

    @Test
    void reviewTime_shouldHandleSingleExternalReview() {
        // Arrange
        OffsetDateTime publishedAt = OffsetDateTime.now();
        PullRequest pr = aPullRequest(publishedAt, publishedAt.plusHours(8), null, List.of(
                aPullRequestReview("alice", publishedAt.plusHours(3))
        ));
        // Act
        var result = pr.reviewTime();
        // Assert
        assertThat(result)
                .isPresent()
                .hasValue(Duration.ofHours(5));
    }


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
        PullRequest pr = aPullRequest(new ArrayList<>());
        // Act
        List<String> reviewers = pr.reviewers();
        // Assert
        assertThat(reviewers).isEmpty();
    }

    @Test
    void reviewers_shouldReturnSingleReviewer_whenOneReviewExists() {
        // Arrange
        PullRequest pr = aPullRequest(
                List.of(
                        aPullRequestReview("alice")
                )
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
                List.of(
                        aPullRequestReview("alice"),
                        aPullRequestReview("alice")
                )
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
        PullRequest pr = aPullRequest(
                List.of(
                        aPullRequestReview("alice"),
                        aPullRequestReview("bob"),
                        aPullRequestReview("charlie")
                )
        );
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
        PullRequest pr = aPullRequest(List.of(
                aPullRequestReview("alice"),
                aPullRequestReview("bob"),
                aPullRequestReview("alice"),
                aPullRequestReview("charlie")
        ));
        // Act
        List<String> reviewers = pr.reviewers();
        // Assert
        assertThat(reviewers)
                .hasSize(3)
                .containsExactlyInAnyOrder("alice", "bob", "charlie");
    }
}
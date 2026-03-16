package com.devt.metrics.domain.models;

import com.devt.metrics.domain.models.entities.PullRequest;
import com.devt.metrics.domain.models.entities.PullRequestReview;

import java.time.OffsetDateTime;
import java.util.List;

import static com.devt.randomizer.RandomizerUtils.random;

public class ModelRandomizer {

    private ModelRandomizer() {
        // utility class shouldn't be instantiated
    }

    public static PullRequest aPullRequest(List<PullRequestReview> reviews) {
        return new PullRequest(
                random(Integer.class),
                random(String.class),
                random(String.class),
                random(OffsetDateTime.class),
                random(OffsetDateTime.class),
                random(OffsetDateTime.class),
                random(String.class),
                random(Boolean.class),
                reviews
        );
    }

    public static PullRequest aPullRequest(PullRequestReview... reviews) {
        return aPullRequest(
                List.of(reviews)
        );
    }

    public static PullRequestReview aPullRequestReview(String user) {
        return new PullRequestReview(
                user,
                random(String.class),
                random(OffsetDateTime.class)
        );
    }

}

package com.devt.metrics.domain.models;

import com.devt.metrics.domain.models.entities.PipelineRun;
import com.devt.metrics.domain.models.entities.PullRequest;
import com.devt.metrics.domain.models.entities.PullRequestReview;
import com.devt.metrics.domain.models.levels.CFRLevel;
import com.devt.metrics.domain.models.levels.DeploymentFrequencyLevel;
import com.devt.metrics.domain.models.metrics.CFRMetric;
import com.devt.metrics.domain.models.metrics.DeploymentFrequencyEntry;
import com.devt.metrics.domain.models.metrics.DeploymentFrequencyMetric;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static com.devt.randomizer.RandomizerUtils.random;

public class ModelRandomizer {

    private ModelRandomizer() {
        // utility class shouldn't be instantiated
    }

    public static PullRequest aPullRequest(String title, List<PullRequestReview> reviews) {
        return new PullRequest(
                random(Integer.class),
                title,
                random(String.class),
                random(OffsetDateTime.class),
                random(OffsetDateTime.class),
                random(OffsetDateTime.class),
                random(String.class),
                random(Boolean.class),
                reviews
        );
    }

    public static PullRequest aPullRequest(List<PullRequestReview> reviews) {
        return aPullRequest(
                random(String.class),
                reviews
        );
    }

    public static PullRequest aPullRequest(OffsetDateTime mergedAt,
                                           OffsetDateTime closedAt) {
        return new PullRequest(
                random(Integer.class),
                random(String.class),
                random(String.class),
                random(OffsetDateTime.class),
                mergedAt,
                closedAt,
                random(String.class),
                random(Boolean.class),
                new ArrayList<>()
        );
    }

    public static PullRequest aPullRequest(OffsetDateTime publishedAt,
                                           OffsetDateTime mergedAt,
                                           OffsetDateTime closedAt,
                                           List<PullRequestReview> reviews) {
        return new PullRequest(
                random(Integer.class),
                random(String.class),
                random(String.class),
                publishedAt,
                mergedAt,
                closedAt,
                random(String.class),
                random(Boolean.class),
                reviews
        );
    }

    public static PullRequest aPullRequest(OffsetDateTime publishedAt,
                                           OffsetDateTime mergedAt,
                                           OffsetDateTime closedAt,
                                           String author,
                                           List<PullRequestReview> reviews) {
        return new PullRequest(
                random(Integer.class),
                random(String.class),
                random(String.class),
                publishedAt,
                mergedAt,
                closedAt,
                author,
                random(Boolean.class),
                reviews
        );
    }


    public static PullRequest aPullRequest(String title) {
        return aPullRequest(
                title,
                IntStream.range(0, 10).mapToObj(_ -> aPullRequestReview()).toList()
        );
    }

    public static PullRequestReview aPullRequestReview() {
        return aPullRequestReview(
                random(String.class)
        );
    }

    public static PullRequestReview aPullRequestReview(String user) {
        return new PullRequestReview(
                user,
                random(String.class),
                random(OffsetDateTime.class)
        );
    }

    public static PullRequestReview aPullRequestReview(String user, OffsetDateTime publishedAt) {
        return new PullRequestReview(
                user,
                random(String.class),
                publishedAt
        );
    }

    public static PipelineRun aPipelineRun(OffsetDateTime startedAt,
                                           OffsetDateTime updatedAt) {
        return new PipelineRun(
                random(boolean.class),
                startedAt,
                random(OffsetDateTime.class),
                updatedAt
        );
    }

    public static PipelineRun aPipelineRun() {
        return aPipelineRun(
                random(OffsetDateTime.class),
                random(OffsetDateTime.class)
        );
    }

    public static CFRMetric aCFRMetric(CFRLevel level) {
        return new CFRMetric(
                random(double.class),
                level
        );
    }

    public static CFRMetric aCFRMetric() {
        return aCFRMetric(
                random(CFRLevel.class)
        );
    }

    public static DeploymentFrequencyMetric aDeploymentFrequencyMetric() {
        return aDeploymentFrequencyMetric(
                random(DeploymentFrequencyLevel.class)
        );
    }

    public static DeploymentFrequencyMetric aDeploymentFrequencyMetric(DeploymentFrequencyLevel level) {
        return new DeploymentFrequencyMetric(
                random(double.class),
                IntStream.range(0, 10).mapToObj(_ -> aDeploymentFrequencyEntry()).toList(),
                level
        );
    }

    public static DeploymentFrequencyMetric aDeploymentFrequencyMetric(DeploymentFrequencyEntry... frequencies) {
        return new DeploymentFrequencyMetric(
                random(double.class),
                List.of(frequencies),
                random(DeploymentFrequencyLevel.class)
        );
    }

    public static DeploymentFrequencyEntry aDeploymentFrequencyEntry() {
        return aDeploymentFrequencyEntry(
                random(int.class)
        );
    }

    public static DeploymentFrequencyEntry aDeploymentFrequencyEntry(int count) {
        return new DeploymentFrequencyEntry(
                random(String.class),
                count
        );
    }

}

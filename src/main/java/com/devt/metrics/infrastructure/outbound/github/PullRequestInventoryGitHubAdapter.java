package com.devt.metrics.infrastructure.outbound.github;

import com.devt.metrics.domain.models.entities.PullRequest;
import com.devt.metrics.domain.models.entities.PullRequestReview;
import com.devt.metrics.domain.outbound.PullRequestInventory;
import com.devt.metrics.infrastructure.outbound.OutboundAdapter;
import com.devt.metrics.infrastructure.outbound.github.client.GitHubClient;
import com.devt.metrics.infrastructure.outbound.github.client.models.GitHubPullRequest;
import com.devt.metrics.infrastructure.outbound.github.client.models.GitHubPullRequestReview;

import java.util.List;

@OutboundAdapter
public class PullRequestInventoryGitHubAdapter implements PullRequestInventory {

    private final GitHubClient gitHubClient;

    public PullRequestInventoryGitHubAdapter(GitHubClient gitHubClient) {
        this.gitHubClient = gitHubClient;
    }

    @Override
    public List<PullRequest> findAllByRepository(String repository) {
        return gitHubClient.findAllPullRequests(repository)
                .stream()
                .map(pr -> fetchReviewsAndMap(pr, repository))
                .toList();
    }

    private PullRequest fetchReviewsAndMap(GitHubPullRequest pr, String repository) {
        List<GitHubPullRequestReview> reviews = gitHubClient.findAllPullRequestReviews(repository, pr.number());
        return map(pr, reviews);
    }

    private PullRequest map(GitHubPullRequest pullRequest, List<GitHubPullRequestReview> ghReviews) {
        List<PullRequestReview> reviews = ghReviews.stream()
                .map(this::map)
                .toList();
        return new PullRequest(
                pullRequest.number(),
                pullRequest.title(),
                pullRequest.state(),
                pullRequest.createdAt(),
                pullRequest.mergedAt(),
                pullRequest.closedAt(),
                pullRequest.user() != null ? pullRequest.user().login() : null,
                pullRequest.user() != null && pullRequest.user().isUser(),
                reviews
        );
    }

    private PullRequestReview map(GitHubPullRequestReview review) {
        return new PullRequestReview(
                review.user() != null ? review.user().login() : null,
                review.state(),
                review.submittedAt()
        );
    }
}

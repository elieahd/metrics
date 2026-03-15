package com.devt.metrics.infrastructure.outbound.github.client;

import com.devt.metrics.infrastructure.outbound.github.client.models.GitHubPullRequest;
import com.devt.metrics.infrastructure.outbound.github.client.models.GitHubPullRequestReview;
import com.devt.metrics.infrastructure.outbound.github.client.models.GitHubRelease;
import com.devt.metrics.infrastructure.outbound.github.client.models.GitHubWorkflow;
import com.devt.metrics.infrastructure.outbound.github.client.models.GitHubWorkflowRun;

import java.util.List;

public interface GitHubClient {

    List<GitHubPullRequest> findAllPullRequests(String repository);

    List<GitHubPullRequestReview> findAllPullRequestReviews(String repository, long pullRequestNumber);

    List<GitHubWorkflow> findAllWorkflows(String repository);

    List<GitHubWorkflowRun> findAllWorkflowRuns(String repository, long workflowId);

    List<GitHubRelease> findAllReleases(String repository);

}

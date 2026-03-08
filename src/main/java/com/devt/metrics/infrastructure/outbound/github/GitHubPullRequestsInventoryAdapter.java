package com.devt.metrics.infrastructure.outbound.github;

import com.devt.metrics.domain.models.PullRequest;
import com.devt.metrics.domain.outbound.PullRequestsInventory;
import com.devt.metrics.infrastructure.outbound.OutboundAdapter;
import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GHPullRequest;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@OutboundAdapter
public class GitHubPullRequestsInventoryAdapter extends GitHubClient implements PullRequestsInventory {

    @Override
    public List<PullRequest> findAllByRepository(String repository) {
        try {
            GHRepository repo = get(repository);
            List<GHPullRequest> prs = repo.getPullRequests(GHIssueState.CLOSED);
            List<PullRequest> results = new ArrayList<>();
            for (GHPullRequest pr : prs) {
                PullRequest pullRequest = new PullRequest(
                        pr.getNumber(),
                        pr.getUser().getLogin(),
                        pr.getCreatedAt(),
                        pr.getMergedAt(),
                        pr.getRequestedReviewers().stream().map(GHUser::getLogin).toList()
                );
                results.add(pullRequest);
            }
            return results;
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}

package com.devt.metrics.infrastructure.outbound.github;

import com.devt.metrics.domain.models.entities.Release;
import com.devt.metrics.domain.outbound.ReleaseInventory;
import com.devt.metrics.infrastructure.outbound.OutboundAdapter;
import com.devt.metrics.infrastructure.outbound.github.client.GitHubClient;
import com.devt.metrics.infrastructure.outbound.github.client.models.GitHubRelease;

import java.util.Comparator;
import java.util.List;

@OutboundAdapter
public class ReleaseInventoryGitHubAdapter implements ReleaseInventory {

    private final GitHubClient gitHubClient;

    public ReleaseInventoryGitHubAdapter(GitHubClient gitHubClient) {
        this.gitHubClient = gitHubClient;
    }

    @Override
    public List<Release> findAllByRepository(String repository) {
        return gitHubClient.findAllReleases(repository)
                .stream()
                .filter(release -> !release.draft() && !release.preRelease())
                .map(this::map)
                .sorted(Comparator.comparing(Release::publishedAt, Comparator.nullsLast(Comparator.naturalOrder())))
                .toList();
    }

    private Release map(GitHubRelease release) {
        return new Release(
                release.name(),
                release.tagName(),
                release.publishedAt()
        );
    }

}

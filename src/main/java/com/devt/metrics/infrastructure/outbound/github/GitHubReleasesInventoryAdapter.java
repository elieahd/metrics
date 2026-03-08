package com.devt.metrics.infrastructure.outbound.github;

import com.devt.metrics.domain.models.Release;
import com.devt.metrics.domain.outbound.ReleasesInventory;
import com.devt.metrics.infrastructure.outbound.OutboundAdapter;
import org.kohsuke.github.GHRelease;
import org.kohsuke.github.GHRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@OutboundAdapter
public class GitHubReleasesInventoryAdapter extends GitHubClient implements ReleasesInventory {

    @Override
    public List<Release> findAllByRepository(String repository) {
        try {
            GHRepository repo = get(repository);
            List<GHRelease> releases = repo.listReleases().toList();
            List<Release> results = new ArrayList<>();
            for (GHRelease rel : releases) {
                if (!rel.isDraft() && !rel.isPrerelease()) {
                    Release release = new Release(
                            rel.getName(),
                            rel.getTagName(),
                            rel.getPublished_at()
                    );
                    results.add(release);
                }
            }
            return results;
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}

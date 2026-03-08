package com.devt.metrics.infrastructure.outbound.github;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.IOException;

public abstract class GitHubClient {

    protected GHRepository get(String repository) throws IOException {
        GitHub gitHub = GitHub.connectAnonymously();
        return gitHub.getRepository(repository);
    }
}

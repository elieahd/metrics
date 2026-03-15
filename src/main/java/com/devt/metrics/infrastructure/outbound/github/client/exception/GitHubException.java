package com.devt.metrics.infrastructure.outbound.github.client.exception;

import java.io.IOException;

public class GitHubException extends RuntimeException {

    public GitHubException(int code, String message) {
        super("GitHub API Exception : %d - %s".formatted(code, message));
    }

    public GitHubException(IOException e) {
        super(e);
    }

}

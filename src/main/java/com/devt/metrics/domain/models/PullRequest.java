package com.devt.metrics.domain.models;

import java.util.Date;
import java.util.List;

public record PullRequest(int number,
                          String author,
                          Date createdAt,
                          Date mergedAt,
                          List<String> reviewers) {
}

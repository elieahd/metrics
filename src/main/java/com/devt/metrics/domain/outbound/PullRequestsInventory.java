package com.devt.metrics.domain.outbound;

import com.devt.metrics.domain.models.PullRequest;

import java.util.List;

public interface PullRequestsInventory {

    List<PullRequest> findAllByRepository(String repository);

}

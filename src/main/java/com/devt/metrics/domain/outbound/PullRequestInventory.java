package com.devt.metrics.domain.outbound;

import com.devt.metrics.domain.models.entities.PullRequest;

import java.util.List;

public interface PullRequestInventory {

    List<PullRequest> findAllByRepository(String repository);

}

package com.devt.metrics.domain.outbound;

import com.devt.metrics.domain.models.Release;

import java.util.List;

public interface ReleasesInventory {

    List<Release> findAllByRepository(String repository);
}

package com.devt.metrics.domain.outbound;

import com.devt.metrics.domain.models.entities.Release;

import java.util.List;

public interface ReleaseInventory {

    List<Release> findAllByRepository(String repository);

}

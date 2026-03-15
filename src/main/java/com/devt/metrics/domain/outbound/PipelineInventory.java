package com.devt.metrics.domain.outbound;

import com.devt.metrics.domain.models.entities.Pipeline;

import java.util.List;

public interface PipelineInventory {

    List<Pipeline> findAllByRepository(String repository);

}

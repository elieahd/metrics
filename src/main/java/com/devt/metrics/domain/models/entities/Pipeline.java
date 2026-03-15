package com.devt.metrics.domain.models.entities;

import java.util.List;

public record Pipeline(String name,
                       List<PipelineRun> runs) {
}

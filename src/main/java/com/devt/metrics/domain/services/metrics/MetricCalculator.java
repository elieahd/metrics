package com.devt.metrics.domain.services.metrics;

import com.devt.metrics.domain.models.Metric;
import com.devt.metrics.domain.models.ProjectContext;

public interface MetricCalculator {

    Metric calculate(ProjectContext project);

}

package com.devt.metrics.domain.services.metrics;

import com.devt.metrics.domain.models.Metric;
import com.devt.metrics.domain.models.ProjectContext;
import com.devt.metrics.domain.models.MetricType;

public class LeadTimeCalculator implements MetricCalculator {

    @Override
    public Metric calculate(ProjectContext project) {
        double value = 0.0;
        return new Metric(MetricType.LEAD_TIME, value);
    }

}

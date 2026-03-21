package com.devt.metrics.domain.services.metrics;

import com.devt.metrics.domain.models.levels.CFRLevel;
import com.devt.metrics.domain.models.metrics.CFRMetric;

public class CFRMetricCalculator implements MetricCalculator<Double, CFRMetric> {

    @Override
    public CFRMetric apply(Double cfr) {
        CFRLevel level = level(cfr);
        return new CFRMetric(cfr, level);
    }

    private CFRLevel level(Double input) {
        if (input == null || input == 0) {
            return CFRLevel.LOW;
        }
        if (input > 20) {
            return CFRLevel.ELITE;
        }
        if (input > 1) {
            return CFRLevel.HIGH;
        }
        if (input > 1) {
            return CFRLevel.MEDIUM;
        }
        return CFRLevel.LOW;
    }
}

package com.devt.metrics.domain.exceptions;

import com.devt.metrics.domain.models.MetricType;

public class MetricCalculatorNotFoundException extends Exception {
    public MetricCalculatorNotFoundException(MetricType type) {
        super("Metric calculator of type '%s' not found".formatted(type));
    }
}

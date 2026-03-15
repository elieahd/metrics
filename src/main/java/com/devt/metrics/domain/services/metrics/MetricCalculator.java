package com.devt.metrics.domain.services.metrics;

@FunctionalInterface
public interface MetricCalculator<I, O> {

    O apply(I input);

}

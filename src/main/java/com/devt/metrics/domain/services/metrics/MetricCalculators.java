package com.devt.metrics.domain.services.metrics;

import com.devt.metrics.domain.exceptions.MetricCalculatorNotFoundException;
import com.devt.metrics.domain.models.MetricType;
import com.devt.metrics.domain.services.DomainService;

import java.util.EnumMap;
import java.util.Map;

@DomainService
public class MetricCalculators {

    private final Map<MetricType, MetricCalculator> calculators;

    public MetricCalculators() {
        this.calculators = new EnumMap<>(MetricType.class);
        this.calculators.put(MetricType.LEAD_TIME, new LeadTimeCalculator());
    }

    public MetricCalculator get(MetricType type) throws MetricCalculatorNotFoundException {
        if (!calculators.containsKey(type)) {
            throw new MetricCalculatorNotFoundException(type);
        }
        return calculators.get(type);
    }
}

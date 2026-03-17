package com.devt.metrics.domain.models.metrics;

import com.devt.metrics.domain.services.helper.Formatter;

import java.time.Duration;
import java.util.List;

public record ContributionsMetrics(Duration averageTimeTo10thPR,
                                   List<ContributorMetric> contributors) {

    public String averageTimeTo10thPRFormatted() {
        return Formatter.format(averageTimeTo10thPR);
    }
}

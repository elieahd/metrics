package com.devt.metrics.domain.models.metrics;

import com.devt.metrics.domain.services.helper.Formatter;

import java.time.Duration;
import java.util.List;

public record ContributionsMetrics(long totalContributors,
                                   long totalActiveContributors,
                                   Duration averageTimeTo10thPR,
                                   List<ContributorMetric> contributors) {

    public String averageTimeTo10thPRFormatted() {
        return Formatter.formatInDaysAndHours(averageTimeTo10thPR);
    }
}

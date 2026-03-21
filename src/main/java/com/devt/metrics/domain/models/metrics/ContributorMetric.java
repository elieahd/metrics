package com.devt.metrics.domain.models.metrics;

import com.devt.metrics.domain.services.helper.Formatter;

import java.time.Duration;
import java.time.OffsetDateTime;

public record ContributorMetric(String user,
                                long totalPullRequests,
                                long totalReviews,
                                OffsetDateTime firstPrDate,
                                OffsetDateTime lastPrDate,
                                boolean active,
                                Duration timeTo10thPr) {

    public String firstPrDateFormatted() {
        return Formatter.format(firstPrDate);
    }

    public String lastPrDateFormatted() {
        return Formatter.format(lastPrDate);
    }

    public String timeTo10thPrDateFormatted() {
        return Formatter.formatInDaysAndHours(timeTo10thPr);
    }

}

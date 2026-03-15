package com.devt.metrics.domain.services.metrics;

import com.devt.metrics.domain.models.entities.Release;
import com.devt.metrics.domain.models.metrics.ReleaseMetric;

public class ReleaseMetricCalculator implements MetricCalculator<Release, ReleaseMetric> {

    @Override
    public ReleaseMetric apply(Release release) {

        String version = release.tagName().replace("-rc", "");

        boolean isChange = version.endsWith(".0");

        return new ReleaseMetric(
                version,
                isChange,
                release.publishedAt()
        );
    }

}

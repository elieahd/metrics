package com.devt.metrics.domain.services.metrics;

import com.devt.metrics.domain.models.entities.PullRequest;
import com.devt.metrics.domain.models.entities.Repository;
import com.devt.metrics.domain.models.metrics.CollaboratorMetric;
import com.devt.metrics.domain.models.metrics.CollaboratorMetricAccumulator;

import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollaboratorMetricCalculator implements MetricCalculator<List<Repository>, List<CollaboratorMetric>> {

    private static final int CUT_OFF = 30;

    @Override
    public List<CollaboratorMetric> apply(List<Repository> repositories) {
        OffsetDateTime cutoff = OffsetDateTime.now().minusDays(CUT_OFF);
        Map<String, CollaboratorMetricAccumulator> acc = new HashMap<>();
        for (Repository repository : repositories) {
            for (PullRequest pullRequest : repository.pullRequests()) {
                if (pullRequest.isAuthorUser()) {
                    acc.computeIfAbsent(pullRequest.author(), k -> new CollaboratorMetricAccumulator())
                            .addPr(pullRequest);
                }
                for (String reviewer : pullRequest.reviewers()) {
                    acc.computeIfAbsent(reviewer, k -> new CollaboratorMetricAccumulator())
                            .addReview();
                }
            }
        }
        return acc.entrySet()
                .stream()
                .map(entry -> entry.getValue().toMetric(entry.getKey(), cutoff))
                .sorted(Comparator.comparing(CollaboratorMetric::totalPullRequests).reversed())
                .toList();
    }

}

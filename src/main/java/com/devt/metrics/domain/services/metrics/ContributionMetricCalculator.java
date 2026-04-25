package com.devt.metrics.domain.services.metrics;

import com.devt.metrics.domain.models.entities.PullRequest;
import com.devt.metrics.domain.models.entities.Repository;
import com.devt.metrics.domain.models.metrics.ContributionsMetrics;
import com.devt.metrics.domain.models.metrics.ContributorMetric;
import com.devt.metrics.domain.models.metrics.ContributorMetricAccumulator;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContributionMetricCalculator implements MetricCalculator<List<Repository>, ContributionsMetrics> {

    private static final int CUT_OFF = 30;

    @Override
    public ContributionsMetrics apply(List<Repository> repositories) {

        if  (repositories == null || repositories.isEmpty()) {
            return ContributionsMetrics.empty();
        }

        OffsetDateTime cutoff = OffsetDateTime.now().minusDays(CUT_OFF);
        Map<String, ContributorMetricAccumulator> acc = new HashMap<>();
        for (Repository repository : repositories) {
            for (PullRequest pullRequest : repository.pullRequests()) {
                if (pullRequest.isAuthorUser()) {
                    acc.computeIfAbsent(pullRequest.author(), _ -> new ContributorMetricAccumulator())
                            .addPr(pullRequest);
                }
                for (String reviewer : pullRequest.reviewers()) {
                    acc.computeIfAbsent(reviewer, _ -> new ContributorMetricAccumulator())
                            .addReview();
                }
            }
        }

        List<ContributorMetric> contributors = acc.entrySet()
                .stream()
                .map(entry -> entry.getValue().toMetric(entry.getKey(), cutoff))
                .sorted(Comparator.comparing(ContributorMetric::totalPullRequests).reversed())
                .toList();

        List<Duration> timeTo10thPRs = contributors.stream()
                .map(ContributorMetric::timeTo10thPr)
                .filter(timeTo10thPR -> timeTo10thPR != null && !timeTo10thPR.isZero())
                .toList();

        Duration averageTimeTo10thPR = !timeTo10thPRs.isEmpty()
                ? timeTo10thPRs.stream().reduce(Duration.ZERO, Duration::plus).dividedBy(timeTo10thPRs.size())
                : Duration.ZERO;

        long totalContributors = contributors.size();
        long totalActiveContributors = contributors.stream().filter(ContributorMetric::active).count();

        return new ContributionsMetrics(
                totalContributors,
                totalActiveContributors,
                averageTimeTo10thPR,
                contributors
        );
    }

}

package com.devt.metrics.domain.models.levels;

import com.devt.metrics.domain.models.entities.PullRequest;

import java.util.Locale;

public enum PullRequestCategory {

    UPGRADES("Upgrades"),
    MAINTENANCE("Maintenance"),
    DOCS("Docs"),
    FIX("Fix"),
    FEATURE("Feature"),
    UNKNOWN("Unknown");

    private final String label;

    PullRequestCategory(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }

    public static PullRequestCategory map(PullRequest pullRequest) {
        if (pullRequest.title() == null || pullRequest.title().isBlank()) {
            return UNKNOWN;
        }

        String title = pullRequest.title().toLowerCase(Locale.ROOT);

        if (title.startsWith("chore(maven-deps)")) return UPGRADES;
        if (title.startsWith("chore(github-actions-deps)")) return UPGRADES;
        if (title.startsWith("chore(deps)")) return UPGRADES;
        if (title.startsWith("chore")) return MAINTENANCE;
        if (title.startsWith("feat")) return FEATURE;
        if (title.startsWith("docs")) return DOCS;
        if (title.startsWith("fix")) return FIX;

        return UNKNOWN;
    }

}

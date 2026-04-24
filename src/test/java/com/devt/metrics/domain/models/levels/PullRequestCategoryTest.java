package com.devt.metrics.domain.models.levels;

import com.devt.metrics.domain.models.entities.PullRequest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.devt.metrics.domain.models.ModelRandomizer.aPullRequest;
import static org.assertj.core.api.Assertions.assertThat;

class PullRequestCategoryTest {

    private static Stream<Arguments> provideLabels() {
        return Stream.of(
                Arguments.of(PullRequestCategory.UPGRADES, "Upgrades"),
                Arguments.of(PullRequestCategory.MAINTENANCE, "Maintenance"),
                Arguments.of(PullRequestCategory.DOCS, "Docs"),
                Arguments.of(PullRequestCategory.FIX, "Fix"),
                Arguments.of(PullRequestCategory.FEATURE, "Feature"),
                Arguments.of(PullRequestCategory.UNKNOWN, "Unknown")
        );
    }

    @ParameterizedTest
    @MethodSource("provideLabels")
    void label_shouldReturnLabel(PullRequestCategory category, String expectedLabel) {
        // Act
        String actualLabel = category.label();
        // Assert
        assertThat(actualLabel).isEqualTo(expectedLabel);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "chore(maven-deps): bump org.assertj:assertj-core from 3.27.0 to 3.27.1",
            "chore(maven-deps): bump org.junit.jupiter:junit-jupiter from 5.11.4 to 5.12.0",
            "CHORE(MAVEN-DEPS): bump something",
            "chore(github-actions-deps): bump actions/checkout from 5 to 6",
            "chore(github-actions-deps): bump sonarsource/sonarqube-scan-action from 4 to 5",
            "CHORE(GITHUB-ACTIONS-DEPS): bump something",
            "chore(deps): bump actions/checkout from 5 to 6",
            "chore(deps): update requests requirement from ~=2.26 to ~=2.32",
            "CHORE(DEPS): bump something",
    })
    void categorize_shouldReturnUpgrades(String title) {
        // Arrange
        PullRequest sut = aPullRequest(title);
        // Act
        PullRequestCategory result = PullRequestCategory.map(sut);
        // Assert
        assertThat(result).isEqualTo(PullRequestCategory.UPGRADES);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "chore: bump to 0.2.4",
            "chore: add license",
            "chore: update docs",
            "CHORE: some maintenance task",
    })
    void categorize_shouldReturnMaintenance(String title) {
        // Arrange
        PullRequest sut = aPullRequest(title);
        // Act
        PullRequestCategory result = PullRequestCategory.map(sut);
        // Assert
        assertThat(result).isEqualTo(PullRequestCategory.MAINTENANCE);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "feat: add new randomizer feature",
            "feat(core): implement collection support",
            "feature: new endpoint",
            "FEAT: something new",
    })
    void categorize_shouldReturnFeature(String title) {
        // Arrange
        PullRequest sut = aPullRequest(title);
        // Act
        PullRequestCategory result = PullRequestCategory.map(sut);
        // Assert
        assertThat(result).isEqualTo(PullRequestCategory.FEATURE);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "docs: update README",
            "docs(api): add swagger annotations",
            "DOCS: something",
    })
    void categorize_shouldReturnDocs(String title) {
        // Arrange
        PullRequest sut = aPullRequest(title);
        // Act
        PullRequestCategory result = PullRequestCategory.map(sut);
        // Assert
        assertThat(result).isEqualTo(PullRequestCategory.DOCS);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "fix: correct null pointer in randomizer",
            "fix(core): handle edge case",
            "FIX: something",
    })
    void categorize_shouldReturnFix(String title) {
        // Arrange
        PullRequest sut = aPullRequest(title);
        // Act
        PullRequestCategory result = PullRequestCategory.map(sut);
        // Assert
        assertThat(result).isEqualTo(PullRequestCategory.FIX);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            "",
            "   ",
            "\t",
            "\n",
            "enh",
            "ignore files",
            "Update README.md",
            "WIP: something",
            "random title with no convention",
    })
    void categorize_shouldReturnUnknown(String title) {
        // Arrange
        PullRequest sut = aPullRequest(title);
        // Act
        PullRequestCategory result = PullRequestCategory.map(sut);
        // Assert
        assertThat(result).isEqualTo(PullRequestCategory.UNKNOWN);
    }
}
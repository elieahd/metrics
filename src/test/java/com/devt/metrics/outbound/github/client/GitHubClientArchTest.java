package com.devt.metrics.outbound.github.client;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(
        packages = "com.devt.metrics.infrastructure.outbound.github.client",
        importOptions = ImportOption.DoNotIncludeTests.class
)
class GitHubClientArchTest {

    @ArchTest
    static final ArchRule shouldNotDependsOnExternalClasses = classes()
            .that()
            .resideInAPackage("..infrastructure.outbound.github.client..")
            .should()
            .onlyDependOnClassesThat()
            .resideInAnyPackage(
                    "com.devt.metrics.infrastructure.outbound.github.client..",
                    "java..",
                    "tools.jackson..",
                    "com.fasterxml.jackson.annotation..",
                    "okhttp3.."
            );
}

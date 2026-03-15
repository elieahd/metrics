package com.devt.metrics.domain;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(
        packages = "com.devt.metrics",
        importOptions = DoNotIncludeTests.class
)
class DomainArchTest {

    @ArchTest
    static final ArchRule shouldNotDependsOnExternalClasses = classes()
            .that()
            .resideInAPackage("..domain..")
            .should()
            .onlyDependOnClassesThat()
            .resideInAnyPackage(
                    "com.devt.metrics.domain..",
                    "java..",
                    "org.slf4j.."
            );

    @ArchTest
    static final ArchRule inboundShouldOnlyBeInterfaces = classes()
            .that()
            .resideInAPackage("..domain.inbound..")
            .should()
            .beInterfaces();

    @ArchTest
    static final ArchRule outboundShouldOnlyBeInterfaces = classes()
            .that()
            .resideInAPackage("..domain.outbound..")
            .should()
            .beInterfaces();

}

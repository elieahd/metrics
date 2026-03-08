package com.devt.metrics.infrastructure.configurations;

import com.devt.metrics.MetricsApplication;
import com.devt.metrics.domain.services.DomainService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;

import static org.springframework.context.annotation.FilterType.ANNOTATION;

@Configuration
@ComponentScan(
        basePackageClasses = MetricsApplication.class,
        includeFilters = {
                @Filter(type = ANNOTATION, classes = DomainService.class)
        }
)
public class DomainConfiguration {
}

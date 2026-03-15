package com.devt.metrics.infrastructure.configurations;

import com.devt.metrics.MetricsApplication;
import com.devt.metrics.infrastructure.outbound.OutboundAdapter;
import com.devt.metrics.infrastructure.outbound.github.client.GitHubClient;
import com.devt.metrics.infrastructure.outbound.github.client.GitHubClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

import static org.springframework.context.annotation.FilterType.ANNOTATION;

@Configuration
@ComponentScan(
        basePackageClasses = MetricsApplication.class,
        includeFilters = {
                @Filter(type = ANNOTATION, classes = OutboundAdapter.class)
        }
)
public class OutboundConfiguration {

    @Bean
    public GitHubClient gitHubClient(@Value("${github.url}") String url,
                                     @Value("${github.token}") String token) {
        return GitHubClientBuilder.builder()
                .url(url)
                .token(token)
                .build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return JsonMapper.builder()
                .findAndAddModules()
                .build();
    }

}

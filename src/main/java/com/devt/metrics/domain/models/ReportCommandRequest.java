package com.devt.metrics.domain.models;

import java.util.List;

public record ReportCommandRequest(String projectName,
                                   List<String> repositoryNames) {
}

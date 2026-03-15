package com.devt.metrics.domain.inbound;

import java.util.List;

public record ReportCommandRequest(String projectName,
                                   List<String> repositoryNames) {
}

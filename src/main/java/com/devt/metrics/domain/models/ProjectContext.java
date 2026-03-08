package com.devt.metrics.domain.models;

import java.util.List;

public record ProjectContext(String name,
                             List<Repository> repositories) {
}

package com.devt.metrics.domain.models.entities;

import java.util.List;

public record Project(String name,
                      List<Repository> repositories) {
}

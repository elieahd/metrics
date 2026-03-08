package com.devt.metrics.domain.models;

import java.util.List;

public record Project(String name,
                      List<String> repositories) {
}

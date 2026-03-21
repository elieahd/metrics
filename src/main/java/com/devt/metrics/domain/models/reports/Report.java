package com.devt.metrics.domain.models.reports;

import java.util.Arrays;
import java.util.Objects;

public record Report(String name,
                     String type,
                     byte[] content) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Report other)) return false;
        return Objects.equals(name, other.name)
                && Objects.equals(type, other.type)
                && Arrays.equals(content, other.content);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, type);
        result = 31 * result + Arrays.hashCode(content);
        return result;
    }

    @Override
    public String toString() {
        return "Report{" +
                "name='" + name + '\'' +
                ", type='" + type + '}';
    }
}

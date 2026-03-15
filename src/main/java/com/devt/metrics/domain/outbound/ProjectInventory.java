package com.devt.metrics.domain.outbound;

import com.devt.metrics.domain.models.entities.Project;

import java.util.Optional;

public interface ProjectInventory {

    Optional<Project> findByName(String name);

    void store(Project project);

}

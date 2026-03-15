package com.devt.metrics.infrastructure.outbound.files;

import com.devt.metrics.domain.models.entities.Project;
import com.devt.metrics.domain.outbound.ProjectInventory;
import com.devt.metrics.infrastructure.outbound.OutboundAdapter;
import tools.jackson.databind.ObjectMapper;

import java.util.Optional;

@OutboundAdapter
public class ProjectInventoryFileAdapter implements ProjectInventory {

    private final ObjectMapper mapper;

    public ProjectInventoryFileAdapter(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Optional<Project> findByName(String name) {
        String filePath = "data/%s.json".formatted(name);
        if (!FileHelper.exists(filePath)) {
            return Optional.empty();
        }
        byte[] content = FileHelper.read(filePath);
        Project project = mapper.readValue(content, Project.class);
        return Optional.ofNullable(project);
    }

    @Override
    public void store(Project project) {
        String name = "data/%s.json".formatted(project.name());
        byte[] content = mapper.writeValueAsBytes(project);
        FileHelper.store(name, content);
    }

}

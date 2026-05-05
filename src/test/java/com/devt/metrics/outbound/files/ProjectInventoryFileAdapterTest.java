package com.devt.metrics.outbound.files;

import com.devt.metrics.domain.models.entities.Project;
import com.devt.metrics.domain.outbound.ProjectInventory;
import com.devt.metrics.infrastructure.outbound.files.ProjectInventoryFileAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static com.devt.metrics.domain.models.ModelRandomizer.aProject;
import static com.devt.randomizer.RandomizerUtils.random;
import static org.assertj.core.api.Assertions.assertThat;

class ProjectInventoryFileAdapterTest {

    private ProjectInventory sut;
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
        sut = new ProjectInventoryFileAdapter(mapper);
    }

    @Test
    void findByName_shouldReturnEmpty_whenFileDoesNotExist() {
        // Arrange
        String name = random(String.class);
        // Act
        Optional<Project> result = sut.findByName(name);
        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    void findByName_shouldReturnProject_whenFileExists() throws IOException {
        // Arrange
        Project project = aProject();
        byte[] content = mapper.writeValueAsBytes(project);
        Path filePath = Path.of("data/%s.json".formatted(project.name()));
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, content);
        // Act
        Optional<Project> result = sut.findByName(project.name());
        // Assert
        assertThat(result).isPresent().contains(project);
        Files.delete(filePath);
    }

    @Test
    void store_shouldWriteSerializedProjectToExpectedPath() throws IOException {
        // Arrange
        Project project = aProject();
        Path expectedPath = Path.of("data/%s.json".formatted(project.name()));
        // Act
        sut.store(project);
        // Assert
        assertThat(Files.exists(expectedPath)).isTrue();
        byte[] written = Files.readAllBytes(expectedPath);
        Project stored = mapper.readValue(written, Project.class);
        assertThat(stored).isEqualTo(project);
        Files.delete(expectedPath);
    }

    @Test
    void store_thenFindByName_shouldReturnSameProject() throws IOException {
        // Arrange
        Project project = aProject();
        sut.store(project);
        // Act
        Optional<Project> result = sut.findByName(project.name());
        // Assert
        assertThat(result).isPresent().contains(project);
        Path projectPath = Path.of("data/%s.json".formatted(project.name()));
        Files.delete(projectPath);
    }

}
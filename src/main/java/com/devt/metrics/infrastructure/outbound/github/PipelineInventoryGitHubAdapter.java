package com.devt.metrics.infrastructure.outbound.github;

import com.devt.metrics.domain.models.entities.Pipeline;
import com.devt.metrics.domain.models.entities.PipelineRun;
import com.devt.metrics.domain.outbound.PipelineInventory;
import com.devt.metrics.infrastructure.outbound.OutboundAdapter;
import com.devt.metrics.infrastructure.outbound.github.client.GitHubClient;
import com.devt.metrics.infrastructure.outbound.github.client.models.GitHubWorkflow;
import com.devt.metrics.infrastructure.outbound.github.client.models.GitHubWorkflowRun;

import java.util.List;

@OutboundAdapter
public class PipelineInventoryGitHubAdapter implements PipelineInventory {

    private final GitHubClient gitHubClient;

    public PipelineInventoryGitHubAdapter(GitHubClient gitHubClient) {
        this.gitHubClient = gitHubClient;
    }

    @Override
    public List<Pipeline> findAllByRepository(String repository) {
        return gitHubClient.findAllWorkflows(repository)
                .stream()
                .map(workflow -> fetchRunsAndMap(workflow, repository))
                .toList();
    }

    private Pipeline fetchRunsAndMap(GitHubWorkflow workflow, String repository) {
        List<GitHubWorkflowRun> workflowRuns = gitHubClient.findAllWorkflowRuns(repository, workflow.id());
        return map(workflow, workflowRuns);
    }

    private Pipeline map(GitHubWorkflow workflow, List<GitHubWorkflowRun> workflowRuns) {
        List<PipelineRun> runs = workflowRuns.stream()
                .map(this::map)
                .toList();
        return new Pipeline(
                workflow.name(),
                runs
        );
    }

    private PipelineRun map(GitHubWorkflowRun run) {
        return new PipelineRun(
                run.isSuccess(),
                run.startedAt(),
                run.createdAt(),
                run.updatedAt()
        );
    }
}

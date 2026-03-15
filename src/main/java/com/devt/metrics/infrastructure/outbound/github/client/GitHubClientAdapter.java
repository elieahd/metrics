package com.devt.metrics.infrastructure.outbound.github.client;

import com.devt.metrics.infrastructure.outbound.github.client.exception.GitHubException;
import com.devt.metrics.infrastructure.outbound.github.client.models.GitHubPullRequest;
import com.devt.metrics.infrastructure.outbound.github.client.models.GitHubPullRequestReview;
import com.devt.metrics.infrastructure.outbound.github.client.models.GitHubRelease;
import com.devt.metrics.infrastructure.outbound.github.client.models.GitHubResponse;
import com.devt.metrics.infrastructure.outbound.github.client.models.GitHubWorkflow;
import com.devt.metrics.infrastructure.outbound.github.client.models.GitHubWorkflowList;
import com.devt.metrics.infrastructure.outbound.github.client.models.GitHubWorkflowRun;
import com.devt.metrics.infrastructure.outbound.github.client.models.GitHubWorkflowRunList;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class GitHubClientAdapter implements GitHubClient {

    private static final int PAGE_SIZE = 100;

    private final String baseUrl;
    private final String token;
    private final OkHttpClient httpClient;
    private final JsonMapper mapper;

    public GitHubClientAdapter(String baseUrl,
                               String token) {
        this.baseUrl = baseUrl;
        this.token = token;
        this.httpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        this.mapper = JsonMapper.builder()
                .findAndAddModules()
                .build();
    }

    @Override
    public List<GitHubPullRequest> findAllPullRequests(String repository) {
        String url = "%s/repos/%s/pulls?state=all&per_page=%d".formatted(baseUrl, repository, PAGE_SIZE);
        return callUntilLastPage(
                url,
                new TypeReference<>() {
                }
        );
    }

    @Override
    public List<GitHubPullRequestReview> findAllPullRequestReviews(String repository, long pullRequestNumber) {
        String url = "%s/repos/%s/pulls/%d/reviews".formatted(this.baseUrl, repository, pullRequestNumber);
        GitHubResponse<List<GitHubPullRequestReview>> response = call(url, new TypeReference<>() {
        });
        return response.data();
    }

    @Override
    public List<GitHubWorkflow> findAllWorkflows(String repository) {
        String url = "%s/repos/%s/actions/workflows?per_page=%d".formatted(baseUrl, repository, PAGE_SIZE);
        return callUntilLastPageWrapped(url, new TypeReference<>() {
        }, GitHubWorkflowList::workflows);
    }

    @Override
    public List<GitHubWorkflowRun> findAllWorkflowRuns(String repository, long workflowId) {
        String url = "%s/repos/%s/actions/workflows/%d/runs?per_page=%d".formatted(baseUrl, repository, workflowId, PAGE_SIZE);
        return callUntilLastPageWrapped(url, new TypeReference<>() {
        }, GitHubWorkflowRunList::runs);
    }

    @Override
    public List<GitHubRelease> findAllReleases(String repository) {
        String url = "%s/repos/%s/releases?per_page=%d".formatted(baseUrl, repository, PAGE_SIZE);
        return callUntilLastPage(
                url,
                new TypeReference<>() {
                }
        );
    }

    private <W, T> List<T> callUntilLastPageWrapped(String url,
                                                    TypeReference<W> typeReference,
                                                    Function<W, List<T>> unwrap) {
        List<T> results = new ArrayList<>();
        while (url != null) {
            GitHubResponse<W> page = call(url, typeReference);
            if (page.data() != null) {
                results.addAll(unwrap.apply(page.data()));
            }
            url = page.nextUrl();
        }
        return results;
    }

    private <T> List<T> callUntilLastPage(String url, TypeReference<List<T>> typeReference) {
        return callUntilLastPageWrapped(url, typeReference, w -> w);
    }

    private <T> GitHubResponse<T> call(String url, TypeReference<T> typeReference) {
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/vnd.github+json")
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new GitHubException(response.code(), response.message());
            }
            if (response.body() == null) {
                return new GitHubResponse<>(null, null);
            }
            T data = mapper.readValue(response.body().string(), typeReference);
            return new GitHubResponse<>(data, nextUrl(response));
        } catch (IOException e) {
            throw new GitHubException(e);
        }
    }

    private String nextUrl(Response response) {
        String link = response.header("Link");
        if (link == null) {
            return null;
        }
        for (String part : link.split(",")) {
            String[] pieces = part.split(";");
            if (pieces.length < 2) {
                continue;
            }
            String url = pieces[0].trim();
            String rel = pieces[1].trim();
            if (rel.equals("rel=\"next\"")) {
                return url.substring(1, url.length() - 1);
            }
        }
        return null;
    }

}

# Tracking KPIs on CI/CD Pipelines (Standalone Setup)

As our CI/CD pipelines evolves, it becomes critical to monitor their health, speed, and reliability.

## Goals

- **Identify Flaky Pipelines**: Catch workflows with dropping success rates before they block major releases.
- **Optimize CI/CD Costs**: Spot workflows that take significantly longer than average, highlighting areas for catching
  or optimization.
- **Centralized Visibility**: Aggregate data from multiple repositories into a single, easy-to-read report.

## Components

The solution relies on two main components

### Script `workflows-stats.sh`

The script accepts two primary inputs: the Target Report Name and a List of GitHub Repositories.

- **Data Retrieval** : use the GitHub CLI (`gh`) to query
    - lists all workflows for each repository
    - fetches the last 100 runs per workflow
- **Data Processing** : using `jq` to parse the `JSON` result, we perform calculation of metrics such as
    - **Total runs**: count of completed attempts
    - **Success Rate %** :Ratio of successful vs. failed runs
    - **Average duration**: mean time from start to completion
- **Report Generation**: dynamically builds a `.md` file that a summary of the all information retrieved and processed

#### Example

```shell
./workflow-stats.sh report.md elieahd/metrics elieahd/my-docs`
```

Will generate the following Markdown file `my-report.md`

```md
*Generated on: Sun Mar 22 13:02:21 CET 2026*

## Repository: `elieahd/metrics`

| Workflow Name                 | Runs (Last 100) | Success Rate | Avg Duration |
|-------------------------------|-----------------|--------------|--------------|
| 🚀 Deploy                     | 2               | 0%           | 00:01:30     |
| ✅ PR checks                  | 21              | 61.9%        | 00:00:52     |
| Reusable Workflow - ✅ Checks | 0               | N/A          | N/A          |
| Dependabot Updates            | 6               | 100%         | 00:00:40     |

## Repository: `elieahd/my-docs`

| Workflow Name          | Runs (Last 100) | Success Rate | Avg Duration |
|------------------------|-----------------|--------------|--------------|
| 🚀 Deployment          | 99              | 86.87%       | 00:00:20     |
| Dependabot Updates     | 92              | 83.7%        | 00:00:45     |
| pages-build-deployment | 87              | 100%         | 00:00:28     |
```

### Orchestration using a GitHub Action Workflow

The workflow acts as the **scheduler**

- **Authentication**: login to GitHub via `gh`
- **Execution**: Triggers the script for a defined array of repositories
- **Delivery**: Publish the report

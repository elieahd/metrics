
## Global Arguments

Every invocation requires the following argument:

| Argument    | Required | Description                          |
|-------------|----------|--------------------------------------|
| `--command` | Yes      | The name of the command to execute   |

---

## Available Commands

### `generate-report`

Generates a development metrics report for a given project, using one or more Git repositories as input.

**Arguments:**

| Argument  | Required | Type             | Description                                      |
|-----------|----------|------------------|--------------------------------------------------|
| `--name`  | Yes      | String           | The name of the project                          |
| `--repos` | Yes      | String (repeatable) | Repository identifier(s). Pass multiple times to include more than one repo |

**Usage:**

```
--command=generate-report --name=<project> --repos=<repo1> --repos=<repo2>
```

**Example — single repository:**

```bash
java -jar metrics.jar \
  --command=generate-report \
  --name=my-service \
  --repos=my-org/my-service
```

**Example — multiple repositories:**

```bash
java -jar metrics.jar \
  --command=generate-report \
  --name=platform \
  --repos=my-org/service-a \
  --repos=my-org/service-b \
  --repos=my-org/service-c
```

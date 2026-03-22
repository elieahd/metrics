#!/bin/bash

# Validate arguments
if [ "$#" -lt 2 ]; then
  echo "Usage: $0 <output-markdown-file> <org/repo1> [<org/repo2> ...]"
  echo "Example: $0 report.md octocat/Hello-World octocat/Spoon-Knife"
  exit 1
fi

OUTPUT_FILE=$1
shift
REPOS=("$@")

# Initialize the output Markdown file
echo "*Generated on: $(date)*" >> "$OUTPUT_FILE"
echo "" >> "$OUTPUT_FILE"

# Helper function to convert seconds into HH:MM:SS format
format_duration() {
  local total_seconds=$1
  if [[ "$total_seconds" == "0" || -z "$total_seconds" ]]; then
    echo "N/A"
    return
  fi
  local h=$((total_seconds / 3600))
  local m=$(( (total_seconds % 3600) / 60 ))
  local s=$((total_seconds % 60))
  printf "%02d:%02d:%02d\n" $h $m $s
}

# Iterate through the provided repositories
for repo in "${REPOS[@]}"; do
  echo "Fetching data for '$repo...'"

  # Append repository header to markdown
  echo "## Repository: \`$repo\`" >> "$OUTPUT_FILE"
  echo "" >> "$OUTPUT_FILE"
  echo "| Workflow Name | Runs (Last 100) | Success Rate | Avg Duration |" >> "$OUTPUT_FILE"
  echo "|---|---|---|---|" >> "$OUTPUT_FILE"

  # Fetch all workflows for the repository
  # Mute errors just in case the repo doesn't exist or lacks permissions
  workflows_json=$(gh api "repos/$repo/actions/workflows" --paginate 2>/dev/null)

  if [ -z "$workflows_json" ]; then
    echo "| *Error fetching workflows* | - | - | - |" >> "$OUTPUT_FILE"
    echo "" >> "$OUTPUT_FILE"
    continue
  fi

  # Extract workflow IDs and names, and process each
  echo "$workflows_json" | jq -c '.workflows[] | {id: .id, name: .name}' | while read -r workflow; do
    wf_id=$(echo "$workflow" | jq -r '.id')
    wf_name=$(echo "$workflow" | jq -r '.name')

    # Fetch up to 100 recent runs for the specific workflow
    runs_stats=$(gh api "repos/$repo/actions/workflows/$wf_id/runs?per_page=100" 2>/dev/null | jq -r '
      [ .workflow_runs[]? | select(.status == "completed") ] |
      length as $total |
      if $total == 0 then
        "0\t0.00\t0"
      else
        (map(select(.conclusion == "success")) | length) as $success |
        (map(select(.updated_at != null and .run_started_at != null) | ((.updated_at | fromdateiso8601) - (.run_started_at | fromdateiso8601))) | add) as $total_duration |
        "\($total)\t\((($success / $total) * 100) * 100 | round / 100)\t\(($total_duration / $total) | round)"
      end
    ')

    # Default to 0s if API fails
    if [ -z "$runs_stats" ]; then
       runs_stats="0\t0.00\t0"
    fi

    # Extract parsed values
    total=$(echo "$runs_stats" | cut -f1)
    rate=$(echo "$runs_stats" | cut -f2)
    avg_dur_sec=$(echo "$runs_stats" | cut -f3)

    # Format values for markdown
    if [ "$total" -eq 0 ]; then
      rate_str="N/A"
      dur_str="N/A"
    else
      rate_str="${rate}%"
      dur_str=$(format_duration "$avg_dur_sec")
    fi

    # Write row to markdown file
    echo "| $wf_name | $total | $rate_str | $dur_str |" >> "$OUTPUT_FILE"
  done

  echo "" >> "$OUTPUT_FILE"
  echo "Finished processing $repo."
done

echo "Report successfully written to $OUTPUT_FILE"
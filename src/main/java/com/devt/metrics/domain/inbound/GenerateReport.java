package com.devt.metrics.domain.inbound;

import com.devt.metrics.domain.models.Project;

public interface GenerateReport {

    void perform(Project project);

}

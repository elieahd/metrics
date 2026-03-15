package com.devt.metrics.domain.inbound;

import com.devt.metrics.domain.models.ReportCommandRequest;

public interface GenerateReport {

    void perform(ReportCommandRequest request);

}

package com.devt.metrics.domain.outbound;

import com.devt.metrics.domain.models.reports.Report;

public interface ReportInventory {

    String store(Report report);

}

package com.devt.metrics.infrastructure.outbound.files;

import com.devt.metrics.domain.models.reports.Report;
import com.devt.metrics.domain.outbound.ReportInventory;
import com.devt.metrics.infrastructure.outbound.OutboundAdapter;

@OutboundAdapter
public class ReportInventoryFileAdapter implements ReportInventory {

    @Override
    public String store(Report report) {
        String filePath = "reports/%s.%s".formatted(report.name(), report.type());
        return FileHelper.store(filePath, report.content());
    }

}

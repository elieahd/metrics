package com.devt.metrics.infrastructure.inbound.cli;

import com.devt.metrics.domain.inbound.GenerateReport;
import com.devt.metrics.domain.models.ReportCommandRequest;
import com.devt.metrics.infrastructure.inbound.cli.exception.MissingArgException;
import com.devt.metrics.infrastructure.inbound.cli.helper.ApplicationArgumentsHelper;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GenerateReportCommand implements Command {

    private final GenerateReport generateReport;

    public GenerateReportCommand(GenerateReport generateReport) {
        this.generateReport = generateReport;
    }

    @Override
    public String name() {
        return "generate-report";
    }

    @Override
    public String usage() {
        return "--command=generate-report " +
                "--name=<project> " +
                "--repos=<repo1> " +
                "--repos=<repo2>";
    }

    @Override
    public void execute(ApplicationArguments args) throws MissingArgException {
        ApplicationArgumentsHelper arguments = ApplicationArgumentsHelper.of(args);

        String name = arguments
                .getArg("name")
                .orElseThrow(() -> new MissingArgException("name"));

        List<String> repositories = arguments
                .getArgs("repos")
                .orElseThrow(() -> new MissingArgException("repos"));

        ReportCommandRequest request = new ReportCommandRequest(name, repositories);
        generateReport.perform(request);
    }

}
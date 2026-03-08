package com.devt.metrics.infrastructure.inbound.cli;

import com.devt.metrics.infrastructure.inbound.cli.exception.MissingArgException;
import org.springframework.boot.ApplicationArguments;

public interface Command {

    String name();

    String usage();

    void execute(ApplicationArguments args) throws MissingArgException;

}
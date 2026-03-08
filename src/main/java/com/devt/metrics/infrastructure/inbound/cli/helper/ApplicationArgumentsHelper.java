package com.devt.metrics.infrastructure.inbound.cli.helper;

import org.springframework.boot.ApplicationArguments;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ApplicationArgumentsHelper {

    private final ApplicationArguments args;

    private ApplicationArgumentsHelper(ApplicationArguments args) {
        this.args = args;
    }

    public static ApplicationArgumentsHelper of(ApplicationArguments args) {
        return new ApplicationArgumentsHelper(args);
    }

    public boolean containsAllOptions(String... options) {
        return Arrays.stream(options).allMatch(args::containsOption);
    }

    public Optional<String> getArg(String argName) {
        return getArgs(argName).map(List::getFirst);
    }

    public Optional<List<String>> getArgs(String argName) {
        if (!args.containsOption(argName)) {
            return Optional.empty();
        }

        List<String> values = args.getOptionValues(argName);

        if (values == null || values.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(values);
    }

}
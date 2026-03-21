package com.devt.metrics.domain.services.helper;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class Formatter {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private Formatter() {
        // utility class shouldn't have public constructor
    }

    public static String format(OffsetDateTime input) {
        if (input == null) {
            return "";
        }
        return input.format(DATE_FORMATTER);
    }

    public static String format(Duration input) {
        if (input == null) {
            return "";
        }

        long days = input.toDaysPart();
        long hours = input.toHoursPart();
        long minutes = input.toMinutesPart();
        long seconds = input.toSecondsPart();

        if (days > 0) {
            return "%dd %dh %dmin %ds".formatted(days, hours, minutes, seconds);
        }

        if (hours > 0) {
            return "%dh %dmin %ds".formatted(hours, minutes, seconds);
        }

        if (minutes > 0) {
            return "%dmin %ds".formatted(minutes, seconds);
        }

        return "%ds".formatted(seconds);
    }

    public static String formatInDaysAndHours(Duration input) {
        if (input == null) {
            return "";
        }

        long days = input.toDaysPart();
        long hours = input.toHoursPart();

        return "%dd %dh".formatted(days, hours);
    }

}

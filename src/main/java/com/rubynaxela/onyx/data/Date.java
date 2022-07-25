package com.rubynaxela.onyx.data;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.WeekFields;

public class Date {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private final LocalDate data;

    private Date(@NotNull LocalDate data) {
        this.data = data;
    }

    public static Date valueOf(@NotNull String date) {
        return new Date(LocalDate.parse(date, DATE_FORMAT));
    }

    public static Date today() {
        return new Date(LocalDate.now());
    }

    public int getYear() {
        return data.get(ChronoField.YEAR);
    }

    public int getWeekOfYear() {
        return data.get(WeekFields.ISO.weekOfYear());
    }

    @Override
    public String toString() {
        return data.format(DATE_FORMAT);
    }
}

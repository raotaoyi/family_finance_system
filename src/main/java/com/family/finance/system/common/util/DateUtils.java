package com.family.finance.system.common.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 获取该年第一天 LocalDate
     */
    public static LocalDate getYearFirstLocalDate(String yearStr) {
        int year = Integer.parseInt(yearStr);
        return LocalDate.of(year, 1, 1);
    }

    /**
     * 获取该年最后一天 LocalDate
     */
    public static LocalDate getYearLastLocalDate(String yearStr) {
        int year = Integer.parseInt(yearStr);
        return LocalDate.of(year, 12, 31);
    }

    /**
     * 获取该年第一天字符串 yyyy‑MM‑dd
     */
    public static String getYearFirstDayStr(String yearStr) {
        return getYearFirstLocalDate(yearStr).format(DATE_FMT);
    }

    /**
     * 获取该年最后一天字符串 yyyy‑MM‑dd
     */
    public static String getYearLastDayStr(String yearStr) {
        return getYearLastLocalDate(yearStr).format(DATE_FMT);
    }
}
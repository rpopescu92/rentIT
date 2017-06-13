package com.rentIT.util;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public final class DateTimeFactory {

    public static final String pattern = "%s-%s-%s";

    public static String createNowDate() {
        ZonedDateTime now = ZonedDateTime.now();
        return getDate(now);
    }

    public static List<String> getMonthNames(int months) {
        ZonedDateTime now = ZonedDateTime.now();

        List<String> m = new ArrayList<>();
        for (int i = 1; i <= months; i++) {
            ZonedDateTime lastMonth = now.minusMonths(i);
            m.add(lastMonth.getMonth().name());
        }

        return m;
    }

    public static List<String> getMonthValues(int months) {
        ZonedDateTime now = ZonedDateTime.now();

        List<String> m = new ArrayList<>();
        for (int i = 1; i <= months; i++) {
            ZonedDateTime lastMonth = now.minusMonths(i);
            String[] s = getDate(lastMonth).split("-");
            String monthYear = s[1] + "-" + s[2];
            m.add(monthYear);
        }

        return m;
    }

    public static String createDateMinusMonths(int months) {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime last = now.minusMonths(months);
        return getDate(last);
    }

    private static String getDate(ZonedDateTime zonedDateTime) {
        String day = String.valueOf(zonedDateTime.getDayOfMonth());
        String month = String.valueOf(zonedDateTime.getMonthValue());
        String year = String.valueOf(zonedDateTime.getYear());

        return String.format(pattern, day, month, year);
    }

}

package com.rentIT.util;

import java.time.ZonedDateTime;

public final class DateTimeFactory {

    public static final String pattern = "%s-%s-%s";

    public static String createNowDate() {
        ZonedDateTime now = ZonedDateTime.now();

        String day = String.valueOf(now.getDayOfMonth());
        String month = String.valueOf(now.getMonthValue());
        String year = String.valueOf(now.getYear());

        return String.format(pattern, day, month, year);
    }


}

package com.tummoc.utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateFormatter {

    public static String formatDateWithWeek(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("E, dd MMM yy | HH:mm", Locale.ENGLISH));
    }

    public static String formatDateTime(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")).replaceAll("am", "AM").replaceAll("pm", "PM");
    }
}

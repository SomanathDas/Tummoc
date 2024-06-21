package com.tummoc.utility;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeZoneConversion {

    private static final ZoneId IST_ZONE_ID = ZoneId.of("Asia/Kolkata");
    private static final ZoneId UTC_ZONE_ID = ZoneId.of("UTC");
    //private static final DateFormatter DATE_FORMATTER = DateFormatter.formatDateTime()

    public static String convertOregonToIST(LocalDateTime oregonDateTime) {
        // Convert LocalDateTime in Oregon time zone to ZonedDateTime
        ZonedDateTime oregonZonedDateTime = oregonDateTime.atZone(UTC_ZONE_ID);

        // Convert ZonedDateTime from Oregon to IST
        ZonedDateTime istZonedDateTime = oregonZonedDateTime.withZoneSameInstant(IST_ZONE_ID);

        // Convert ZonedDateTime to LocalDateTime
        LocalDateTime istDateTime = istZonedDateTime.toLocalDateTime();

        // Convert LocalDateTime to String
        return DateFormatter.formatDateTime(istDateTime);
    }
}

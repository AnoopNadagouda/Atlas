package com.atlas.common.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public final class DateUtils {

    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter
            .ofPattern(AtlasConstants.ISO_DATETIME_FORMAT)
            .withZone(ZoneId.of(AtlasConstants.DEFAULT_TIMEZONE));

    private DateUtils() {}

    public static String formatIso(Instant instant) {
        if (instant == null) return null;
        return ISO_FORMATTER.format(instant);
    }

    public static Instant parseIso(String isoString) {
        if (StringUtils.isNullOrBlank(isoString)) return null;
        return Instant.from(ISO_FORMATTER.parse(isoString));
    }
}

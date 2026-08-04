package com.atlas.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RegexUtils {

    private static final Pattern URL_PATTERN = Pattern.compile(
            "^(https?|ftp)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]",
            Pattern.CASE_INSENSITIVE
    );

    private static final Pattern DOMAIN_PATTERN = Pattern.compile(
            "^(?:https?://)?(?:www\\.)?([^/:]+)",
            Pattern.CASE_INSENSITIVE
    );

    private RegexUtils() {}

    public static boolean isValidUrl(String url) {
        if (StringUtils.isNullOrBlank(url)) return false;
        return URL_PATTERN.matcher(url).matches();
    }

    public static String extractDomain(String url) {
        if (StringUtils.isNullOrBlank(url)) return "";
        Matcher matcher = DOMAIN_PATTERN.matcher(url);
        if (matcher.find()) {
            return matcher.group(1).toLowerCase();
        }
        return "";
    }
}

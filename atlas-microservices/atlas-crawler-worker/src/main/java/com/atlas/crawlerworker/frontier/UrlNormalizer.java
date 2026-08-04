package com.atlas.crawlerworker.frontier;

import com.atlas.common.utils.StringUtils;

import java.net.URI;
import java.net.URISyntaxException;

public final class UrlNormalizer {

    private UrlNormalizer() {}

    public static String normalize(String urlStr) {
        if (StringUtils.isNullOrBlank(urlStr)) return "";
        try {
            URI uri = new URI(urlStr.trim());

            // Remove fragment
            String scheme = uri.getScheme() != null ? uri.getScheme().toLowerCase() : "http";
            String host = uri.getHost() != null ? uri.getHost().toLowerCase() : "";
            int port = uri.getPort();

            if (("http".equals(scheme) && port == 80) || ("https".equals(scheme) && port == 443)) {
                port = -1; // Default ports stripped
            }

            String path = uri.getPath();
            if (path == null || path.isEmpty()) {
                path = "/";
            } else {
                path = path.replaceAll("//+", "/"); // Duplicate slashes removed
                if (path.length() > 1 && path.endsWith("/")) {
                    path = path.substring(0, path.length() - 1); // Trailing slash normalized
                }
            }

            String query = uri.getQuery();

            StringBuilder sb = new StringBuilder();
            sb.append(scheme).append("://").append(host);
            if (port != -1) {
                sb.append(":").append(port);
            }
            sb.append(path);
            if (query != null && !query.isEmpty()) {
                sb.append("?").append(query);
            }

            return sb.toString();
        } catch (URISyntaxException e) {
            return urlStr.trim();
        }
    }

    public static String resolveRelative(String baseUrl, String relativeUrl) {
        if (StringUtils.isNullOrBlank(relativeUrl)) return "";
        if (relativeUrl.startsWith("http://") || relativeUrl.startsWith("https://")) {
            return normalize(relativeUrl);
        }
        try {
            URI base = new URI(baseUrl);
            URI resolved = base.resolve(relativeUrl);
            return normalize(resolved.toString());
        } catch (Exception e) {
            return relativeUrl;
        }
    }
}

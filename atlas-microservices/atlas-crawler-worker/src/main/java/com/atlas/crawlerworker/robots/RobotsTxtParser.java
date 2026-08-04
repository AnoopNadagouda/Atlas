package com.atlas.crawlerworker.robots;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Data
public class RobotsTxtParser {

    @Data
    public static class UserAgentRules {
        private String userAgent = "*";
        private List<String> disallows = new ArrayList<>();
        private List<String> allows = new ArrayList<>();
        private long crawlDelayMs = 0;
    }

    private final List<UserAgentRules> rulesList = new ArrayList<>();
    private final List<String> sitemaps = new ArrayList<>();

    public static RobotsTxtParser parse(String robotsTxtContent) {
        RobotsTxtParser parser = new RobotsTxtParser();
        if (robotsTxtContent == null || robotsTxtContent.isBlank()) {
            return parser;
        }

        String[] lines = robotsTxtContent.split("\\r?\\n");
        UserAgentRules currentRules = null;

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("#")) continue;

            int colonIndex = line.indexOf(':');
            if (colonIndex <= 0) continue;

            String directive = line.substring(0, colonIndex).trim().toLowerCase(Locale.ENGLISH);
            String value = line.substring(colonIndex + 1).trim();

            if ("user-agent".equals(directive)) {
                currentRules = new UserAgentRules();
                currentRules.setUserAgent(value);
                parser.rulesList.add(currentRules);
            } else if (currentRules != null) {
                if ("disallow".equals(directive) && !value.isEmpty()) {
                    currentRules.getDisallows().add(value);
                } else if ("allow".equals(directive) && !value.isEmpty()) {
                    currentRules.getAllows().add(value);
                } else if ("crawl-delay".equals(directive)) {
                    try {
                        currentRules.setCrawlDelayMs((long) (Double.parseDouble(value) * 1000));
                    } catch (NumberFormatException ignored) {}
                }
            }

            if ("sitemap".equals(directive) && !value.isEmpty()) {
                parser.sitemaps.add(value);
            }
        }
        return parser;
    }

    public boolean isAllowed(String userAgent, String path) {
        if (path == null || path.isEmpty()) path = "/";

        UserAgentRules matchingRule = null;
        for (UserAgentRules rule : rulesList) {
            if (rule.getUserAgent().equalsIgnoreCase(userAgent) || "*".equals(rule.getUserAgent())) {
                matchingRule = rule;
                if (rule.getUserAgent().equalsIgnoreCase(userAgent)) break; // Explicit match preferred
            }
        }

        if (matchingRule == null) return true; // Default allow

        // Check explicit allows first
        for (String allow : matchingRule.getAllows()) {
            if (path.startsWith(allow)) return true;
        }

        // Check disallows
        for (String disallow : matchingRule.getDisallows()) {
            if (path.startsWith(disallow)) return false;
        }

        return true;
    }
}

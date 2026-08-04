package com.atlas.crawlerworker.sitemap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class SitemapParser {

    private static final Pattern LOC_PATTERN = Pattern.compile("<loc>(.*?)</loc>", Pattern.CASE_INSENSITIVE);

    public List<String> extractUrls(String sitemapXmlContent) {
        List<String> urls = new ArrayList<>();
        if (sitemapXmlContent == null || sitemapXmlContent.isBlank()) return urls;

        Matcher matcher = LOC_PATTERN.matcher(sitemapXmlContent);
        while (matcher.find()) {
            String url = matcher.group(1).trim();
            if (!url.isEmpty()) {
                urls.add(url);
            }
        }
        log.info("Extracted {} discovered URLs from sitemap XML", urls.size());
        return urls;
    }
}

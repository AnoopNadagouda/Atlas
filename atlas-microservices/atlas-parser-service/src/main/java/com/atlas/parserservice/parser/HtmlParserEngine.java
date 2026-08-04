package com.atlas.parserservice.parser;

import com.atlas.common.dto.ExtractedLinkDto;
import com.atlas.common.utils.RegexUtils;
import com.atlas.common.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class HtmlParserEngine {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ParsedHtmlData {
        private String title;
        private String description;
        private String keywords;
        private String canonicalUrl;
        private String cleanText;
        private List<String> headings;
        private List<ExtractedLinkDto> links;
        private String htmlLang;
        private String charset;
    }

    public ParsedHtmlData parse(String rawHtml, String sourceUrl) {
        if (StringUtils.isNullOrBlank(rawHtml)) {
            return ParsedHtmlData.builder()
                    .title("")
                    .description("")
                    .cleanText("")
                    .headings(List.of())
                    .links(List.of())
                    .build();
        }

        Document doc = Jsoup.parse(rawHtml, sourceUrl);

        // Extract metadata before stripping DOM
        String title = doc.title();
        if (StringUtils.isNullOrBlank(title)) {
            Element metaOgTitle = doc.selectFirst("meta[property=og:title]");
            if (metaOgTitle != null) title = metaOgTitle.attr("content");
        }

        String description = "";
        Element metaDesc = doc.selectFirst("meta[name=description]");
        if (metaDesc != null) description = metaDesc.attr("content");

        String keywords = "";
        Element metaKey = doc.selectFirst("meta[name=keywords]");
        if (metaKey != null) keywords = metaKey.attr("content");

        String canonicalUrl = "";
        Element linkCanonical = doc.selectFirst("link[rel=canonical]");
        if (linkCanonical != null) canonicalUrl = linkCanonical.attr("abs:href");

        String htmlLang = doc.selectFirst("html") != null ? doc.selectFirst("html").attr("lang") : "";

        // Extract Headings H1-H6
        List<String> headings = new ArrayList<>();
        Elements headingEls = doc.select("h1, h2, h3, h4, h5, h6");
        for (Element h : headingEls) {
            String text = h.text().trim();
            if (!text.isEmpty()) {
                headings.add(text);
            }
        }

        // Extract Hyperlinks before boilerplate removal
        List<ExtractedLinkDto> links = new ArrayList<>();
        String sourceDomain = RegexUtils.extractDomain(sourceUrl);

        Elements aEls = doc.select("a[href]");
        for (Element a : aEls) {
            String absUrl = a.attr("abs:href");
            String anchorText = a.text().trim();
            if (!absUrl.isEmpty() && !absUrl.startsWith("javascript:") && !absUrl.startsWith("#")) {
                String targetDomain = RegexUtils.extractDomain(absUrl);
                boolean isExternal = !sourceDomain.isEmpty() && !sourceDomain.equalsIgnoreCase(targetDomain);

                links.add(ExtractedLinkDto.builder()
                        .sourceUrl(sourceUrl)
                        .targetUrl(absUrl)
                        .anchorText(anchorText)
                        .isExternal(isExternal)
                        .build());
            }
        }

        // --- BOILERPLATE REMOVAL ---
        doc.select("script, style, noscript, iframe, svg, header, footer, nav, aside, .nav, .footer, .menu, .sidebar, .ad, .banner, .cookie, .popup, .social").remove();

        // Extract clean text
        String bodyText = doc.body() != null ? doc.body().text() : doc.text();
        String normalizedText = normalizeText(bodyText);

        return ParsedHtmlData.builder()
                .title(title != null ? title.trim() : "")
                .description(description != null ? description.trim() : "")
                .keywords(keywords != null ? keywords.trim() : "")
                .canonicalUrl(canonicalUrl != null ? canonicalUrl.trim() : "")
                .cleanText(normalizedText)
                .headings(headings)
                .links(links)
                .htmlLang(htmlLang != null ? htmlLang.trim() : "")
                .build();
    }

    private String normalizeText(String text) {
        if (text == null) return "";
        // Unicode NFC Normalization
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFC);
        // Replace non-printable control characters
        normalized = normalized.replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "");
        // Normalize multiple spaces into single space
        return normalized.replaceAll("\\s+", " ").trim();
    }
}

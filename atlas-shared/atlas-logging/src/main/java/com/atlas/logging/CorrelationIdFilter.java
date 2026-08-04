package com.atlas.logging;

import com.atlas.common.utils.AtlasConstants;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CorrelationIdFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest httpRequest && response instanceof HttpServletResponse httpResponse) {
            String correlationId = httpRequest.getHeader(AtlasConstants.CORRELATION_ID_HEADER);
            if (correlationId == null || correlationId.isBlank()) {
                correlationId = CorrelationIdContext.generateNewCorrelationId();
            }
            CorrelationIdContext.setCorrelationId(correlationId);
            httpResponse.setHeader(AtlasConstants.CORRELATION_ID_HEADER, correlationId);
            try {
                chain.doFilter(request, response);
            } finally {
                CorrelationIdContext.clear();
            }
        } else {
            chain.doFilter(request, response);
        }
    }
}

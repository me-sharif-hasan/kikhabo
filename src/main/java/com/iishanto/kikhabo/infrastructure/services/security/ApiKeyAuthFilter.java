package com.iishanto.kikhabo.infrastructure.services.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    private static final String ADMIN_PATH_PREFIX = "/api/v1/admin/";
    private static final String API_KEY_HEADER = "X-Api-Key";

    private final String masterApiKey;
    private final Logger logger;

    public ApiKeyAuthFilter(
            @Value("${kikhabo.admin.api-key}") String masterApiKey,
            Logger logger) {
        this.masterApiKey = masterApiKey;
        this.logger = logger;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        if (!request.getRequestURI().startsWith(ADMIN_PATH_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        String providedKey = request.getHeader(API_KEY_HEADER);
        if (providedKey == null || !providedKey.equals(masterApiKey)) {
            logger.warn("[AdminAuth] Rejected request to {} — invalid or missing X-Api-Key", request.getRequestURI());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"status\":\"error\",\"message\":\"Invalid or missing X-Api-Key\"}");
            return;
        }

        logger.debug("[AdminAuth] Authorised admin request to {}", request.getRequestURI());
        chain.doFilter(request, response);
    }
}

package com.example.demo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.logging.Logger;

@Component
public class RateLimitingFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(RateLimitingFilter.class);

    @Autowired
    private RateLimiter rateLimiter;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (rateLimiter.getCurrentRequests().incrementAndGet() > rateLimiter.getRateLimit()) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setHeader("X-Rate-Limit", String.valueOf(rateLimiter.getRateLimit()));
            long waitTime = (rateLimiter.getCurrentRequests().get() - rateLimiter.getRateLimit()) * 1000;
            response.setHeader("X-Wait-Till", Instant.now().plusMillis(waitTime).toString());
            LOGGER.info("Rate limit reached. Returning HTTP {}");
            return;
        }

        filterChain.doFilter(request, response);
    }


}

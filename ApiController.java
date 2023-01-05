package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private RateLimiter rateLimiter;

    @GetMapping("/request-count")
    public ResponseEntity<Map<String, Long>> getRequestCount() {
        Map<String, Long> response = new HashMap<>();
        response.put("requestCount", rateLimiter.getCurrentRequests().get());
        response.put("rateLimit", rateLimiter.getRateLimit());
        return ResponseEntity.ok(response);
    }
}

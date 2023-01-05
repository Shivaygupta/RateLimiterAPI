package com.example.demo;

import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.atomic.AtomicLong;

public class RateLimiter {

    private AtomicLong currentRequests;

    @Value("${rate.limit}")
    private Long rateLimit;

    public RateLimiter(Long rateLimit) {
        this.currentRequests = new AtomicLong(0);
        this.rateLimit = rateLimit;
    }

    public Long getRateLimit() {
        return rateLimit;
    }

    public void setRateLimit(Long rateLimit) {
        this.rateLimit = rateLimit;
    }

    public AtomicLong getCurrentRequests() {
        return currentRequests;
    }
}

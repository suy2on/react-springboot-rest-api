package com.example.springreactprac.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderItem (
        UUID productId,
        Category category,
        long price,
        long quantity,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}

package com.dachi.sellerapp.dto;

import java.time.LocalDateTime;

public record CommentDTO(
        Long id,
        Long authorId,    // nullable if anonymous
        Long sellerId,
        String message,
        int rating,       // 0–10
        LocalDateTime createdAt
) {}

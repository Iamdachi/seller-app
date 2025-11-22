package com.dachi.sellerapp.dto;

import java.time.LocalDateTime;

public record GameObjectDTO(
        Long id,
        String title,
        String text,
        Long userId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}

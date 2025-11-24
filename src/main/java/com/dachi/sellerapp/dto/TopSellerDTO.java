package com.dachi.sellerapp.dto;

public record TopSellerDTO(
        Long sellerId,
        String firstName,
        String lastName,
        double avgRating,
        int commentCount
) {}

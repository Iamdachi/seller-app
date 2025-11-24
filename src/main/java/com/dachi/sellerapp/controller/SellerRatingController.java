package com.dachi.sellerapp.controller;

import com.dachi.sellerapp.dto.TopSellerDTO;
import com.dachi.sellerapp.service.SellerRatingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sellers")
public class SellerRatingController {

    private final SellerRatingService sellerRatingService;

    public SellerRatingController(SellerRatingService sellerRatingService) {
        this.sellerRatingService = sellerRatingService;
    }

    /**
     * GET /sellers/top
     * Retrieve the top sellers ordered by average rating.
     *
     * Optional query parameter:
     *   - limit (int) : maximum number of top sellers to return (default 10)
     *
     * Example:
     *   /sellers/top?limit=5
     */
    @GetMapping("/top")
    public List<TopSellerDTO> getTopSellers(@RequestParam(required = false, defaultValue = "10") int limit) {
        return sellerRatingService.getTopSellers(limit);
    }
}

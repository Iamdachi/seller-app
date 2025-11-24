package com.dachi.sellerapp.service;

import com.dachi.sellerapp.dto.TopSellerDTO;
import com.dachi.sellerapp.model.SellerRating;
import com.dachi.sellerapp.model.User;
import com.dachi.sellerapp.repository.SellerRatingRepository;
import com.dachi.sellerapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SellerRatingService {

    private final SellerRatingRepository sellerRatingRepository;
    private final UserRepository userRepository;

    public SellerRatingService(SellerRatingRepository sellerRatingRepository,
                               UserRepository userRepository) {
        this.sellerRatingRepository = sellerRatingRepository;
        this.userRepository = userRepository;
    }

    public List<TopSellerDTO> getTopSellers(int limit) {
        return sellerRatingRepository.findAll().stream()
                .sorted((a, b) -> Double.compare(b.getAvgRating(), a.getAvgRating()))
                .limit(limit)
                .map(r -> {
                    User user = userRepository.findById(r.getSellerId()).orElse(null);
                    return new TopSellerDTO(user.getId(), user.getFirstName(), user.getLastName(), r.getAvgRating(), r.getCommentCount());
                })
                .collect(Collectors.toList());
    }
}

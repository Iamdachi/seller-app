package com.dachi.sellerapp.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "seller_ratings")
public class SellerRating {

    @Id
    @Column(name = "seller_id")
    private Long sellerId;   // same as user.id

    @Column(name = "avg_rating", nullable = false)
    private double avgRating;

    @Column(name = "comment_count", nullable = false)
    private int commentCount;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public SellerRating() {}

    public SellerRating(Long sellerId, double avgRating, int commentCount) {
        this.sellerId = sellerId;
        this.avgRating = avgRating;
        this.commentCount = commentCount;
        this.updatedAt = LocalDateTime.now();
    }

    // GETTERS
    public Long getSellerId() { return sellerId; }

    public double getAvgRating() { return avgRating; }

    public int getCommentCount() { return commentCount; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // SETTERS
    public void setSellerId(Long sellerId) { this.sellerId = sellerId; }

    public void setAvgRating(double avgRating) { this.avgRating = avgRating; }

    public void setCommentCount(int commentCount) { this.commentCount = commentCount; }

    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}

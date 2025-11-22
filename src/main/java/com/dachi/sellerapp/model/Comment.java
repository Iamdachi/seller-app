package com.dachi.sellerapp.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private int rating; // 0–10

    @ManyToOne(optional = true)
    @JoinColumn(name = "author_id")
    private User author; // nullable for anonymous users

    @ManyToOne(optional = false)
    @JoinColumn(name = "seller_id")
    private User seller; // must always point to seller

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Comment() {}

    public Comment(String message, int rating, User author, User seller) {
        this.message = message;
        this.rating = rating;
        this.author = author;
        this.seller = seller;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and setters
    public Long getId() { return id; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public User getAuthor() { return author; }
    public void setAuthor(User author) { this.author = author; }

    public User getSeller() { return seller; }
    public void setSeller(User seller) { this.seller = seller; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

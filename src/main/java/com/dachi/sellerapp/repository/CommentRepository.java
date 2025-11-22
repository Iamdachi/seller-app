package com.dachi.sellerapp.repository;

import com.dachi.sellerapp.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findBySellerId(Long sellerId);
}

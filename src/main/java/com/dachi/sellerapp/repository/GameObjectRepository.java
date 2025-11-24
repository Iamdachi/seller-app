package com.dachi.sellerapp.repository;

import com.dachi.sellerapp.model.GameObject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameObjectRepository extends JpaRepository<GameObject, Long> {
    List<GameObject> findByGameId(Long gameId);
}

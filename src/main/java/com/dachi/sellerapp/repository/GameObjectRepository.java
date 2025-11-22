package com.dachi.sellerapp.repository;

import com.dachi.sellerapp.model.GameObject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameObjectRepository extends JpaRepository<GameObject, Long> {}

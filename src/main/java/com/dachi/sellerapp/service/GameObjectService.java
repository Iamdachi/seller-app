package com.dachi.sellerapp.service;

import com.dachi.sellerapp.dto.GameObjectDTO;
import com.dachi.sellerapp.model.GameObject;
import com.dachi.sellerapp.model.User;
import com.dachi.sellerapp.repository.GameObjectRepository;
import com.dachi.sellerapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameObjectService {

    private final GameObjectRepository gameObjectRepo;
    private final UserRepository userRepo;

    public GameObjectService(GameObjectRepository gameObjectRepo, UserRepository userRepo) {
        this.gameObjectRepo = gameObjectRepo;
        this.userRepo = userRepo;
    }

    // Convert entity -> DTO
    private GameObjectDTO toDTO(GameObject obj) {
        return new GameObjectDTO(
                obj.getId(),
                obj.getTitle(),
                obj.getText(),
                obj.getUser().getId(),
                obj.getCreatedAt(),
                obj.getUpdatedAt()
        );
    }

    public GameObjectDTO createObject(Long userId, String title, String text) {
        User author = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        GameObject obj = new GameObject();
        obj.setTitle(title);
        obj.setText(text);
        obj.setUser(author);
        obj.setCreatedAt(LocalDateTime.now());
        obj.setUpdatedAt(LocalDateTime.now());

        return toDTO(gameObjectRepo.save(obj));
    }

    public List<GameObjectDTO> getObjects() {
        return gameObjectRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public GameObjectDTO updateObject(Long objectId, Long userId, String title, String text) {
        GameObject obj = gameObjectRepo.findById(objectId)
                .orElseThrow(() -> new RuntimeException("Object not found"));

        if (!obj.getUser().getId().equals(userId)) throw new RuntimeException("Not the author");

        obj.setTitle(title);
        obj.setText(text);
        obj.setUpdatedAt(LocalDateTime.now());

        return toDTO(gameObjectRepo.save(obj));
    }

    public void deleteObject(Long objectId, Long userId) {
        GameObject obj = gameObjectRepo.findById(objectId)
                .orElseThrow(() -> new RuntimeException("Object not found"));

        if (!obj.getUser().getId().equals(userId)) throw new RuntimeException("Not the author");

        gameObjectRepo.delete(obj);
    }
}

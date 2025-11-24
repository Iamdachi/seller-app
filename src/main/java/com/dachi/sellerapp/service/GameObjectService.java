package com.dachi.sellerapp.service;

import com.dachi.sellerapp.dto.GameObjectDTO;
import com.dachi.sellerapp.model.GameObject;
import com.dachi.sellerapp.model.SellerRating;
import com.dachi.sellerapp.model.User;
import com.dachi.sellerapp.repository.GameObjectRepository;
import com.dachi.sellerapp.repository.SellerRatingRepository;
import com.dachi.sellerapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameObjectService {

    private final GameObjectRepository gameObjectRepo;
    private final UserRepository userRepo;
    private final SellerRatingRepository sellerRatingRepo;

    public GameObjectService(GameObjectRepository gameObjectRepo, UserRepository userRepo, SellerRatingRepository sellerRatingRepo) {
        this.gameObjectRepo = gameObjectRepo;
        this.userRepo = userRepo;
        this.sellerRatingRepo = sellerRatingRepo;
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

    public List<GameObject> filterByGame(Long gameId) {
        return gameObjectRepo.findByGameId(gameId);
    }

    public List<GameObject> filterByGameAndRating(Long gameId, int minRating, int maxRating) {
        List<GameObject> objects = gameObjectRepo.findByGameId(gameId);

        return objects.stream()
                .filter(obj -> {
                    SellerRating rating = sellerRatingRepo.findById(obj.getUser().getId()).orElse(null);

                    if (rating == null) return false;

                    double avg = rating.getAvgRating();

                    return avg >= minRating && avg <= maxRating;
                })
                .collect(Collectors.toList());
    }

}

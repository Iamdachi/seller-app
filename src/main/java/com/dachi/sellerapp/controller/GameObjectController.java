package com.dachi.sellerapp.controller;

import com.dachi.sellerapp.dto.GameObjectDTO;
import com.dachi.sellerapp.model.GameObject;
import com.dachi.sellerapp.repository.GameObjectRepository;
import com.dachi.sellerapp.service.GameObjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/object")
public class GameObjectController {

    private final GameObjectService gameObjectService;
    private final GameObjectRepository repo;

    public GameObjectController(GameObjectService gameObjectService, GameObjectRepository repo) {
        this.gameObjectService = gameObjectService;
        this.repo = repo;
    }

    /**
     * POST /object
     * Create a new game object.
     */
    @PostMapping
    public GameObjectDTO create(@RequestBody Map<String, String> body) {
        Long userId = Long.valueOf(body.get("userId"));
        String title = body.get("title");
        String text = body.get("text");
        return gameObjectService.createObject(userId, title, text);
    }

    /**
     * GET /object
     * Retrieve all game objects.
     */
    @GetMapping
    public List<GameObjectDTO> list() {
        return gameObjectService.getObjects();
    }

    /**
     * PUT /object/{id}
     * Update an existing game object (only author can update).
     */
    @PutMapping("/{id}")
    public GameObjectDTO update(@PathVariable Long id,
                                @RequestBody Map<String, String> body) {
        Long userId = Long.valueOf(body.get("userId"));
        String title = body.get("title");
        String text = body.get("text");
        return gameObjectService.updateObject(id, userId, title, text);
    }

    /**
     * DELETE /object/{id}
     * Delete a game object (only author can delete).
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id,
                         @RequestParam Long userId) {
        gameObjectService.deleteObject(id, userId);
        return "deleted";
    }

    /**
     * GET /object/filter/{gameId}
     * Filter game objects by game ID.
     *
     * Optional query parameters:
     *   - minRating
     *   - maxRating
     *
     * Example:
     *   /object/filter/12?minRating=3&maxRating=5
     */
    @GetMapping("/filter")
    public List<GameObject> filterByGame(
            @RequestParam Long gameId,
            @RequestParam(required = false) Integer minRating,
            @RequestParam(required = false) Integer maxRating
    ) {

        if (minRating != null && maxRating != null) {
            return gameObjectService.filterByGameAndRating(gameId, minRating, maxRating);
        }

        return gameObjectService.filterByGame(gameId);
    }


}

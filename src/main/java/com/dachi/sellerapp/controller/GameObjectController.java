package com.dachi.sellerapp.controller;

import com.dachi.sellerapp.dto.GameObjectDTO;
import com.dachi.sellerapp.service.GameObjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/object")
public class GameObjectController {

    private final GameObjectService gameObjectService;

    public GameObjectController(GameObjectService gameObjectService) {
        this.gameObjectService = gameObjectService;
    }

    @PostMapping
    public GameObjectDTO create(@RequestBody Map<String, String> body) {
        Long userId = Long.valueOf(body.get("userId"));
        String title = body.get("title");
        String text = body.get("text");
        return gameObjectService.createObject(userId, title, text);
    }

    @GetMapping
    public List<GameObjectDTO> list() {
        return gameObjectService.getObjects();
    }

    @PutMapping("/{id}")
    public GameObjectDTO update(@PathVariable Long id,
                                @RequestBody Map<String, String> body) {
        Long userId = Long.valueOf(body.get("userId"));
        String title = body.get("title");
        String text = body.get("text");
        return gameObjectService.updateObject(id, userId, title, text);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id,
                         @RequestParam Long userId) {
        gameObjectService.deleteObject(id, userId);
        return "deleted";
    }
}

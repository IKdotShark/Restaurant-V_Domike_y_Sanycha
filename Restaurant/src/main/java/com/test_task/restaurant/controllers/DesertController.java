package com.test_task.restaurant.controllers;

import com.test_task.restaurant.models.Desert;
import com.test_task.restaurant.services.DesertService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/deserts")
public class DesertController {

    private final DesertService desertService;

    public DesertController(DesertService desertService) {
        this.desertService = desertService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Desert> findDesertById(@PathVariable Long id) {
        Desert desert = desertService.findDesertById(id);
        if (desert.getIngredients() != null) {
            List<String> ingredientsList = Arrays.asList(desert.getIngredients().split(","));
            desert.setTransientIngredients(ingredientsList);
        }
        return ResponseEntity.ok(desert);
    }

    @GetMapping()
    public ResponseEntity<List<Desert>> findAllDeserts() {
        List<Desert> deserts = desertService.findAllDeserts();
        return ResponseEntity.ok(deserts);
    }

    @PostMapping()
    public ResponseEntity<Desert> createDesert(@RequestBody Desert desert) {
        Desert savedDesert = desertService.createDesert(desert);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDesert);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Desert> updateDesert(@RequestBody Desert desertInfo, @PathVariable Long id) {
        Desert desert = desertService.findDesertById(id);
        Desert updatedDesert = desertService.updateDesert(desert, desertInfo);
        return ResponseEntity.ok(updatedDesert);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Desert> deleteDesert(@PathVariable Long id) {
        desertService.deleteDesertById(id);
        return ResponseEntity.noContent().build();
    }
}

package com.test_task.restaurant.controllers;

import com.test_task.restaurant.models.Dish;
import com.test_task.restaurant.services.DishService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/dishes")
public class DishController {

    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dish> findDishById(@PathVariable Long id) {
        Dish dish = dishService.findDishById(id);
        if (dish.getIngredients() != null) {
            List<String> ingredientsList = Arrays.asList(dish.getIngredients().split(","));
            dish.setTransientIngredients(ingredientsList);
        }
        return ResponseEntity.ok(dish);
    }

    @GetMapping()
    public ResponseEntity<List<Dish>> findAllDishes() {
        List<Dish> dishes = dishService.findAllDishes();
        return ResponseEntity.ok(dishes);
    }

    @PostMapping()
    public ResponseEntity<Dish> createDish(@RequestBody Dish dish) {
        Dish savedDish = dishService.createDish(dish);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDish);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Dish> updateDish(@RequestBody Dish dishInfo, @PathVariable Long id) {
        Dish dish = dishService.findDishById(id);
        Dish updatedDish = dishService.updateDish(dish, dishInfo);
        return ResponseEntity.ok(updatedDish);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDish(@PathVariable Long id) {
        dishService.deleteDishById(id);
        return ResponseEntity.noContent().build();
    }
}

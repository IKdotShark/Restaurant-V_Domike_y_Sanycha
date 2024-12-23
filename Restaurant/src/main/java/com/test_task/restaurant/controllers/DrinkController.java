package com.test_task.restaurant.controllers;

import com.test_task.restaurant.models.Drink;
import com.test_task.restaurant.services.DrinkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drinks")
public class DrinkController {

    private final DrinkService drinkService;

    public DrinkController(DrinkService drinkService) {
        this.drinkService = drinkService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Drink> findDrinkById(@PathVariable Long id) {
        Drink drink = drinkService.findDrinkById(id);
        return ResponseEntity.ok(drink);
    }

    @GetMapping()
    public ResponseEntity<List<Drink>> findAllDrinks() {
        List<Drink> drinks = drinkService.findAllDrinks();
        return ResponseEntity.ok(drinks);
    }

    @PostMapping()
    public ResponseEntity<Drink> createDrink(@RequestBody Drink drink) {
        Drink savedDrink = drinkService.createDrink(drink);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDrink);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Drink> updateDrink(@RequestBody Drink drinkInfo, @PathVariable Long id) {
        Drink drink = drinkService.findDrinkById(id);
        Drink updatedDrink = drinkService.updateDrink(drink, drinkInfo);
        return ResponseEntity.ok(updatedDrink);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDrink(@PathVariable Long id) {
        drinkService.deleteDrinkById(id);
        return ResponseEntity.noContent().build();
    }
}

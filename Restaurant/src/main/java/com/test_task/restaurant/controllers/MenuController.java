package com.test_task.restaurant.controllers;

import com.test_task.restaurant.models.Desert;
import com.test_task.restaurant.models.Dish;
import com.test_task.restaurant.models.Drink;
import com.test_task.restaurant.models.Menu;
import com.test_task.restaurant.services.MenuService;
import com.test_task.restaurant.services.DishService;
import com.test_task.restaurant.services.DrinkService;
import com.test_task.restaurant.services.DesertService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private final MenuService menuService;
    private final DishService dishService;
    private final DrinkService drinkService;
    private final DesertService desertService;

    public MenuController(MenuService menuService, DishService dishService, DrinkService drinkService, DesertService desertService) {
        this.menuService = menuService;
        this.dishService = dishService;
        this.drinkService = drinkService;
        this.desertService = desertService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Menu> findMenuById(@PathVariable Long id) {
        Menu menu = menuService.findMenuById(id);
        menuService.convertStringIngrToArray(menu);
        return ResponseEntity.ok(menu);
    }

    @GetMapping
    public ResponseEntity<List<Menu>> getAllMenuByCategory(@RequestParam(required = false) String category) {
        List<Menu> menus = menuService.findAllItemsByCategory(category);

        if (menus.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(menus);
    }

    @PostMapping
    public ResponseEntity<Menu> createMenu(@RequestBody Menu menuRequest) {
        Menu createdMenu = menuService.createMenu(menuRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMenu);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Menu> updateMenu(@RequestBody Menu menuInfo, @PathVariable Long id) {
        Menu menu = menuService.findMenuById(id);
        Menu menuIdAdder = menuService.menuIdAdder(menuInfo, menu);
        Menu updatedMenu = menuService.updateMenu(menuIdAdder);
        return ResponseEntity.ok(updatedMenu);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Menu> deleteMenu(@PathVariable Long id) {
        menuService.deleteMenuById(id);
        return ResponseEntity.noContent().build();
    }
}

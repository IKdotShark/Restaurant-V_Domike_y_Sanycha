package com.test_task.restaurant.services;

import com.test_task.restaurant.exception.ResourceNotFoundException;
import com.test_task.restaurant.models.Desert;
import com.test_task.restaurant.models.Dish;
import com.test_task.restaurant.repositories.DishRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class DishService {

    private final DishRepository dishRepository;

    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public Dish createDish(Dish dish) {
        if (dish.getTransientIngredients() != null) {
            String ingredients = String.join(",", dish.getTransientIngredients());
            dish.setIngredients(ingredients);
        }
        return dishRepository.save(dish);
    }

    public Dish findDishById(Long id) {
        return dishRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found dish with such " + id));
    }

    public List<Dish> findDishesByIds(List<Long> ids) {
        List<Dish> dishes = new ArrayList<>();
        for (Long id : ids) {
            dishRepository.findById(id).ifPresent(dish -> {
                if (dish.getIngredients() != null) {
                    List<String> ingredientsList = Arrays.asList(dish.getIngredients().split(","));
                    dish.setTransientIngredients(ingredientsList);
                }
                dishes.add(dish);
            });
        }
        return dishes;
    }

    public List<Dish> findAllDishes() {
        return dishRepository.findAll();
    }

    public void deleteDishById(Long id) {
        Optional<Dish> dish = dishRepository.findById(id);
        if (dish.isEmpty()) throw new ResourceNotFoundException("Not found dish with such " + id);
        dishRepository.deleteById(id);
    }

    public Dish updateDish(Dish dish, Dish dishInfo) {
        if (dishInfo.getName() != null) {
            dish.setName(dishInfo.getName());
        }

        if (dishInfo.getPrice() != dish.getPrice()) {
            dish.setPrice(dishInfo.getPrice());
        }

        if (dishInfo.getTransientIngredients() != null) {
            String ingredients = String.join(",", dishInfo.getTransientIngredients());
            dish.setIngredients(ingredients);
            dish.setTransientIngredients(dishInfo.getTransientIngredients());
        } else {
            List<String> ingredientsList = Arrays.asList(dish.getIngredients().split(","));
            dish.setTransientIngredients(ingredientsList);
        }

        if (dishInfo.getDescription() != null) {
            dish.setDescription(dishInfo.getDescription());
        }

        if (dishInfo.getCategory() != null) {
            dish.setCategory(dishInfo.getCategory());
        }

        if (dishInfo.getSrc() != null) {
            dish.setSrc(dishInfo.getSrc());
        }
        return dishRepository.save(dish);
    }
}
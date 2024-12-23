package com.test_task.restaurant.services;

import com.test_task.restaurant.exception.ResourceNotFoundException;
import com.test_task.restaurant.models.Desert;
import com.test_task.restaurant.models.Drink;
import com.test_task.restaurant.repositories.DrinkRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DrinkService {

    private final DrinkRepository drinkRepository;

    public DrinkService(DrinkRepository drinkRepository) {
        this.drinkRepository = drinkRepository;
    }

    public Drink createDrink(Drink drink) {
        return drinkRepository.save(drink);
    }

    public Drink findDrinkById(Long id) {
        return drinkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found drink with such " + id));
    }

    public List<Drink> findDrinksByIds(List<Long> ids) {
        List<Drink> drinks = new ArrayList<>();
        for (Long id : ids) {
            Optional<Drink> drink = drinkRepository.findById(id);
            drink.ifPresent(drinks::add);
        }
        return drinks;
    }

    public List<Drink> findAllDrinks() {
        return drinkRepository.findAll();
    }

    public void deleteDrinkById(Long id) {
        Optional<Drink> drink = drinkRepository.findById(id);
        if (drink.isEmpty()) throw new ResourceNotFoundException("Not found drink with such " + id);
        drinkRepository.deleteById(id);
    }

    public Drink updateDrink(Drink drink, Drink drinkInfo) {
        if (drinkInfo.getName() != null) {
            drink.setName(drinkInfo.getName());
        }

        if (drinkInfo.getPrice() != drink.getPrice()) {
            drink.setPrice(drinkInfo.getPrice());
        }

        if (drinkInfo.getDescription() != null) {
            drink.setDescription(drinkInfo.getDescription());
        }

        if (drinkInfo.getCategory() != null) {
            drink.setCategory(drinkInfo.getCategory());
        }

        if (drinkInfo.getSrc() != null) {
            drink.setSrc(drinkInfo.getSrc());
        }

        return drinkRepository.save(drink);
    }
}
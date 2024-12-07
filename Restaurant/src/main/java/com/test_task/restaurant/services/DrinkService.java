package com.test_task.restaurant.services;

import com.test_task.restaurant.exception.ResourceNotFoundException;
import com.test_task.restaurant.models.Drink;
import com.test_task.restaurant.repositories.DrinkRepository;
import org.springframework.stereotype.Service;

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

    public List<Drink> findAllDrinks() {
        return drinkRepository.findAll();
    }

    public void deleteDrinkByid(Long id) {
        Optional<Drink> drink = drinkRepository.findById(id);
        if (drink.isEmpty()) throw new ResourceNotFoundException("Not found drink with such " + id);
        drinkRepository.deleteById(id);
    }
}
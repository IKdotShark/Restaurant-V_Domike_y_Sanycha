package com.test_task.restaurant.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    private List<Long> dishesIds;

    @ElementCollection
    private List<Long> drinksIds;

    @ElementCollection
    private List<Long> desertsIds;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private List<Dish> dishes;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private List<Drink> drinks;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private List<Desert> deserts;

    private Menu() {
    }

    private static final class InstanceHolder {
        private static final Menu instance = new Menu();
    }

    public static Menu getInstance() {
        return InstanceHolder.instance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getDishesIds() {
        return dishesIds;
    }

    public void setDishesIds(List<Long> dishesIds) {
        this.dishesIds = dishesIds;
    }

    public List<Long> getDrinksIds() {
        return drinksIds;
    }

    public void setDrinksIds(List<Long> drinksIds) {
        this.drinksIds = drinksIds;
    }

    public List<Long> getDesertsIds() {
        return desertsIds;
    }

    public void setDesertsIds(List<Long> desertsIds) {
        this.desertsIds = desertsIds;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public List<Drink> getDrinks() {
        return drinks;
    }

    public void setDrinks(List<Drink> drinks) {
        this.drinks = drinks;
    }

    public List<Desert> getDeserts() {
        return deserts;
    }

    public void setDeserts(List<Desert> deserts) {
        this.deserts = deserts;
    }
}

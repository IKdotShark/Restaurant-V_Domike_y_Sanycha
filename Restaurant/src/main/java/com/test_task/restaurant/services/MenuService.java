package com.test_task.restaurant.services;

import com.test_task.restaurant.exception.ResourceNotFoundException;
import com.test_task.restaurant.models.Desert;
import com.test_task.restaurant.models.Dish;
import com.test_task.restaurant.models.Drink;
import com.test_task.restaurant.models.Menu;
import com.test_task.restaurant.repositories.DesertRepository;
import com.test_task.restaurant.repositories.DishRepository;
import com.test_task.restaurant.repositories.DrinkRepository;
import com.test_task.restaurant.repositories.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final DishService dishService;
    private final DesertService desertService;
    private final DrinkService drinkService;

    public MenuService(MenuRepository menuRepository,
                       DishService dishService,
                       DesertService desertService,
                       DrinkService drinkService) {
        this.menuRepository = menuRepository;
        this.dishService = dishService;
        this.desertService = desertService;
        this.drinkService = drinkService;
    }

    public Menu findMenuById(Long id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found menu with such id: " + id));
    }

    public List<Menu> findAllMenus() {
        return menuRepository.findAll();
    }

    public List<Menu> findAllItemsByCategory(String category) {
        if (category == null) {
            return findAllMenus();
        }

        return switch (category.toLowerCase()) {
            case "dishes" -> findMenusWithDishes();
            case "drinks" -> findMenusWithDrinks();
            case "deserts" -> findMenusWithDeserts();
            default -> findAllMenus();
        };
    }

    private List<Menu> findMenusWithDishes() {
        return findAllMenus().stream()
                .filter(menu -> !menu.getDishes().isEmpty())
                .collect(Collectors.toList());
    }

    private List<Menu> findMenusWithDrinks() {
        return findAllMenus().stream()
                .filter(menu -> !menu.getDrinks().isEmpty())
                .collect(Collectors.toList());
    }

    private List<Menu> findMenusWithDeserts() {
        return findAllMenus().stream()
                .filter(menu -> !menu.getDeserts().isEmpty())
                .collect(Collectors.toList());
    }

    public void settingMenuId(Menu menu) {
        List<Dish> dishes = menu.getDishes();
        List<Drink> drinks = menu.getDrinks();
        List<Desert> deserts = menu.getDeserts();

        dishes.forEach(dish -> dish.setMenuId(menu.getId() != null ? menu.getId() : 1L));
        drinks.forEach(drink -> drink.setMenuId(menu.getId() != null ? menu.getId() : 1L));
        deserts.forEach(desert -> desert.setMenuId(menu.getId() != null ? menu.getId() : 1L));
    }


    public Menu menuIdAdder(Menu menuInfo, Menu menu) {
        if (menuInfo.getDishesIds() != null) {
            mergeCollections(menu.getDishesIds(), menuInfo.getDishesIds());
        }

        if (menuInfo.getDrinksIds() != null) {
            mergeCollections(menu.getDrinksIds(), menuInfo.getDrinksIds());
        }

        if (menuInfo.getDesertsIds() != null) {
            mergeCollections(menu.getDesertsIds(), menuInfo.getDesertsIds());
        }

        return menu;
    }
    public Menu updateMenu(Menu menu) {
        return saveOrUpdateMenu(menu);
    }

    public Menu createMenu(Menu menuRequest) {
        Menu menu = Menu.getInstance();
        menu.setDishesIds(menuRequest.getDishesIds());
        menu.setDrinksIds(menuRequest.getDrinksIds());
        menu.setDesertsIds(menuRequest.getDesertsIds());

        return saveOrUpdateMenu(menu);
    }

    private Menu saveOrUpdateMenu(Menu menu) {
        List<Long> dishesIds = menu.getDishesIds();
        List<Long> drinksIds = menu.getDrinksIds();
        List<Long> desertsIds = menu.getDesertsIds();

        menu.setDishes(dishService.findDishesByIds(dishesIds));
        menu.setDrinks(drinkService.findDrinksByIds(drinksIds));
        menu.setDeserts(desertService.findDesertsByIds(desertsIds));

        settingMenuId(menu);

        return menuRepository.save(menu);
    }

    public void deleteMenuById(Long id) {
        if (!menuRepository.existsById(id)) {
            throw new ResourceNotFoundException("Not found menu with such id: " + id);
        }
        menuRepository.deleteById(id);
    }

    public void convertStringIngrToArray(Menu menu) {
        menu.getDishes().forEach(dish -> {
            if (dish.getIngredients() != null && !dish.getIngredients().isEmpty()) {
                List<String> ingredientsList = Arrays.asList(dish.getIngredients().split(","));
                dish.setMenuId(menu.getId());
                dish.setTransientIngredients(ingredientsList);
            }
        });

        menu.getDeserts().forEach(desert -> {
            if (desert.getIngredients() != null && !desert.getIngredients().isEmpty()) {
                List<String> ingredientsList = Arrays.asList(desert.getIngredients().split(","));
                desert.setMenuId(menu.getId());
                desert.setTransientIngredients(ingredientsList);
            }
        });
    }

    private <T> void mergeCollections(List<T> existing, List<T> updates) {
        existing.clear();
        existing.addAll(updates);
    }
}

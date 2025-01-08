package com.test_task.restaurant.services;

import com.test_task.restaurant.Dto.OrderRequest;
import com.test_task.restaurant.Dto.StatusRequest;
import com.test_task.restaurant.exception.ResourceNotFoundException;
import com.test_task.restaurant.models.*;
import com.test_task.restaurant.repositories.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final ClientRepository clientRepository;
    private final DishService dishService;
    private final DrinkService drinkService;
    private final DesertService desertService;

    public OrdersService(OrdersRepository ordersRepository,
                         ClientRepository clientRepository,
                         DishService dishService,
                         DrinkService drinkService,
                         DesertService desertService) {
        this.ordersRepository = ordersRepository;
        this.clientRepository = clientRepository;
        this.dishService = dishService;
        this.drinkService = drinkService;
        this.desertService = desertService;
    }

    public Orders createOrder(OrderRequest request) {

        Client client = null;
        Optional<Client> findClient = clientRepository.findByContact("+" + request.getClient().getContact());
        if (findClient.isPresent()) {
            client = findClient.get();
        } else {
            client = new Client();
            client.setName(request.getClient().getName());
            client.setContact("+" + request.getClient().getContact());
            clientRepository.save(client);
        }

        List<Dish> dishes = dishService.findDishesByIds(request.getDishesIds());
        List<Drink> drinks = drinkService.findDrinksByIds(request.getDrinksIds());
        List<Desert> deserts = desertService.findDesertsByIds(request.getDesertsIds());

        double totalCost = calculateTotalCost(dishes, drinks, deserts);

        Orders order = new Orders();
        order.setClient(client);
        order.setStatus(
                Optional.of(request.getStatus().toLowerCase())
                        .map(s -> switch (s) {
                            case "accepted" -> Orders.Status.ACCEPTED;
                            case "delivered" -> Orders.Status.DELIVERED;
                            case "delivering" -> Orders.Status.DELIVERING;
                            case "cooking" -> Orders.Status.COOKING;
                            default -> throw new IllegalArgumentException("Unknown status: " + s);
                        })
                        .orElseThrow(() -> new IllegalArgumentException("Status cannot be null"))
        );
        order.setDishes(dishes);
        order.setDrinks(drinks);
        order.setDeserts(deserts);
        order.setTotalCost(totalCost);

        return ordersRepository.save(order);
    }

    public Orders updateOrderStatus(Orders order, StatusRequest request) {
        switch (request.getStatus().toLowerCase()) {
            case "accepted":
                order.setStatus(Orders.Status.ACCEPTED);
                break;
            case "cooking":
                order.setStatus(Orders.Status.COOKING);
                break;
            case "delivering":
                order.setStatus(Orders.Status.DELIVERING);
                break;
            case "delivered":
                order.setStatus(Orders.Status.DELIVERED);
                break;
            default:
                throw new ResourceNotFoundException("Not found such status");
        }
        return ordersRepository.save(order);
    }

    private double calculateTotalCost(List<Dish> dishes, List<Drink> drinks, List<Desert> deserts) {
        return (dishes != null ? dishes.stream().mapToDouble(Dish::getPrice).sum() : 0) +
                (drinks != null ? drinks.stream().mapToDouble(Drink::getPrice).sum() : 0) +
                (deserts != null ? deserts.stream().mapToDouble(Desert::getPrice).sum() : 0);
    }

    public Orders findOrderById(Long id) {
        return ordersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    public List<Orders> findAllOrders() {
        return ordersRepository.findAll();
    }

    public void deleteOrderById(Long id) {
        if (!ordersRepository.existsById(id)) {
            throw new ResourceNotFoundException("Order not found with id: " + id);
        }
        ordersRepository.deleteById(id);
    }
}

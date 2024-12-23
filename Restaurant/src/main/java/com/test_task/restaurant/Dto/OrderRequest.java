package com.test_task.restaurant.Dto;

import com.test_task.restaurant.models.Client;
import com.test_task.restaurant.models.Orders;

import java.util.List;
import java.util.stream.Collectors;

public class OrderRequest {

    private Client client;
    private Orders.Status status;
    private List<Long> dishesIds;
    private List<Long> drinksIds;
    private List<Long> desertsIds;

    public OrderRequest() {}

    public OrderRequest(Orders order) {
        this.client = order.getClient();
        this.status = order.getStatus();
        this.dishesIds = order.getDishes().stream().map(dish -> dish.getId()).collect(Collectors.toList());
        this.drinksIds = order.getDrinks().stream().map(drink -> drink.getId()).collect(Collectors.toList());
        this.desertsIds = order.getDeserts().stream().map(desert -> desert.getId()).collect(Collectors.toList());
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Orders.Status getStatus() {
        return status;
    }

    public void setStatus(Orders.Status status) {
        this.status = status;
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
}

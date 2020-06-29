package fruitstore.model;

import java.util.ArrayList;
import java.util.List;

public class FruitStore {
    private List<Fruit> fruits = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();
    private double moneyBalance;

    public List<Fruit> getFruits() {
        return fruits;
    }

    public void setFruits(List<Fruit> fruits) {
        this.fruits = fruits;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public double getMoneyBalance() {
        return moneyBalance;
    }

    public void setMoneyBalance(double moneyBalance) {
        this.moneyBalance = moneyBalance;
    }
}

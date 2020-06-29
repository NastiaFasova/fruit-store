package fruitstore.service;

import fruitstore.model.Fruit;
import fruitstore.model.Order;
import java.time.LocalDate;
import java.util.List;

public interface FruitStoreService {
    void addFruits(List<Fruit> fruits, String pathToJsonFile);

    List<Fruit> getSpoiledFruits(LocalDate date);

    List<Fruit> getSpoiledFruits(LocalDate date, Fruit.FruitType type);

    List<Fruit> getAvailableFruits(LocalDate date);

    List<Fruit> getAvailableFruits(LocalDate date, Fruit.FruitType type);

    List<Fruit> getAddedFruits(LocalDate date);

    List<Fruit> getAddedFruits(LocalDate date, Fruit.FruitType type);

    void addOrders(List<Order> orders, String pathToJsonFile);

    List<Order> getAllOrders(String pathToJsonFile);

    void sell(String pathToJsonFile);
}

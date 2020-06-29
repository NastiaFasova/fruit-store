package fruitstore.dao;

import fruitstore.model.Fruit;
import fruitstore.model.Order;
import java.time.LocalDate;
import java.util.List;

public interface FruitStoreDao {
    void addFruits(List<Fruit> fruits, String pathToJsonFile);

    void save(String pathToJsonFile);

    void load(String pathToJsonFile);

    List<Fruit> getAvailableFruits(LocalDate date);

    List<Fruit> getAvailableFruits(LocalDate date, Fruit.FruitType type);

    void addOrders(List<Order> orders, String pathToJsonFile);

    String updateOrders(String pathToJsonFile);

    List<Fruit> getAddedFruits(LocalDate date);

    List<Fruit> getAddedFruits(LocalDate date, Fruit.FruitType type);

    List<Order> getAllOrders(String pathToJsonFile);

    void sell(String pathToJsonFile);

    List<Fruit> getSpoiledFruits(LocalDate date, Fruit.FruitType type);

    List<Fruit> getSpoiledFruits(LocalDate date);
}

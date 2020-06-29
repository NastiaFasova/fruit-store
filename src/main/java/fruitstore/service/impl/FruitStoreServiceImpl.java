package fruitstore.service.impl;

import fruitstore.dao.FruitStoreDao;
import fruitstore.lib.Inject;
import fruitstore.lib.Service;
import fruitstore.model.Fruit;
import fruitstore.model.Order;
import fruitstore.service.FruitStoreService;
import java.time.LocalDate;
import java.util.List;

@Service
public class FruitStoreServiceImpl implements FruitStoreService {

    private static final String PATH = "fruit-store.json";

    @Inject
    private FruitStoreDao fruitStoreDao;

    @Override
    public void addFruits(List<Fruit> fruits, String pathToJsonFile) {
        fruitStoreDao.addFruits(fruits, pathToJsonFile);
        fruitStoreDao.save(PATH);
        fruitStoreDao.load(PATH);
    }

    @Override
    public List<Fruit> getSpoiledFruits(LocalDate date) {
        return fruitStoreDao.getSpoiledFruits(date);
    }

    @Override
    public List<Fruit> getSpoiledFruits(LocalDate date, Fruit.FruitType type) {
        return fruitStoreDao.getSpoiledFruits(date, type);
    }

    @Override
    public List<Fruit> getAvailableFruits(LocalDate date) {
        return fruitStoreDao.getAvailableFruits(date);
    }

    @Override
    public List<Fruit> getAvailableFruits(LocalDate date, Fruit.FruitType type) {
        return fruitStoreDao.getAvailableFruits(date, type);
    }

    @Override
    public List<Fruit> getAddedFruits(LocalDate date) {
        return fruitStoreDao.getAddedFruits(date);
    }

    @Override
    public List<Fruit> getAddedFruits(LocalDate date, Fruit.FruitType type) {
        return fruitStoreDao.getAddedFruits(date, type);
    }

    @Override
    public void addOrders(List<Order> orders, String pathToJsonFile) {
        fruitStoreDao.addOrders(orders, pathToJsonFile);
    }

    @Override
    public List<Order> getAllOrders(String pathToJsonFile) {
        return fruitStoreDao.getAllOrders(pathToJsonFile);
    }

    @Override
    public void sell(String pathToJsonFile) {
        fruitStoreDao.sell(pathToJsonFile);
        fruitStoreDao.save(PATH);
        fruitStoreDao.load(PATH);
    }
}

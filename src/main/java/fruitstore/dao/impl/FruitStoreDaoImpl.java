package fruitstore.dao.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import fruitstore.dao.FruitStoreDao;
import fruitstore.lib.Dao;
import fruitstore.lib.Inject;
import fruitstore.model.Fruit;
import fruitstore.model.FruitStore;
import fruitstore.model.Order;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@Dao
public class FruitStoreDaoImpl implements FruitStoreDao {

    @Inject
    private final FruitStore fruitStore = new FruitStore();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void addFruits(List<Fruit> fruits, String pathToJsonFile) {
        fruitStore.getFruits().addAll(fruits);
        JSONObject obj = new JSONObject();
        writeJsonArrIntoFile(fruits, obj);
        try (FileWriter file = new FileWriter(pathToJsonFile)) {
            file.write(obj.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(String pathToJsonFile) {
        List<Fruit> fruits = fruitStore.getFruits();
        JSONObject obj = new JSONObject();
        obj.put("moneyBalance", fruitStore.getMoneyBalance());
        writeJsonArrIntoFile(fruits, obj);
        try (FileWriter file = new FileWriter(pathToJsonFile)) {
            file.write(obj.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeJsonArrIntoFile(List<Fruit> fruits, JSONObject obj) {
        JSONArray fruitsArr = new JSONArray();
        for (Fruit fruit : fruits) {
            JSONObject fruitProperties = new JSONObject();
            fruitProperties.put("fruitType", fruit.getFruitType().toString());
            fruitProperties.put("deliveryDate", fruit.getDeliveryDate().toString());
            fruitProperties.put("expirationDateBound", fruit.getExpirationDateBound());
            fruitProperties.put("price", fruit.getPrice());
            JSONObject fruitObject = new JSONObject();
            fruitObject.put("fruit", fruitProperties);
            fruitsArr.add(fruitObject);
            obj.put("fruits", fruitsArr);
        }
    }

    @Override
    public void load(String pathToJsonFile) {
        fruitStore.getFruits().clear();
        JSONParser parser = new JSONParser();
        List<Fruit> fruits = new ArrayList<>();
        try (FileReader reader = new FileReader(pathToJsonFile)) {
            Object obj = parser.parse(reader);
            JSONObject object = (JSONObject) obj;
            double moneyBalance = (Double) object.get("moneyBalance");
            JSONArray fruitArr = (JSONArray) object.get("fruits");
            fruitArr.forEach(f -> fruits.add(getFruitFromJson((JSONObject)f)));
            fruitStore.setFruits(fruits);
            fruitStore.setMoneyBalance(moneyBalance);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Fruit> getSpoiledFruits(LocalDate date) {
        return fruitStore.getFruits().stream()
                .filter(f -> f.getDeliveryDate()
                        .plusDays(f.getExpirationDateBound()).isBefore(date))
                .collect(Collectors.toList());
    }

    @Override
    public List<Fruit> getSpoiledFruits(LocalDate date, Fruit.FruitType type) {
        return fruitStore.getFruits().stream()
                .filter(f -> f.getFruitType().equals(type))
                .filter(f -> f.getDeliveryDate()
                        .plusDays(f.getExpirationDateBound()).isBefore(date))
                .collect(Collectors.toList());
    }

    private Fruit getFruitFromJson(JSONObject object) {
        JSONObject fruitObject = (JSONObject) object.get("fruit");
        Fruit.FruitType fruitType
                = Fruit.FruitType.valueOf(fruitObject.get("fruitType").toString());
        long expirationDateBound = (Long) fruitObject.get("expirationDateBound");
        LocalDate deliveryDate = LocalDate.parse((String) fruitObject.get("deliveryDate"));
        double price = (Double) fruitObject.get("price");
        Fruit fruit = new Fruit();
        fruit.setFruitType(fruitType);
        fruit.setExpirationDateBound(expirationDateBound);
        fruit.setPrice(price);
        fruit.setDeliveryDate(deliveryDate);
        return fruit;
    }

    @Override
    public List<Fruit> getAddedFruits(LocalDate date) {
        return fruitStore.getFruits().stream()
                .filter(f -> f.getDeliveryDate().equals(date))
                .collect(Collectors.toList());
    }

    @Override
    public List<Fruit> getAddedFruits(LocalDate date, Fruit.FruitType type) {
        return fruitStore.getFruits().stream()
                .filter(f -> f.getFruitType().equals(type))
                .filter(f -> f.getDeliveryDate().equals(date))
                .collect(Collectors.toList());
    }

    @Override
    public void addOrders(List<Order> orders, String pathToJsonFile) {
        JSONObject obj = new JSONObject();
        JSONArray ordersArr = new JSONArray();
        for (Order order : orders) {
            JSONObject orderProperties = new JSONObject();
            orderProperties.put("fruitType", order.getFruitType().toString());
            orderProperties.put("name", order.getName());
            orderProperties.put("count", order.getCount());
            JSONObject orderObject = new JSONObject();
            orderObject.put("order", orderProperties);
            ordersArr.add(orderObject);
            obj.put("orders", ordersArr);
        }
        try (FileWriter file = new FileWriter(pathToJsonFile)) {
            file.write(obj.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Fruit> getAvailableFruits(LocalDate date) {
        return fruitStore.getFruits().stream()
                .filter(f -> f.getDeliveryDate()
                        .plusDays(f.getExpirationDateBound()).isAfter(date))
                .collect(Collectors.toList());
    }

    @Override
    public List<Fruit> getAvailableFruits(LocalDate date, Fruit.FruitType type) {
        return fruitStore.getFruits().stream()
                .filter(f -> f.getFruitType().equals(type))
                .filter(f -> f.getDeliveryDate()
                        .plusDays(f.getExpirationDateBound()).isAfter(date))
                .collect(Collectors.toList());
    }

    @Override
    public String updateOrders(String pathToJsonFile) {
        try (FileWriter file = new FileWriter(pathToJsonFile)) {
            mapper.writeValue(file, fruitStore.getOrders());
            return mapper.writeValueAsString(fruitStore.getOrders());
        } catch (IOException e) {
            throw new RuntimeException("Can't update orders");
        }
    }

    @Override
    public List<Order> getAllOrders(String pathToJsonFile) {
        JSONParser parser = new JSONParser();
        List<Order> orders = new ArrayList<>();
        try (FileReader reader = new FileReader(pathToJsonFile)) {
            Object obj = parser.parse(reader);
            JSONObject object = (JSONObject) obj;
            JSONArray orderArr = (JSONArray) object.get("orders");
            orderArr.forEach(f -> orders.add(getOrderFromJson((JSONObject)f)));
            fruitStore.setOrders(orders);
            return orders;
        } catch (IOException | ParseException e) {
            throw new RuntimeException();
        }
    }

    private Order getOrderFromJson(JSONObject object) {
        JSONObject orderObject = (JSONObject) object.get("order");
        String name = (String) orderObject.get("name");
        Fruit.FruitType fruitType = Fruit.FruitType.valueOf((String) orderObject.get("fruitType"));
        long count = (Long) orderObject.get("count");
        Order order = new Order();
        order.setFruitType(fruitType);
        order.setName(name);
        order.setCount(count);
        return order;
    }

    @Override
    public void sell(String pathToJsonFile) {
        double moneyBalance = fruitStore.getMoneyBalance();
        List<Order> orders = getAllOrders(pathToJsonFile);
        List<Fruit> fruits = new ArrayList<>();
        for (Order order : orders) {
            long fruitQuantity = fruitStore.getFruits().stream()
                    .filter(f -> f.getFruitType().equals(order.getFruitType()))
                    .count();
            Fruit fruit = fruitStore.getFruits().stream()
                    .filter(f -> f.getFruitType().equals(order.getFruitType()))
                    .findAny().orElseThrow();
            if (order.getCount() <= fruitQuantity) {
                moneyBalance += fruit.getPrice() * fruitQuantity;
                fruitStore.getFruits().remove(fruit);
            }
        }
        fruitStore.getOrders().removeAll(orders);
        fruitStore.setMoneyBalance(moneyBalance);
        updateOrders(pathToJsonFile);
    }
}

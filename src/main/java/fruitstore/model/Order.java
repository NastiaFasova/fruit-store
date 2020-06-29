package fruitstore.model;

import java.util.Objects;

public class Order {
    private String name;
    private Fruit.FruitType fruitType;
    private long count;

    public Order(String name, Fruit.FruitType fruitType, long count) {
        this.name = name;
        this.fruitType = fruitType;
        this.count = count;
    }

    public Order() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Fruit.FruitType getFruitType() {
        return fruitType;
    }

    public void setFruitType(Fruit.FruitType fruitType) {
        this.fruitType = fruitType;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return count == order.count
                && Objects.equals(name, order.name)
                && fruitType == order.fruitType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, fruitType, count);
    }
}

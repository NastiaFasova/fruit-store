package fruitstore.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.time.LocalDate;
import java.util.Objects;

public class Fruit {
    private FruitType fruitType;
    private Long expirationDateBound;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate deliveryDate;
    private double price;

    public Fruit(FruitType fruitType, Long expirationDateBound, LocalDate deliveryDate,
                 double price) {
        this.fruitType = fruitType;
        this.expirationDateBound = expirationDateBound;
        this.deliveryDate = deliveryDate;
        this.price = price;
    }

    public Fruit() {
    }

    public enum FruitType {
        APPLE, STRAWBERRY, BANANA, GRAPE, PEAR, APRICOT
    }

    public FruitType getFruitType() {
        return fruitType;
    }

    public void setFruitType(FruitType fruitType) {
        this.fruitType = fruitType;
    }

    public Long getExpirationDateBound() {
        return expirationDateBound;
    }

    public void setExpirationDateBound(Long expirationDateBound) {
        this.expirationDateBound = expirationDateBound;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Fruit fruit = (Fruit) o;
        return expirationDateBound.equals(fruit.expirationDateBound)
                && Double.compare(fruit.price, price) == 0
                && fruitType == fruit.fruitType
                && Objects.equals(deliveryDate, fruit.deliveryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fruitType, expirationDateBound, deliveryDate, price);
    }

    @Override
    public String toString() {
        return "Fruit{" + "fruitType=" + fruitType
                + ", expirationDateBound=" + expirationDateBound
                + ", deliveryDate=" + deliveryDate
                + ", price=" + price + '}';
    }
}

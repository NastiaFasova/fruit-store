package fruitstore;

import fruitstore.lib.Injector;
import fruitstore.model.Fruit;
import fruitstore.model.FruitStore;
import fruitstore.model.Order;
import fruitstore.service.FruitStoreService;
import java.time.LocalDate;
import java.util.List;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("fruitstore");
    private static final FruitStoreService fruitStoreService
            = (FruitStoreService) INJECTOR.getInstance(FruitStoreService.class);

    public static void main(String[] args) {
        Fruit apple = new Fruit(Fruit.FruitType.APPLE,
                25L, LocalDate.of(2020, 9,11), 15);

        Fruit banana = new Fruit(Fruit.FruitType.BANANA,
                20L, LocalDate.of(2020, 10,9), 30);
        Fruit apricot = new Fruit(Fruit.FruitType.APRICOT,
                15L, LocalDate.of(2020, 11,8), 20.5);

        FruitStore fruitStore = new FruitStore();
        fruitStore.setFruits(List.of(apple, banana, apricot));

        fruitStoreService.addFruits(List.of(apple, banana, apricot), "fruit-delivery.json");

        System.out.println(fruitStoreService.getAddedFruits(LocalDate.of(2020, 11,8)));
        System.out.println(fruitStoreService.getAddedFruits(LocalDate.of(2020, 10, 9),
                Fruit.FruitType.BANANA));

        System.out.println("Fruits which will be available by 11-11-2020 : "
                + fruitStoreService.getAvailableFruits(LocalDate.of(2020, 11, 11)));
        System.out.println(Fruit.FruitType.BANANA.toString()
                + "s which are available by 09-12-2020"
                + fruitStoreService.getAvailableFruits(LocalDate.of(2020, 10, 12),
                Fruit.FruitType.BANANA));

        System.out.println("Imagine today is 31-12-2020. Then these fruits will be spoiled: "
                + fruitStoreService.getSpoiledFruits(LocalDate.of(2020, 12, 12)));
        System.out.println("Imagine today is 31-12-2020. Then these "
                + Fruit.FruitType.BANANA + "s will be spoiled: "
                + fruitStoreService.getSpoiledFruits(LocalDate.of(2020, 12, 12),
                Fruit.FruitType.BANANA));

        Order firstOrder = new Order("Bob", Fruit.FruitType.APPLE, 1);
        Order secondOrder = new Order("Chris", Fruit.FruitType.BANANA, 1);

        fruitStoreService.addOrders(List.of(firstOrder, secondOrder), "orders.json");

        fruitStoreService.sell("orders.json");

    }
}

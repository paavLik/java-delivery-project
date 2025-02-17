import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.homework.delivery.Cargo;
import ru.homework.delivery.Delivery;
import ru.homework.delivery.LoadLevel;
import ru.homework.delivery.Size;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeliveryTest {

    @DisplayName("Тест расчёта стоимости по расстоянию")
    @ParameterizedTest(name = "Должен вернуть {1} руб. за расстояние > {0} км")
    @Tag("Smoke")
    @CsvSource({
            "1, 50",
            "5, 100",
            "15, 200",
            "31, 300"
    })
    void getDistanceCostParameterizedTest(int distance, int expectedCost) {
        Cargo cargo = new Cargo(Size.SMALL, false);
        Delivery delivery = new Delivery(distance, cargo, LoadLevel.HIGH);

        int actualCost = delivery.getDistanceCost();

        assertEquals(expectedCost, actualCost);
    }


    @DisplayName("Тест расчёта стоимости по хрупкости груза")
    @ParameterizedTest(name = "Должен вернуть {1} руб. так как груз {0} ")
    @Tag("Smoke")
    @CsvSource({
            "true, 30, 300",
            "false, 31, 0",

    })
    void validateFragileDeliveryParameterizedTest(boolean fragile, int distance, int expectedCost) {
        Cargo cargo = new Cargo(Size.SMALL, fragile);
        Delivery delivery = new Delivery(distance, cargo, LoadLevel.MEDIUM);

        int actualCost = delivery.validateFragileDelivery();

        assertEquals(expectedCost, actualCost);
    }

    @DisplayName("Тест расчёта стоимости по габаритам груза")
    @ParameterizedTest(name = "Должен вернуть {1} руб. за габариты груза {0} ")
    @Tag("Smoke")
    @CsvSource({
            "BIG, 200",
            "SMALL, 100",
    })
    void getSizeCostTest(Size size, int expectedCost) {
        Cargo cargo = new Cargo(size, false);
        Delivery delivery = new Delivery(30, cargo, LoadLevel.HIGH);

        int actualCost = delivery.getSizeCost();

        assertEquals(expectedCost, actualCost);
    }

    @ParameterizedTest(name = "Уровень загруженности {0} → коэффициент {1}")
    @CsvSource({
            "VERY_HIGH, 1.6",
            "HIGH, 1.4",
            "MEDIUM, 1.2",
            "NORMAL, 1.0"
    })
    @DisplayName("Тестирование коэффициента загруженности для разных уровней")
    void getLoadFactorTest(LoadLevel loadLevel, double expectedFactor) {
        Delivery delivery = new Delivery(10, new Cargo(Size.SMALL, false), loadLevel);

        double actualFactor = delivery.getLoadFactor();

        assertEquals(expectedFactor, actualFactor);
    }

    @ParameterizedTest(name = "Расчёт стоимости: Distance {0}, Size {1}, Fragile {2}, LoadLevel {3} → Expected {4}")
    @CsvSource({
            "35, BIG, false, HIGH, 700",
            "25, SMALL, false, NORMAL, 400",
            "10, SMALL, true, MEDIUM, 600",
            "5, BIG, true, VERY_HIGH, 960"
    })
    @DisplayName("Тестирование calculateCost() с разными коэффициентами загруженности")
    void calculateCostTest(int distance, String sizeStr, String fragileStr, String levelStr, int expectedTotal) {
        Size size = Size.valueOf(sizeStr);
        boolean fragile = Boolean.parseBoolean(fragileStr);
        LoadLevel loadLevel = LoadLevel.valueOf(levelStr);

        Cargo cargo = new Cargo(size, fragile);
        Delivery delivery = new Delivery(distance, cargo, loadLevel);

        int actualCost = delivery.calculateCost();

        assertEquals(expectedTotal, actualCost);
    }
}


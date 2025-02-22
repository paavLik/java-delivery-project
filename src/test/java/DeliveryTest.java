import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.homework.delivery.Cargo;
import ru.homework.delivery.Delivery;
import ru.homework.delivery.LoadLevel;
import ru.homework.delivery.Size;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeliveryTest {

    @ParameterizedTest(name = "Расстояние {0} → Стоимость {1}")
    @CsvSource({
            "1.99, 50.0",
            "2.01, 100.0",
            "9.99, 100.0",
            "10.01, 200.0",
            "29.99, 200.0",
            "30.01, 300.0"
    })
    @Tag("Boundary")
    @DisplayName("Граничные значения расчета стоимости по расстоянию")
    void getDistanceCostParameterizedTest(double distance, double expectedCost) {
        Cargo cargo = new Cargo(Size.SMALL, false);
        Delivery delivery = new Delivery(distance, cargo, LoadLevel.HIGH);
        double actualCost = delivery.getDistanceCost();

        assertEquals(expectedCost, actualCost);
    }

    @ParameterizedTest(name = "Груз {0} → Стоимость {1}")
    @CsvSource({
            "BIG, 200.0",
            "SMALL, 100.0"
    })
    @DisplayName("Тест расчёта стоимости по габаритам груза")
    void getSizeCostTest(Size size, double expectedCost) {
        Cargo cargo = new Cargo(size, false);
        Delivery delivery = new Delivery(30.0, cargo, LoadLevel.HIGH);

        double actualCost = delivery.getSizeCost();

        assertEquals(expectedCost, actualCost);
    }

    @ParameterizedTest(name = "Загруженность {0} → Коэффициент {1}")
    @CsvSource({
            "VERY_HIGH, 1.6",
            "HIGH, 1.4",
            "MEDIUM, 1.2",
            "NORMAL, 1.0"
    })
    @DisplayName("Тестирование коэффициента загруженности")
    void getLoadFactorTest(LoadLevel loadLevel, double expectedFactor) {
        Delivery delivery = new Delivery(10.0, new Cargo(Size.SMALL, false), loadLevel);

        double actualFactor = delivery.getLoadFactor();

        assertEquals(expectedFactor, actualFactor);
    }
}

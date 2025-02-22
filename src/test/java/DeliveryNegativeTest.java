import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.homework.delivery.Cargo;
import ru.homework.delivery.Delivery;
import ru.homework.delivery.LoadLevel;
import ru.homework.delivery.Size;

import static org.junit.jupiter.api.Assertions.*;

public class DeliveryNegativeTest {

    @ParameterizedTest(name = "Расстояние {0} → Ошибка: расстояние должно быть больше 0")
    @CsvSource({"0.0", "-1.0", "-10.0"})
    @Tag("Negative")
    @DisplayName("Должен выбросить исключение при отрицательном или нулевом расстоянии")
    void invalidDistanceTest(double distance) {
        Cargo cargo = new Cargo(Size.SMALL, true);
        assertThrows(IllegalArgumentException.class, () -> new Delivery(distance, cargo, LoadLevel.NORMAL));
    }

    @Test
    @Tag("Negative")
    @DisplayName("Должен выбросить исключение, если хрупкий груз нельзя доставить")
    void fragileDeliveryImpossibleTest() {
        Cargo cargo = new Cargo(Size.SMALL, true);
        Delivery delivery = new Delivery(30.01, cargo, LoadLevel.NORMAL);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                delivery::calculateCost
        );
        assertTrue(exception.getMessage().contains("хрупкий груз нельзя доставить"));
    }

    @Test
    @Tag("Negative")
    @DisplayName("Должен выбросить исключение при отсутствии уровня загруженности")
    void missingLoadLevelTest() {
        Cargo cargo = new Cargo(Size.SMALL, true);
        assertThrows(IllegalArgumentException.class, () -> new Delivery(10.0, cargo, null));
    }
}

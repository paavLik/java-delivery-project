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

    @ParameterizedTest(name = "Расстояние {0} → Должно содержать слово 'Расстояние'")
    @CsvSource({"-1", "-10", "-100"})
    @Tag("Negative")
    @DisplayName("Должен выбросить исключение, если расстояние меньше или равно -1")
    void invalidDistanceTest(int distance) {
        Cargo cargo = new Cargo(Size.SMALL, true);
        Delivery delivery = new Delivery(distance, cargo, LoadLevel.NORMAL);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                delivery::calculateCost
        );

        assertTrue(exception.getMessage().contains("Расстояние"),
                "Ожидалось сообщение об ошибке, связанное с расстоянием.");
    }

    @ParameterizedTest(name = "Расстояние {0} → Ожидаемая ошибка: {1}")
    @CsvSource({
            "0, 'Ошибка: расстояние должно быть больше 0'",
            "-1, 'Ошибка: расстояние должно быть больше 0'",
            "-10, 'Ошибка: расстояние должно быть больше 0'"
    })
    @Tag("Smoke")
    @DisplayName("Должен выбросить исключение, если расстояние меньше или равно 0")
    void invalidDistanceTest(int distance, String expectedMessage) {
        Cargo cargo = new Cargo(Size.SMALL, true);
        Delivery delivery = new Delivery(distance, cargo, LoadLevel.NORMAL);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> delivery.getDistanceCost()
        );

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    @Tag("Negative")
    @DisplayName("Должен выбросить точное исключение, если хрупкий груз нельзя доставить")
    void fragileDeliveryImpossibleTest() {
        Cargo cargo = new Cargo(Size.SMALL, true);
        Delivery delivery = new Delivery(35, cargo, LoadLevel.NORMAL);

        IllegalArgumentException exception = assertThrowsExactly(
                IllegalArgumentException.class,
                delivery::calculateCost
        );

        assertTrue(exception.getMessage().contains("хрупкий"),
                "Ожидалось сообщение о хрупкости груза");
    }

    @Test
    @Tag("Negative")
    @DisplayName("Должен выбросить исключение, если уровень загруженности не указан")
    void missingLoadLevelTest() {
        Cargo cargo = new Cargo(Size.SMALL, true);
        Delivery delivery = new Delivery(10, cargo, null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                delivery::calculateCost
        );

        assertTrue(exception.getMessage().contains("уровень загруженности"),
                "Ожидалось сообщение об ошибке, связанное с уровнем загруженности.");
    }

    @Test
    @Tag("Smoke")
    @DisplayName("Должен вернуть сообщение о том, что груз нельзя доставить")
    void validateFragileDeliveryTest() {
        Cargo cargo = new Cargo(Size.SMALL, true);
        Delivery delivery = new Delivery(31, cargo, LoadLevel.NORMAL);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                delivery::validateFragileDelivery
        );
        String actualMessage = "Извините, хрупкий груз нельзя доставить на такое расстояние!";

        assertEquals(actualMessage, exception.getMessage());
    }
}

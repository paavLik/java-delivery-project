import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.homework.delivery.DeliveryService;
import ru.homework.delivery.LoadLevel;
import ru.homework.delivery.Size;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeliveryServiceTest {

    @ParameterizedTest(name = "Distance={0}, Size={1}, Fragile={2}, LoadLevel={3} → Expected={4}")
    @CsvSource({
            "5, SMALL, false, NORMAL, 400",
            "15, BIG, true, HIGH, 979",
            "35, SMALL, false, VERY_HIGH, 640",
            "10, BIG, true, MEDIUM, 720"
    })
    @Tag("Smoke")
    @DisplayName("Тест расчёта стоимости в DeliveryService")
    void calculateDeliveryCostTest(int distance, String sizeStr, boolean fragile, String loadLevelStr, int expectedCost) {
        Size size = Size.valueOf(sizeStr.toUpperCase());
        LoadLevel loadLevel = LoadLevel.valueOf(loadLevelStr.toUpperCase());

        int actualCost = DeliveryService.calculateDeliveryCost(distance, size, fragile, loadLevel);

        assertEquals(expectedCost, actualCost);
    }


    @Test
    @Tag("Boundary")
    @DisplayName("Должен вернуть не менее 400 руб. за доставку")
    void calculateDeliveryCost_ShouldBeAtLeastMinimumCost() {
        int cost = DeliveryService.calculateDeliveryCost(1, Size.SMALL, false, LoadLevel.NORMAL);
        assertTrue(cost >= 400, "Ожидалось, что стоимость будет не менее 400 руб.");
    }
}

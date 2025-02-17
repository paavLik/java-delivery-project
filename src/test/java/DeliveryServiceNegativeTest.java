import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.homework.delivery.DeliveryService;
import ru.homework.delivery.LoadLevel;
import ru.homework.delivery.Size;

import static org.junit.jupiter.api.Assertions.assertThrows;

class DeliveryServiceNegativeTest {

    @Test
    @Tag("Negative")
    @DisplayName("Должен выбросить исключение при недопустимом расстоянии")
    void calculateDeliveryCost_ShouldThrowException_WhenDistanceInvalid() {
        assertThrows(IllegalArgumentException.class, () ->
                DeliveryService.calculateDeliveryCost(0, Size.SMALL, false, LoadLevel.NORMAL)
        );


        assertThrows(IllegalArgumentException.class, () ->
                DeliveryService.calculateDeliveryCost(-10, Size.BIG, true, LoadLevel.HIGH)
        );
    }
}


package ru.homework.delivery;

/**
 * Класс, описывающий доставку груза.
 */
public class Delivery {

    private final Cargo cargo;
    private final double distance;
    private final LoadLevel loadLevel;

    /**
     * Конструктор класса Delivery.
     *
     * @param distance  расстояние в км
     * @param cargo     груз
     * @param loadLevel уровень загруженности дорог
     */
    public Delivery(double distance, Cargo cargo, LoadLevel loadLevel) {
        if (distance <= 0) {
            throw new IllegalArgumentException("Ошибка: расстояние должно быть больше 0");
        }
        if (loadLevel == null) {
            throw new IllegalArgumentException("Ошибка: уровень загруженности не указан");
        }
        this.cargo = cargo;
        this.distance = distance;
        this.loadLevel = loadLevel;
    }

    /**
     * Рассчитывает стоимость доставки в зависимости от расстояния.
     *
     * @return стоимость доставки по расстоянию
     */
    public double getDistanceCost() {
        if (distance > 30) return 300.0;
        if (distance > 10) return 200.0;
        if (distance > 2) return 100.0;
        return 50.0;
    }

    /**
     * Рассчитывает стоимость доставки в зависимости от размера груза.
     *
     * @return стоимость доставки по размеру груза
     */
    public double getSizeCost() {
        return (cargo.getSize() == Size.BIG) ? 200.0 : 100.0;
    }

    /**
     * Рассчитывает доплату за хрупкость груза.
     *
     * @return доплата за хрупкость или исключение, если доставка невозможна.
     */
    public double getFragileCost() {
        if (cargo.isFragile() && distance > 30) {
            throw new IllegalArgumentException("Ошибка: хрупкий груз нельзя доставить на расстояние более 30 км");
        }
        return (cargo.isFragile() && distance <= 30) ? 300.0 : 0.0;
    }

    /**
     * Получает коэффициент загруженности дорог.
     *
     * @return коэффициент загруженности
     */
    public double getLoadFactor() {
        return loadLevel.getFactor();
    }

    /**
     * Финальный расчет стоимости доставки.
     *
     * @return итоговая стоимость доставки
     */
    public double calculateCost() {
        // Базовая стоимость
        double baseCost = getDistanceCost() + getSizeCost() + getFragileCost();

        // Применение коэффициента загруженности
        double costWithLoadFactor = baseCost * getLoadFactor();

        // Минимальная стоимость 400.0 применяется после коэффициента
        double finalCost = Math.max(costWithLoadFactor, 400.0);

        // Округление до двух знаков
        return Math.round(finalCost * 100.0) / 100.0;
    }
}

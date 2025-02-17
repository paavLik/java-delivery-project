package ru.homework.delivery;

public class Delivery {

    private final Cargo cargo;
    private final int distance;
    private final LoadLevel loadLevel;


    public Delivery(int distance, Cargo cargo, LoadLevel loadLevel) {
        this.cargo = cargo;
        this.distance = distance;
        this.loadLevel = loadLevel;
    }

    public int getDistanceCost() {
        if (distance <= 0) {
            throw new IllegalArgumentException("Ошибка: расстояние должно быть больше 0");
        }
        if (distance > 30) return 300;
        if (distance > 10) return 200;
        if (distance > 2) return 100;
        return 50;
    }

    public int getSizeCost() {
        if (cargo.getSize() == Size.BIG) return 200;
        else return 100;
    }

    public int validateFragileDelivery() {
        if (cargo.isFragile() && distance > 30) {
            throw new IllegalArgumentException("Извините, хрупкий груз нельзя доставить на такое расстояние!");
        }
        return (cargo.isFragile() && distance <= 30) ? 300 : 0;
    }

    public double getLoadFactor() {
        return loadLevel.getFactor();
    }

    public int calculateCost() {
        if (distance <= 0) {
            throw new IllegalArgumentException("Расстояние должно быть больше нуля!");
        }

        int fragileCost = validateFragileDelivery();
        if (fragileCost == -1) {
            throw new IllegalArgumentException("Ошибка: доставка невозможна");
        }

        if (loadLevel == null) {
            throw new IllegalArgumentException("Ошибка: уровень загруженности не указан");
        }

        double baseCost = getDistanceCost() + getSizeCost() + validateFragileDelivery();
        double loadFactor = getLoadFactor();

        int adjustedBaseCost = (int) Math.max(baseCost, 400);
        int finalCost = (int) (adjustedBaseCost * loadFactor);

        return finalCost;
    }
}

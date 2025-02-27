package ru.homework.delivery;

/**
 * Перечисление уровней загруженности дорог.
 */
public enum LoadLevel {
    VERY_HIGH(1.6), HIGH(1.4), MEDIUM(1.2), NORMAL(1.0);

    private final double factor;

    LoadLevel(double factor) {
        this.factor = factor;
    }

    public double getFactor() {
        return factor;
    }
}

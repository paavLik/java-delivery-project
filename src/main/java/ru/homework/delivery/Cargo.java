package ru.homework.delivery;

public class Cargo {

    private final Size size;
    private final boolean fragile;

    public Cargo(Size size, boolean fragile) {
        this.size = size;
        this.fragile = fragile;
    }

    public Size getSize() {
        return size;
    }

    public boolean isFragile() {
        return this.fragile;
    }
}

package ru.homework.delivery;

public class DeliveryService {
    public static int calculateDeliveryCost(int distance, Size size, boolean fragile, LoadLevel loadLevel) {
        Cargo cargo = new Cargo(size, fragile);
        Delivery delivery = new Delivery(distance, cargo, loadLevel);
        return delivery.calculateCost();
    }

}

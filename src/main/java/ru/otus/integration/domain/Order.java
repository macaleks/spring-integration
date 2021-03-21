package ru.otus.integration.domain;

public class Order {

    private int items;
    private int price;

    public Order(int items, int price) {
        this.items = items;
        this.price = price;
    }

    public Order() {
    }

    @Override
    public String toString() {
        return "Order{" +
                "items=" + items +
                ", price=" + price +
                '}';
    }
}

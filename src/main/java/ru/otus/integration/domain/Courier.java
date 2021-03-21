package ru.otus.integration.domain;

public class Courier {

    private final String name;
    private Order order;

    public Courier(String name) {
        this.name = name;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Courier{" +
                "name='" + name + '\'' +
                ", order=" + order +
                '}';
    }
}

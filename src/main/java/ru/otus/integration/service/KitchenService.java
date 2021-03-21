package ru.otus.integration.service;

import org.springframework.stereotype.Service;
import ru.otus.integration.App;
import ru.otus.integration.domain.Order;

@Service
public class KitchenService {

    public void prepareOrder(Order order) throws InterruptedException {
        System.out.println("Preparing order " + order);
        Thread.sleep(3000);
        System.out.println("Order is ready " + order);
        App.orders.add(order);
    }
}

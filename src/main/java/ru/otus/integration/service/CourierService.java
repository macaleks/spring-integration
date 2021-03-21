package ru.otus.integration.service;

import org.springframework.stereotype.Service;
import ru.otus.integration.App;
import ru.otus.integration.domain.Courier;
import ru.otus.integration.domain.Order;

import java.util.Random;

@Service
public class CourierService {

    public Courier callCourier() throws InterruptedException {
        System.out.println("Courier is running to the cafe");
        Thread.sleep(5000);
        Courier courier = new Courier("Victor" + new Random().nextInt(10));
        System.out.println("Courier " + courier.getName() + "is in the cafe waiting the order");
        Order order = App.orders.poll();
        System.out.println("Courier has taken the order and is on the way");
        courier.setOrder(order);
        return courier;
    }

    public Courier takeOrder() {
        return null;
    }
}

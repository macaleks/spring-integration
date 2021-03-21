package ru.otus.integration.service;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.integration.domain.Courier;
import ru.otus.integration.domain.Order;

@MessagingGateway
public interface OrderService {

    @Gateway(requestChannel = "ordersChannel", replyChannel = "deliveryChannel")
    Courier makeOrder(Order order);
}

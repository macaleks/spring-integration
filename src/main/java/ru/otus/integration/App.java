package ru.otus.integration;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.MessageChannel;
import ru.otus.integration.domain.Courier;
import ru.otus.integration.domain.Order;
import ru.otus.integration.service.OrderService;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@IntegrationComponentScan
@ComponentScan
@Configuration
@EnableIntegration
public class App {
    private static final Order[] ORDERS = {new Order(4, 100), new Order(6, 240), new Order(1, 50)};
    public static final BlockingQueue<Order> orders = new ArrayBlockingQueue<>(10);

    @Bean
    public PublishSubscribeChannel ordersChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(100).maxMessagesPerPoll(2).get();
    }

    @Bean
    public MessageChannel kitchenChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public MessageChannel courierChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public MessageChannel deliveryChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public IntegrationFlow orderFlow1() {
        return IntegrationFlows.from("ordersChannel")
                .channel("kitchenChannel")
                .get();
    }

    @Bean
    public IntegrationFlow orderFlow2() {
        return IntegrationFlows.from("ordersChannel")
                .channel("courierChannel")
                .get();
    }

    @Bean
    public IntegrationFlow orderFlow() {
        return IntegrationFlows.from("kitchenChannel")
                .handle("kitchenService", "prepareOrder")
                .channel("nullChannel")
                .get();
    }

    @Bean
    public IntegrationFlow courierFlow() {
        return IntegrationFlows.from("courierChannel")
                .handle("courierService", "callCourier")
                .channel("deliveryChannel")
                .get();
    }

    public static void main(String[] args) throws InterruptedException {
        AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(App.class);

        OrderService orderService = ctx.getBean(OrderService.class);

        for (int i = 0; i < ORDERS.length; i++) {
            Thread.sleep(1000);
            Courier courier = orderService.makeOrder(ORDERS[i]);
            System.out.println("Delivering an order: " + courier);
        }

    }


}

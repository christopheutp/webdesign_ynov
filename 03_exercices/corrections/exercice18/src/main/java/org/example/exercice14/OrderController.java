package org.example.exercice14;


import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final List<Order> orders = new ArrayList<>();

    public OrderController() {
        orders.add(new Order(1L, "Laptop"));
        orders.add(new Order(2L, "Phone"));
    }

    @GetMapping
    public Flux<Order> getAllOrders() {
        return Flux.fromIterable(orders);
    }

    @PostMapping
    public Mono<Order> addOrder(@RequestBody Order order) {
        orders.add(order);
        return Mono.just(order);
    }
}

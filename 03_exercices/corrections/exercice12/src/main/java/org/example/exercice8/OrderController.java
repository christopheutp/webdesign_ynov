package org.example.exercice8;


import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public Flux<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Mono<Order> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PostMapping
    public Mono<Order> createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @PutMapping("/{id}")
    public Mono<Order> updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        return orderService.updateOrderStatus(id,status);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteOrder(@PathVariable Long id) {
        return orderService.deleteOrderById(id);
    }

    @GetMapping("/search")
    public Flux<Order> search(@RequestParam String status) {
        return orderService.getOrdersByStatus(status);
    }

    @GetMapping("/paged")
    public Flux<Order> getOrdersPaged(@RequestParam int page,@RequestParam int size) {
        return orderService.getOrdersPages(page,size);
    }

    @GetMapping("/customer/{customername}")
    public Flux<Order> getCustomerOrders(@PathVariable String customername) {
        return orderService.getOrderByCustomerName(customername);
    }
}

package org.example.exercice8;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Flux<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public Mono<Order> getOrderById(long id){
        return orderRepository.findById(id);
    }

    public Mono<Order> createOrder(Order order){
        order.setStatus("PENDING");
        return orderRepository.save(order);
    }

    public Mono<Order> updateOrderStatus(long id, String status){
        return orderRepository.findById(id)
                .flatMap(order -> {
                    order.setStatus(status);
                    return orderRepository.save(order);
                });
    }

    public Mono<Void> deleteOrderById(long id){
        return orderRepository.deleteById(id);
    }

    public Flux<Order> getOrdersByStatus(String status){
        return orderRepository.findByStatus(status);
    }

    public Flux<Order> getOrdersPages(int page,int size){
        PageRequest pageable = PageRequest.of(page, size, Sort.by("createdAt"));
        return orderRepository.findAllBy(pageable);
    }

    public Flux<Order> getOrderByCustomerName(String customerName){
        return orderRepository.findByCustomerName(customerName);
    }


}

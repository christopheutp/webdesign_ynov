package org.example.exercice8;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface OrderRepository extends ReactiveCrudRepository<Order, Long> {

    Flux<Order> findByStatus(String status);

    Flux<Order> findAllBy(Pageable pageable);

    Flux<Order> findByCustomerName(String customerName);
}

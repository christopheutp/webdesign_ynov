package org.example.exercice14;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Map;

@WebFluxTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testGetAllOrders() {
        webTestClient.get().uri("/api/orders")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("application/json")
                .expectBodyList(Order.class)
                .hasSize(3)
                .contains(new Order(1L, "Laptop"), new Order(2L, "Phone"),new Order(3L, "Tablet"));
                //.contains(new Order(1L, "Laptop"));
    }

    @Test
    void testAddOrder() {
        webTestClient.post().uri("/api/orders")
                .bodyValue(Map.of("id", 3, "item", "Tablet"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Order.class)
                .consumeWith(response -> {
                    Order order = response.getResponseBody();
                    assert order != null;
                    assert order.getId() == 3;
                    assert order.getItem().equals("Tablet");
                });
    }
}


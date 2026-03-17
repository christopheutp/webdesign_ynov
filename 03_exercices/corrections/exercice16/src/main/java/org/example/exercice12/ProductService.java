package org.example.exercice12;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProductService {

    private final Map<Long, Product> productRepository = new HashMap<>();

    public ProductService() {
        productRepository.put(1L, new Product(1L, "Laptop", 5, 1200.50));
        productRepository.put(2L, new Product(2L, "Phone", 10, 800.00));
    }

    public Mono<Product> getProductById(Long id) {
        return Mono.justOrEmpty(productRepository.get(id))
                .switchIfEmpty(Mono.error(new RuntimeException("Produit non trouvé avec l'identifiant " + id)));
    }

    public Flux<Product> getAllProducts() {
        return Flux.fromIterable(productRepository.values());
    }

    public Mono<Void> addProduct(Product product) {
        productRepository.put(product.getId(), product);
        return Mono.empty();
    }

    public Flux<Product> getProductsByMaxPrice(double maxPrice) {
        if (maxPrice < 0) {
            // exception sans gestion locale
            throw new IllegalArgumentException("Le prix maximum ne peut pas être négatif");
        }

        return Flux.fromIterable(productRepository.values())
                .filter(product -> product.getPrice() <= maxPrice);
    }
}


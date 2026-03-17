package org.example.exercice9;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Flux<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Mono<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Mono<Product> createProduct(Product product) {
        return productRepository.save(product);
    }

    public Mono<Product> updateProduct(Long id, Product product) {
        return productRepository.findById(id)
                .flatMap(existingProduct -> {
                    existingProduct.setName(product.getName());
                    existingProduct.setPrice(product.getPrice());
                    existingProduct.setStock(product.getStock());
                    return productRepository.save(existingProduct);
                });
    }

    public Mono<Void> deleteProduct(Long id) {
        return productRepository.deleteById(id);
    }

    public Flux<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public Flux<Product> getProductsPaged(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return productRepository.findAllBy(pageable);
    }

    public Mono<Product> buyProduct(Long id, int quantity) {
        return productRepository.findById(id)
                .flatMap(product -> {
                    if (product.getStock() >= quantity) {
                        product.setStock(product.getStock() - quantity);
                        return productRepository.save(product);
                    } else {
                        return Mono.error(new IllegalArgumentException("Not enough stock available"));
                    }
                });
    }
}

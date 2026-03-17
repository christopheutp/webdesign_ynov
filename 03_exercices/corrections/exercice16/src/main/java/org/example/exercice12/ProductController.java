package org.example.exercice12;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/inventory")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Product>> getProduct(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .onErrorResume(ex -> Mono.just(ResponseEntity.notFound().build()));
    }

    @PostMapping
    public Mono<ResponseEntity<Void>> addProduct(@RequestBody Product product) {
        if (product.getPrice() < 0 || product.getName() == null) {
            return Mono.just(ResponseEntity.badRequest().build());
        }
        return productService.addProduct(product)
                .then(Mono.just(ResponseEntity.ok().build()));
    }


    @GetMapping("/price/{maxPrice}")
    public Flux<Product> getProductsByMaxPrice(@PathVariable double maxPrice) {
        return productService.getProductsByMaxPrice(maxPrice);
    }

}


package co.com.bancolombia.model.product.gateways;

import co.com.bancolombia.model.product.Product;
import reactor.core.publisher.Mono;

public interface ProductRepository {

    Mono<Product> getProduct(String id);
    Mono<Product> saveProduct(Product product);
    Mono<Product> updateProduct(Product product);
    Mono<Void> deleteProduct(String id);
    Mono<Boolean> existByName (String name);
}

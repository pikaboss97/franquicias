package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.product.gateways.ProductRepository;
import co.com.bancolombia.r2dbc.entity.ProductEntity;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class ProductReactiveRepositoryAdapter extends ReactiveAdapterOperations<Product, ProductEntity, String,
    ProductReactiveRepository> implements ProductRepository {

    public ProductReactiveRepositoryAdapter (ProductReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Product.class));
    }

    @Override
    public Mono<Product> getProduct (String id) {
        return this.repository.findById(id)
            .map(this::toEntity);
    }

    @Override
    public Mono<Product> saveProduct (Product product) {
        return this.repository.save(this.toData(product))
            .map(this::toEntity);
    }


    @Override
    public Mono<Product> updateProduct (Product product) {
        return this.repository.save(this.toData(product))
            .map(this::toEntity);
    }

    @Override
    public Mono<Void> deleteProduct (String productId) {
        return this.repository.deleteById(productId);
    }

    @Override
    public Mono<Boolean> existByName (String name) {
        return this.repository.findByName(name)
            .map(this::toEntity)
            .map(product -> true)
            .defaultIfEmpty(false);
    }

}

package co.com.bancolombia.r2dbc;

import co.com.bancolombia.dto.TopStockProductDTO;
import co.com.bancolombia.model.branchproduct.BranchProduct;
import co.com.bancolombia.model.branchproduct.gateways.BranchProductRepository;
import co.com.bancolombia.r2dbc.entity.BranchProductEntity;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class BranchProductReactiveRepositoryAdapter extends ReactiveAdapterOperations<BranchProduct, BranchProductEntity, String,
    BranchProductReactiveRepository> implements BranchProductRepository {

    public BranchProductReactiveRepositoryAdapter (BranchProductReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, BranchProduct.class));
    }

    @Override
    public Mono<BranchProduct> getBranchProduct (Integer id) {
        return this.repository.findByProductId(id)
            .map(this::toEntity);
    }

    @Override
    public Mono<BranchProduct> saveBranchProduct (BranchProduct branchProduct) {
        return this.repository.save(this.toData(branchProduct))
            .map(this::toEntity);
    }

    @Override
    public Mono<BranchProduct> updateBranchProduct (BranchProduct branchProduct) {
        return this.repository.save(this.toData(branchProduct))
            .map(this::toEntity);
    }

    @Override
    public Mono<Void> deleteBranchProduct (String id) {
        return this.repository.deleteById(id);
    }

    @Override
    public Mono<Boolean> existByBranchAndProduct (Integer branchId, Integer productId) {
        return this.repository.existsByBranchIdAndProductId(branchId, productId)
            .defaultIfEmpty(false);
    }

    @Override
    public Flux<TopStockProductDTO> getTopProductsPerBranch (String branchId) {
        return this.repository.findTopProductPerBranchByFranchiseId(Integer.parseInt(branchId));
    }

    @Override
    public Mono<BranchProduct> getByBranchAndProduct (Integer branchId, Integer productId) {
        return this.repository.getByBranchIdAndProductId(branchId, productId)
            .map(this::toEntity);
    }
}

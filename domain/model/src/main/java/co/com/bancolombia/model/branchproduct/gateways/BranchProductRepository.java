package co.com.bancolombia.model.branchproduct.gateways;

import co.com.bancolombia.dto.TopStockProductDTO;
import co.com.bancolombia.model.branchproduct.BranchProduct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BranchProductRepository {

    Mono<BranchProduct> getBranchProduct(Integer id);
    Mono<BranchProduct> saveBranchProduct(BranchProduct branchProduct);
    Mono<BranchProduct> updateBranchProduct(BranchProduct branchProduct);
    Mono<Void> deleteBranchProduct(String id);
    Mono<Boolean> existByBranchAndProduct (Integer branchId, Integer productId);
    Flux<TopStockProductDTO> getTopProductsPerBranch (String branchId);
    Mono<BranchProduct> getByBranchAndProduct (Integer branchId, Integer productId);
}

package co.com.bancolombia.usecase.product;

import co.com.bancolombia.dto.TopStockProductDTO;
import co.com.bancolombia.enums.TechnicalMessage;
import co.com.bancolombia.exceptions.BusinessException;
import co.com.bancolombia.model.branchproduct.BranchProduct;
import co.com.bancolombia.model.branchproduct.gateways.BranchProductRepository;
import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.product.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ProductUseCase {

    private final ProductRepository productRepository;
    private final BranchProductRepository branchProductRepository;


    public Mono<Product> getProduct (String id) {
        return productRepository.getProduct(id)
            .switchIfEmpty(Mono.error(new IllegalArgumentException("Product not found with id: " + id)))
            .onErrorResume(Mono::error);
    }

    public Flux<TopStockProductDTO> getTopProductsPerBranch (String branchId) {
        return branchProductRepository.getTopProductsPerBranch(branchId)
            .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.BRANCH_NOT_FOUND)))
            .onErrorResume(Mono::error);
    }

    public Mono<Product> createProduct (Product product) {
        return productRepository.existByName(product.getName())
            .filter(exists -> !exists)
            .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.PRODUCT_ALREADY_EXISTS)))
            .flatMap(data -> productRepository.saveProduct(product))
            .onErrorResume(Mono::error);
    }

    public Mono<Product> updateProduct (Product product, String productId) {
        return productRepository.getProduct(productId)
            .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.PRODUCT_NOT_FOUND)))
            .flatMap(existingFranchise -> {
                product.setId(existingFranchise.getId());
                return productRepository.updateProduct(product);
            })
            .onErrorResume(Mono::error);
    }

    public Mono<BranchProduct> saveBranchProduct (BranchProduct product) {
        return branchProductRepository.existByBranchAndProduct(product.getBranchId(), product.getProductId())
            .filter(exists -> !exists)
            .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.BRANCH_PRODUCT_ALREADY_EXISTS)))
            .flatMap(data -> branchProductRepository.saveBranchProduct(product))
            .onErrorResume(Mono::error);
    }

    public Mono<BranchProduct> updateBranchProduct (BranchProduct product) {
        return branchProductRepository.getBranchProduct(product.getProductId())
            .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.BRANCH_PRODUCT_NOT_FOUND)))
            .flatMap(existingBranchProduct -> {
                if (!existingBranchProduct.getBranchId().equals(product.getBranchId())) {
                    return Mono.error(new BusinessException(TechnicalMessage.BRANCH_PRODUCT_NOT_FOUND));
                }
                existingBranchProduct.setStock(product.getStock());
                return branchProductRepository.updateBranchProduct(existingBranchProduct);
            })
            .onErrorResume(Mono::error);
    }

    public Mono<Void> deleteBranchProduct (String productId, String branchId) {
        return branchProductRepository.getByBranchAndProduct(Integer.parseInt(productId), Integer.parseInt(branchId))
            .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.BRANCH_PRODUCT_NOT_FOUND)))
            .flatMap(existingBranchProduct ->branchProductRepository.deleteBranchProduct(existingBranchProduct.getId().toString()))
            .onErrorResume(Mono::error);
    }


}

package co.com.bancolombia.api.handler;

import co.com.bancolombia.api.dto.BranchDTO;
import co.com.bancolombia.api.dto.BranchProductDTO;
import co.com.bancolombia.api.dto.ProductDTO;
import co.com.bancolombia.api.exceptions.HandleError;
import co.com.bancolombia.api.mapper.BranchMapper;
import co.com.bancolombia.api.mapper.BranchProductMapper;
import co.com.bancolombia.api.mapper.ProductMapper;
import co.com.bancolombia.enums.TechnicalMessage;
import co.com.bancolombia.usecase.branch.BranchUseCase;
import co.com.bancolombia.usecase.product.ProductUseCase;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import static co.com.bancolombia.util.Constants.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class BranchHandler {

    private final BranchUseCase branchServicePort;
    private final ProductUseCase productServicePort;
    private final ProductMapper productMapper;
    private final BranchMapper branchMapper;
    private final BranchProductMapper branchProductMapper;

    @Operation(
        summary = "Update a product by ID",
        description = "Updates a product's name based on the provided product ID."
    )
    public Mono<ServerResponse> updateProductById (ServerRequest request) {
        String messageId = getMessageId(request);
        String productId = request.pathVariable("id");
        return request.bodyToMono(ProductDTO.class)
            .flatMap(product -> productServicePort.updateProduct(productMapper.productDTOToProduct(product), productId)
                .doOnSuccess(updated -> log.info("Product updated successfully with messageId: {}", messageId)))
            .flatMap(updated -> ServerResponse.ok().bodyValue(updated))
            .contextWrite(Context.of(X_MESSAGE_ID, messageId))
            .doOnError(ex -> log.error(FRANCHISE_ERROR, ex))
            .onErrorResume(ex -> HandleError.handleError(ex, messageId));
    }

    @Operation(
        summary = "Get top products by franchise ID",
        description = "Retrieves the top products for a given franchise."
    )
    public Mono<ServerResponse> getTopProductsByFranchiseId (ServerRequest request) {
        String messageId = getMessageId(request);
        String franchiseId = request.pathVariable("franchiseId");
        return productServicePort.getTopProductsPerBranch(franchiseId)
            .collectList()
            .flatMap(topProducts -> ServerResponse.ok().bodyValue(topProducts))
            .contextWrite(Context.of(X_MESSAGE_ID, messageId))
            .doOnError(ex -> log.error(FRANCHISE_ERROR, ex))
            .onErrorResume(ex -> HandleError.handleError(ex, messageId));
    }

    @Operation(
        summary = "Create a new franchise branch",
        description = "Creates a new branch for a franchise."
    )
    public Mono<ServerResponse> createFranchiseBranch (ServerRequest request) {
        String messageId = getMessageId(request);
        return request.bodyToMono(BranchDTO.class)
            .flatMap(branch -> branchServicePort.saveBranch(branchMapper.branchDTOToBranch(branch))
                .doOnSuccess(saved -> log.info("Branch created successfully with messageId: {}", messageId)))
            .flatMap(saved -> ServerResponse.status(HttpStatus.CREATED)
                .bodyValue(TechnicalMessage.BRANCH_CREATED.getMessage()))
            .contextWrite(Context.of(X_MESSAGE_ID, messageId))
            .doOnError(ex -> log.error(BRANCH_ERROR, ex))
            .onErrorResume(ex -> HandleError.handleError(ex, messageId));
    }

    @Operation(
        summary = "Update Branch name",
        description = "Update Franchise branch name by Branch ID"
    )
    public Mono<ServerResponse> updateFranchiseBranchById (ServerRequest request) {
        String messageId = getMessageId(request);
        String branchId = request.pathVariable("id");
        return request.bodyToMono(BranchDTO.class)
            .flatMap(branch -> branchServicePort.updateBranch(branchMapper.branchDTOToBranch(branch), branchId)
                .doOnSuccess(updated -> log.info("Branch updated successfully with messageId: {}", messageId)))
            .flatMap(updated -> ServerResponse.ok().bodyValue(updated))
            .contextWrite(Context.of(X_MESSAGE_ID, messageId))
            .doOnError(ex -> log.error(BRANCH_ERROR, ex))
            .onErrorResume(ex -> HandleError.handleError(ex, messageId));
    }

    @Operation(
        summary = "Create Branch Product",
        description = "Create a new product for a specific branch."
    )
    public Mono<ServerResponse> createBranchProduct (ServerRequest request) {
        String messageId = getMessageId(request);
        return request.bodyToMono(BranchProductDTO.class)
            .flatMap(product -> productServicePort.saveBranchProduct(branchProductMapper.branchProductDTOToBranchProduct(product)))
            .doOnSuccess(savedBranchProduct -> log.info("Branch product created successfully with messageId: {}", messageId))
            .flatMap(saved -> ServerResponse.status(HttpStatus.CREATED)
                .bodyValue(TechnicalMessage.BRANCH_PRODUCT_CREATED.getMessage()))
            .contextWrite(Context.of(X_MESSAGE_ID, messageId))
            .doOnError(ex -> log.error(BRANCH_ERROR, ex))
            .onErrorResume(ex -> HandleError.handleError(ex, messageId));
    }

    @Operation(
        summary = "Update Branch Product Stock",
        description = "Update the stock of a product in a specific branch."
    )
    public Mono<ServerResponse> updateBranchProductStock (ServerRequest request) {
        String messageId = getMessageId(request);
        return request.bodyToMono(BranchProductDTO.class)
            .flatMap(product -> productServicePort.updateBranchProduct(branchProductMapper.branchProductDTOToBranchProduct(product))
                .doOnSuccess(updated -> log.info("Branch product updated successfully with messageId: {}", messageId)))
            .flatMap(updated -> ServerResponse.ok().bodyValue(updated))
            .contextWrite(Context.of(X_MESSAGE_ID, messageId))
            .doOnError(ex -> log.error(BRANCH_ERROR, ex))
            .onErrorResume(ex -> HandleError.handleError(ex, messageId));
    }

    @Operation(
        summary = "Delete Branch Product",
        description = "Delete a product from a specific branch."
    )
    public Mono<ServerResponse> deleteBranchProduct (ServerRequest request) {
        String messageId = getMessageId(request);
        String productId = request.queryParam("productId").orElseThrow(() -> new IllegalArgumentException("Product ID is required"));
        String branchId = request.queryParam("branchId").orElseThrow(() -> new IllegalArgumentException("Branch ID is required"));

        return productServicePort.deleteBranchProduct(productId, branchId)
            .doOnSuccess(aVoid -> log.info("Branch product deleted successfully with messageId: {}", messageId))
            .then(ServerResponse.ok().bodyValue(TechnicalMessage.BRANCH_PRODUCT_DELETED.getMessage()))
            .contextWrite(Context.of(X_MESSAGE_ID, messageId))
            .doOnError(ex -> log.error(BRANCH_ERROR, ex))
            .onErrorResume(ex -> HandleError.handleError(ex, messageId));
    }

    @Operation(
        summary = "Create a new product",
        description = "Creates a new product in the system."
    )
    public Mono<ServerResponse> createProduct (ServerRequest request) {
        String messageId = getMessageId(request);
        return request.bodyToMono(ProductDTO.class)
            .flatMap(product -> productServicePort.createProduct(productMapper.productDTOToProduct(product))
                .doOnSuccess(saved -> log.info("Product created successfully with messageId: {}", messageId)))
            .flatMap(saved -> ServerResponse.status(HttpStatus.CREATED)
                .bodyValue(TechnicalMessage.PRODUCT_CREATED.getMessage()))
            .contextWrite(Context.of(X_MESSAGE_ID, messageId))
            .doOnError(ex -> log.error(PRODUCT_ERROR, ex))
            .onErrorResume(ex -> HandleError.handleError(ex, messageId));
    }

    private String getMessageId (ServerRequest serverRequest) {
        return serverRequest.headers().firstHeader(X_MESSAGE_ID);
    }
}
package co.com.bancolombia.r2dbc;

import co.com.bancolombia.dto.TopStockProductDTO;
import co.com.bancolombia.r2dbc.entity.BranchProductEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BranchProductReactiveRepository extends ReactiveCrudRepository<BranchProductEntity, String>, ReactiveQueryByExampleExecutor<BranchProductEntity> {

    Mono<Boolean> existsByBranchIdAndProductId (Integer branchId, Integer productId);

    @Query("""
        SELECT b.name AS branch_name,
               p.name AS product_name,
               bp.stock AS stock
        FROM branch b
        JOIN branch_product bp ON b.id = bp.branch_id
        JOIN product p ON p.id = bp.product_id
        WHERE b.franchise_id = :franchiseId
          AND bp.product_id = (
              SELECT bp2.product_id
              FROM branch_product bp2
              WHERE bp2.branch_id = b.id
              ORDER BY bp2.stock DESC
              LIMIT 1
          )
        """)
    Flux<TopStockProductDTO> findTopProductPerBranchByFranchiseId (Integer franchiseId);

    Mono<BranchProductEntity> findByProductId (Integer productId);

    Mono<BranchProductEntity> getByBranchIdAndProductId (Integer branchId, Integer productId);
}

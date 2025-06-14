package co.com.bancolombia.r2dbc;

import co.com.bancolombia.r2dbc.entity.BranchEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface BranchReactiveRepository extends ReactiveCrudRepository<BranchEntity, String>, ReactiveQueryByExampleExecutor<BranchEntity> {

    Mono<BranchEntity> findByName (String name);
}

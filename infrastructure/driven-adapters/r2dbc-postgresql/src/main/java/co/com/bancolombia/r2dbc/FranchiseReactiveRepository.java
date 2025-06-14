package co.com.bancolombia.r2dbc;

import co.com.bancolombia.r2dbc.entity.FranchiseEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

// TODO: This file is just an example, you should delete or modify it
public interface FranchiseReactiveRepository extends ReactiveCrudRepository<FranchiseEntity, String>, ReactiveQueryByExampleExecutor<FranchiseEntity> {

    Mono<FranchiseEntity> findByName (String name);
}

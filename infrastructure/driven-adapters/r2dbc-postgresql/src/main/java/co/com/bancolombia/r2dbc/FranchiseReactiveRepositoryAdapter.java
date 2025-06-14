package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import co.com.bancolombia.r2dbc.entity.FranchiseEntity;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class FranchiseReactiveRepositoryAdapter extends ReactiveAdapterOperations<Franchise, FranchiseEntity, String,
    FranchiseReactiveRepository> implements FranchiseRepository {

    public FranchiseReactiveRepositoryAdapter (FranchiseReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Franchise.class));
    }


    @Override
    public Mono<Franchise> getFranchise (String id) {
        return this.repository.findById(id)
            .map(this::toEntity);
    }

    @Override
    public Mono<Boolean> existByName (String name) {
        return this.repository.findByName(name)
            .map(this::toEntity)
            .map(franchise -> true)
            .defaultIfEmpty(false);
    }

    @Override
    public Mono<Franchise> saveFranchise (Franchise franchise) {
        return this.repository.save(this.toData(franchise))
            .map(this::toEntity);
    }

    @Override
    public Mono<Franchise> updateFranchise (Franchise franchise) {
        return this.repository.save(this.toData(franchise))
            .map(this::toEntity);
    }

    @Override
    public Mono<Void> deleteFranchise (String id) {
        return null;
    }
}

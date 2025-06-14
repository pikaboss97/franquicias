package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.branch.gateways.BranchRepository;
import co.com.bancolombia.r2dbc.entity.BranchEntity;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class BranchReactiveRepositoryAdapter extends ReactiveAdapterOperations<Branch, BranchEntity, String,
    BranchReactiveRepository> implements BranchRepository {

    public BranchReactiveRepositoryAdapter (BranchReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Branch.class));
    }

    @Override
    public Mono<Branch> getBranch (String id) {
        return this.repository.findById(id)
            .map(this::toEntity);
    }

    @Override
    public Mono<Boolean> existByName (String name) {
        return this.repository.findByName(name)
            .map(this::toEntity)
            .map(branch -> true)
            .defaultIfEmpty(false);
    }

    @Override
    public Mono<Branch> saveBranch (Branch branch) {
        return this.repository.save(this.toData(branch))
            .map(this::toEntity);
    }

    @Override
    public Mono<Branch> updateBranch (Branch branch) {
        return this.repository.save(this.toData(branch))
            .map(this::toEntity);
    }

    @Override
    public Mono<Void> deleteBranch (String id) {
        return null;
    }


}

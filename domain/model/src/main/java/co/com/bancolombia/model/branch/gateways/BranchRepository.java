package co.com.bancolombia.model.branch.gateways;

import co.com.bancolombia.model.branch.Branch;
import reactor.core.publisher.Mono;

public interface BranchRepository {

    Mono<Branch> getBranch(String id);
    Mono<Branch> saveBranch(Branch branch);
    Mono<Branch> updateBranch(Branch branch);
    Mono<Void> deleteBranch(String id);
    Mono<Boolean> existByName (String name);
}

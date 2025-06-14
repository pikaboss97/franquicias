package co.com.bancolombia.usecase.branch;

import co.com.bancolombia.enums.TechnicalMessage;
import co.com.bancolombia.exceptions.BusinessException;
import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.branch.gateways.BranchRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class BranchUseCase {

    private final BranchRepository branchRepository;

    public Mono<Branch> getBranch (String id) {
        return branchRepository.getBranch(id)
            .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.BRANCH_NOT_FOUND)))
            .onErrorResume(Mono::error);
    }

    public Mono<Branch> saveBranch (Branch branch) {
        return branchRepository.existByName(branch.getName())
            .filter(exists -> !exists)
            .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.BRANCH_ALREADY_EXISTS)))
            .flatMap(data -> branchRepository.saveBranch(branch))
            .onErrorResume(Mono::error);
    }

    public Mono<Branch> updateBranch (Branch branch, String branchId) {
        return branchRepository.getBranch(branchId)
            .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.FRANCHISE_NOT_FOUND)))
            .flatMap(existingFranchise -> {
                branch.setId(existingFranchise.getId());
                branch.setFranchiseId(existingFranchise.getFranchiseId());
                return branchRepository.updateBranch(branch);
            })
            .onErrorResume(Mono::error);
    }
}

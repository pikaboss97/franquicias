package co.com.bancolombia.usecase.franchise;

import co.com.bancolombia.enums.TechnicalMessage;
import co.com.bancolombia.exceptions.BusinessException;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FranchiseUseCase {

    private final FranchiseRepository franchiseRepository;


    public Mono<Franchise> getFranchise (String id) {
        return franchiseRepository.getFranchise(id)
            .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.FRANCHISE_NOT_FOUND)))
            .onErrorResume(Mono::error);
    }

    public Mono<Franchise> saveFranchise (Franchise franchise) {
        return franchiseRepository.existByName(franchise.getName())
            .filter(exists -> !exists)
            .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.FRANCHISE_ALREADY_EXISTS)))
            .flatMap(data -> franchiseRepository.saveFranchise(franchise))
            .onErrorResume(Mono::error);
    }

    public Mono<Franchise> updateFranchise (Franchise franchise, String franchiseId) {
        return franchiseRepository.getFranchise(franchiseId)
            .switchIfEmpty(Mono.error(new BusinessException(TechnicalMessage.FRANCHISE_NOT_FOUND)))
            .flatMap(existingFranchise -> {
                franchise.setId(existingFranchise.getId());
                return franchiseRepository.updateFranchise(franchise);
            })
            .onErrorResume(Mono::error);
    }
}

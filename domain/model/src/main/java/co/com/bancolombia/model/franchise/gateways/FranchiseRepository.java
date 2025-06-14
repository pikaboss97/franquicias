package co.com.bancolombia.model.franchise.gateways;

import co.com.bancolombia.model.franchise.Franchise;
import reactor.core.publisher.Mono;

public interface FranchiseRepository {

    Mono<Franchise> getFranchise(String id);
    Mono<Boolean> existByName(String name);
    Mono<Franchise> saveFranchise(Franchise franchise);
    Mono<Franchise> updateFranchise(Franchise franchise);
    Mono<Void> deleteFranchise(String id);
}

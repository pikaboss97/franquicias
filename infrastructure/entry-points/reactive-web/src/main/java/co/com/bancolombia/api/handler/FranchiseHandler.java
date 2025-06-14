package co.com.bancolombia.api.handler;

import co.com.bancolombia.api.dto.FranchiseDTO;
import co.com.bancolombia.api.exceptions.HandleError;
import co.com.bancolombia.api.mapper.FranchiseMapper;
import co.com.bancolombia.enums.TechnicalMessage;
import co.com.bancolombia.usecase.franchise.FranchiseUseCase;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import static co.com.bancolombia.util.Constants.FRANCHISE_ERROR;
import static co.com.bancolombia.util.Constants.X_MESSAGE_ID;

@Component
@RequiredArgsConstructor
@Slf4j
public class FranchiseHandler {

    private final FranchiseUseCase franchiseServicePort;
    private final FranchiseMapper franchiseMapper;

    @Operation(
        summary = "Get franchise by ID",
        description = "Retrieves a franchise by its unique identifier."
    )
    public Mono<ServerResponse> getFranchiseById (ServerRequest request) {
        String messageId = getMessageId(request);
        String franchiseId = request.pathVariable("id");
        return franchiseServicePort.getFranchise(franchiseId)
            .flatMap(franchise -> ServerResponse.ok().bodyValue(franchise))
            .contextWrite(Context.of(X_MESSAGE_ID, messageId))
            .doOnError(ex -> log.error(FRANCHISE_ERROR, ex))
            .onErrorResume(ex -> HandleError.handleError(ex, messageId));
    }

    @Operation(
        summary = "Create franchise",
        description = "Creates a new franchise with the provided details."
    )
    public Mono<ServerResponse> createFranchise (ServerRequest request) {
        String messageId = getMessageId(request);
        return request.bodyToMono(FranchiseDTO.class)
            .flatMap(franchise -> franchiseServicePort.saveFranchise(franchiseMapper.franchiseDTOToFranchise(franchise))
                .doOnSuccess(saved -> log.info("User created successfully with messageId: {}", messageId)))
            .flatMap(saved -> ServerResponse.status(HttpStatus.CREATED)
                .bodyValue(TechnicalMessage.FRANCHISE_CREATED.getMessage()))
            .contextWrite(Context.of(X_MESSAGE_ID, messageId))
            .doOnError(ex -> log.error(FRANCHISE_ERROR, ex))
            .onErrorResume(ex -> HandleError.handleError(ex, messageId));
    }

    @Operation(
        summary = "Update franchise by ID",
        description = "Updates an existing franchise based on the provided ID."
    )
    public Mono<ServerResponse> updateFranchiseById (ServerRequest request) {
        String messageId = getMessageId(request);
        String franchiseId = request.pathVariable("id");
        return request.bodyToMono(FranchiseDTO.class)
            .flatMap(franchise -> franchiseServicePort.updateFranchise(franchiseMapper.franchiseDTOToFranchise(franchise), franchiseId)
                .doOnSuccess(updated -> log.info("Franchise updated successfully with messageId: {}", messageId)))
            .flatMap(updated -> ServerResponse.ok().bodyValue(updated))
            .contextWrite(Context.of(X_MESSAGE_ID, messageId))
            .doOnError(ex -> log.error(FRANCHISE_ERROR, ex))
            .onErrorResume(ex -> HandleError.handleError(ex, messageId));
    }


    private String getMessageId (ServerRequest serverRequest) {
        return serverRequest.headers().firstHeader(X_MESSAGE_ID);
    }


}
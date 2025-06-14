package co.com.bancolombia.api.exceptions;

import co.com.bancolombia.enums.TechnicalMessage;
import co.com.bancolombia.exceptions.BusinessException;
import co.com.bancolombia.exceptions.TechnicalException;
import co.com.bancolombia.util.APIResponse;
import co.com.bancolombia.util.ErrorDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;

import static co.com.bancolombia.util.Constants.X_MESSAGE_ID;

@RequiredArgsConstructor
@Slf4j
public class HandleError {

    public static Mono<ServerResponse> handleError (Throwable ex, String messageId) {
        if (ex instanceof BusinessException businessEx) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, messageId, TechnicalMessage.INVALID_PARAMETERS,
                List.of(ErrorDTO.builder()
                    .code(businessEx.getTechnicalMessage().getCode())
                    .message(businessEx.getTechnicalMessage().getMessage())
                    .param(businessEx.getTechnicalMessage().getParam())
                    .build()));
        } else if (ex instanceof TechnicalException technicalEx) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, messageId, TechnicalMessage.INTERNAL_ERROR,
                List.of(ErrorDTO.builder()
                    .code(technicalEx.getTechnicalMessage().getCode())
                    .message(technicalEx.getTechnicalMessage().getMessage())
                    .param(technicalEx.getTechnicalMessage().getParam())
                    .build()));
        } else {
            log.error("Unexpected error occurred for messageId: {}", messageId, ex);
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, messageId, TechnicalMessage.INTERNAL_ERROR,
                List.of(ErrorDTO.builder()
                    .code(TechnicalMessage.INTERNAL_ERROR.getCode())
                    .message(TechnicalMessage.INTERNAL_ERROR.getMessage())
                    .build()));
        }
    }

    private static Mono<ServerResponse> buildErrorResponse (HttpStatus httpStatus, String identifier, TechnicalMessage error,
                                                            List<ErrorDTO> errors) {
        APIResponse apiErrorResponse = APIResponse.builder()
            .code(error.getCode())
            .message(error.getMessage())
            .identifier(identifier)
            .date(Instant.now().toString())
            .errors(errors)
            .build();
        return ServerResponse.status(httpStatus).bodyValue(apiErrorResponse);
    }

}

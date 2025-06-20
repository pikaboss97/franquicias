package co.com.bancolombia.exceptions;

import co.com.bancolombia.enums.TechnicalMessage;
import lombok.Getter;


@Getter
public class BusinessException extends ProcessorException {

    public BusinessException(TechnicalMessage technicalMessage) {
        super(technicalMessage.getMessage(), technicalMessage);
    }
}

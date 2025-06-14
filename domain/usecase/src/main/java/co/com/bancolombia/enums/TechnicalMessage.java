package co.com.bancolombia.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TechnicalMessage {

    INTERNAL_ERROR("500", "Something went wrong, please try again", ""),
    INTERNAL_ERROR_IN_ADAPTERS("PRC501", "Something went wrong in adapters, please try again", ""),
    INVALID_REQUEST("400", "Bad Request, please verify data", ""),
    INVALID_PARAMETERS(INVALID_REQUEST.getCode(), "Bad Parameters, please verify data", ""),
    INVALID_EMAIL("403", "Invalid email, please verify", "email"),
    INVALID_MESSAGE_ID("404", "Invalid Message ID, please verify", "messageId"),
    UNSUPPORTED_OPERATION("501", "Method not supported, please try again", ""),
    FRANCHISE_CREATED("201", "Franchise created successfully", ""),
    ADAPTER_RESPONSE_NOT_FOUND("404-0", "invalid email, please verify", ""),
    FRANCHISE_ALREADY_EXISTS("400", "The Franchise is already registered.", ""),
    FRANCHISE_NOT_FOUND("404-0", "Franchise not found", ""),
    BRANCH_NOT_FOUND("404-0", "Branch not found", ""),
    BRANCH_ALREADY_EXISTS("400", "The Branch is already registered.", ""),
    PRODUCT_ALREADY_EXISTS("400", "The Product is already registered.", ""),
    PRODUCT_NOT_FOUND("404-0", "Product not found", ""),
    BRANCH_PRODUCT_ALREADY_EXISTS("400", "The Product is already registered on branch.", ""),
    BRANCH_PRODUCT_NOT_FOUND("404-0", "Branch Product not found", ""),
    BRANCH_CREATED("201", "Branch created successfully", ""),
    BRANCH_PRODUCT_CREATED("201", "Branch Product created successfully", ""),
    PRODUCT_CREATED("201", "Product created successfully", ""),
    BRANCH_PRODUCT_DELETED ("200", "Branch Product deleted successfully", "");
    private final String code;
    private final String message;
    private final String param;
}
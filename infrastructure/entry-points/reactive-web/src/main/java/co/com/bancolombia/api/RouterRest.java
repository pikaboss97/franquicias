package co.com.bancolombia.api;

import co.com.bancolombia.api.handler.BranchHandler;
import co.com.bancolombia.api.handler.FranchiseHandler;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    @Bean
    @RouterOperations({
        @RouterOperation(
            path = "/api/franchise/{id}",
            method = RequestMethod.GET,
            beanClass = FranchiseHandler.class,
            beanMethod = "getFranchiseById"
        ),
        @RouterOperation(
            path = "/api/franchise",
            method = RequestMethod.POST,
            beanClass = FranchiseHandler.class,
            beanMethod = "createFranchise"
        ),
        @RouterOperation(
            path = "/api/franchise/{id}",
            method = RequestMethod.PUT,
            beanClass = FranchiseHandler.class,
            beanMethod = "updateFranchiseById"
        ),
        @RouterOperation(
            path = "/api/branch",
            method = RequestMethod.POST,
            beanClass = BranchHandler.class,
            beanMethod = "createFranchiseBranch"
        ),
        @RouterOperation(
            path = "/api/branch/{id}",
            method = RequestMethod.PUT,
            beanClass = BranchHandler.class,
            beanMethod = "updateFranchiseBranchById"
        ),
        @RouterOperation(
            path = "/api/branch/product",
            method = RequestMethod.POST,
            beanClass = BranchHandler.class,
            beanMethod = "createBranchProduct"
        ),
        @RouterOperation(
            path = "/api/branch/product/{productId}",
            method = RequestMethod.PUT,
            beanClass = BranchHandler.class,
            beanMethod = "updateBranchProductStock"
        ),
        @RouterOperation(
            path = "/api/branch/product",
            method = RequestMethod.DELETE,
            beanClass = BranchHandler.class,
            beanMethod = "deleteBranchProduct"
        ),
        @RouterOperation(
            path = "/api/product",
            method = RequestMethod.POST,
            beanClass = BranchHandler.class,
            beanMethod = "createProduct"
        ),
        @RouterOperation(
            path = "/api/product/{id}",
            method = RequestMethod.PUT,
            beanClass = BranchHandler.class,
            beanMethod = "updateProductById"
        ),
        @RouterOperation(
            path = "/api/{franchiseId}/top-products",
            method = RequestMethod.GET,
            beanClass = BranchHandler.class,
            beanMethod = "getTopProductsByFranchiseId"
        )
    })
    public RouterFunction<ServerResponse> routerFunction (FranchiseHandler franchiseHandler, BranchHandler branchHandler) {
        return route(GET("/api/franchise/{id}"), franchiseHandler::getFranchiseById)
            .andRoute(POST("/api/franchise"), franchiseHandler::createFranchise)
            .andRoute(PUT("/api/franchise/{id}"), franchiseHandler::updateFranchiseById)
            .andRoute(POST("/api/branch"), branchHandler::createFranchiseBranch)
            .andRoute(PUT("/api/branch/{id}"), branchHandler::updateFranchiseBranchById)
            .andRoute(POST("/api/branch/product"), branchHandler::createBranchProduct)
            .andRoute(PUT("/api/branch/product/{productId}"), branchHandler::updateBranchProductStock)
            .andRoute(DELETE("/api/branch/product"), branchHandler::deleteBranchProduct)
            .andRoute(POST("/api/product"), branchHandler::createProduct)
            .andRoute(PUT("/api/product/{id}"), branchHandler::updateProductById)
            .andRoute(GET("/api/{franchiseId}/top-products"), branchHandler::getTopProductsByFranchiseId);
    }
}
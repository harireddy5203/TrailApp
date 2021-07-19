/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.web.api;

import com.harik.inno.commons.data.utils.PageUtils;
import com.harik.inno.commons.web.api.AbstractApi;
import com.harik.inno.commons.web.configuration.properties.ApiDocumentationSettings;
import com.harik.inno.features.platform.data.model.experience.stock.CreateStockRequest;
import com.harik.inno.features.platform.data.model.experience.stock.Stock;
import com.harik.inno.features.platform.data.model.experience.stock.UpdateStockRequest;
import com.harik.inno.features.platform.web.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementation of APIs that provide CRUD (Create, Read, Update and Delete) functionality for
 * persistence models of type {@link
 * com.harik.inno.features.platform.data.model.persistence.StockEntity}.
 *
 * @author Admin
 */
@Slf4j
@RestController
@RequestMapping(StockApi.rootEndPoint)
public class StockApi extends AbstractApi {
    /** Tag for this API. */
    public static final String API_TAG = "Stocks";

    /** Root end point. */
    public static final String rootEndPoint = "/ecom-sales";

    /** Service implementation of type {@link StockService}. */
    private final StockService stockService;

    /**
     * Constructor.
     *
     * @param stockService Service instance of type {@link StockService}.
     */
    public StockApi(final StockService stockService) {
        this.stockService = stockService;
    }

    /**
     * This API provides the capability to add a new instance of type {@link
     * com.harik.inno.features.platform.data.model.persistence.StockEntity} into the system.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     com.harik.inno.features.platform.data.model.persistence.StockEntity}.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link Stock}.
     */
    @Operation(
            method = "createStock",
            summary = "Create a new Stock.",
            description = "This API is used to create a new Stock in the system.",
            tags = {StockApi.API_TAG},
            security = {
                @SecurityRequirement(
                        name =
                                ApiDocumentationSettings.ApiSecurityScheme
                                        .DEFAULT_SECURITY_SCHEME_NAME)
            })
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Successfully created a new Stock in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PostMapping(value = "/stocks")
    public ResponseEntity<Stock> createStock(@Valid @RequestBody final CreateStockRequest payload) {
        // Delegate to the service layer.
        final Stock newInstance = stockService.createStock(payload);

        // Build a response entity object and return it.
        return ResponseEntity.status(HttpStatus.CREATED).body(newInstance);
    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.harik.inno.features.platform.data.model.persistence.StockEntity} in the system.
     *
     * @param stockId Unique identifier of Stock in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Stock, which needs to be
     *     updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link Stock}.
     */
    @Operation(
            method = "updateStock",
            summary = "Update an existing Stock.",
            description = "This API is used to update an existing Stock in the system.",
            tags = {StockApi.API_TAG},
            security = {
                @SecurityRequirement(
                        name =
                                ApiDocumentationSettings.ApiSecurityScheme
                                        .DEFAULT_SECURITY_SCHEME_NAME)
            })
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successfully updated an existing Stock in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PutMapping(value = "/stocks/{stockId}")
    public ResponseEntity<Stock> updateStock(
            @PathVariable(name = "stockId") final Integer stockId,
            @Valid @RequestBody final UpdateStockRequest payload) {
        // Delegate to the service layer.
        final Stock updatedInstance = stockService.updateStock(stockId, payload);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to retrieve the details of an existing {@link
     * com.harik.inno.features.platform.data.model.persistence.StockEntity} in the system.
     *
     * @param stockId Unique identifier of Stock in the system, whose details have to be retrieved.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link Stock}.
     */
    @Operation(
            method = "findStock",
            summary = "Find an existing Stock.",
            description = "This API is used to find an existing Stock in the system.",
            tags = {StockApi.API_TAG},
            security = {
                @SecurityRequirement(
                        name =
                                ApiDocumentationSettings.ApiSecurityScheme
                                        .DEFAULT_SECURITY_SCHEME_NAME)
            })
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description =
                                "Successfully retrieved the details of an existing Stock in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/stocks/{stockId}")
    public ResponseEntity<Stock> findStock(@PathVariable(name = "stockId") final Integer stockId) {
        // Delegate to the service layer.
        final Stock matchingInstance = stockService.findStock(stockId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstance);
    }

    /**
     * This API provides the capability to retrieve all instances of type {@link
     * com.harik.inno.features.platform.data.model.persistence.StockEntity} in the system in a
     * paginated manner.
     *
     * @param page Page number.
     * @param size Page size.
     * @return Response of type {@link ResponseEntity} that holds a page of instances of type Stock
     *     based on the provided pagination settings.
     */
    @Operation(
            method = "findAllStocks",
            summary = "Find all Stocks.",
            description = "This API is used to find all Stocks in the system.",
            tags = {StockApi.API_TAG},
            security = {
                @SecurityRequirement(
                        name =
                                ApiDocumentationSettings.ApiSecurityScheme
                                        .DEFAULT_SECURITY_SCHEME_NAME)
            })
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description =
                                "Successfully retrieved the Stocks in the system based on the provided pagination settings.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/stocks")
    public ResponseEntity<Page<Stock>> findAllStocks(
            @RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "20")
                    final Integer size) {
        // Delegate to the service layer.
        final Pageable pageSettings = PageUtils.createPaginationConfiguration(page, size);
        final Page<Stock> matchingInstances = stockService.findAllStocks(pageSettings);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstances);
    }

    /**
     * This API provides the capability to delete an existing instance of type {@link
     * com.harik.inno.features.platform.data.model.persistence.StockEntity} in the system.
     *
     * @param stockId Unique identifier of Stock in the system, which needs to be deleted.
     * @return Response of type {@link ResponseEntity} that holds the unique identifier of the
     *     {@link com.harik.inno.features.platform.data.model.persistence.StockEntity} that was
     *     deleted from the system.
     */
    @Operation(
            method = "deleteStock",
            summary = "Delete an existing Stock.",
            description = "This API is used to delete an existing Stock in the system.",
            tags = {StockApi.API_TAG},
            security = {
                @SecurityRequirement(
                        name =
                                ApiDocumentationSettings.ApiSecurityScheme
                                        .DEFAULT_SECURITY_SCHEME_NAME)
            })
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successfully deleted an existing Stock in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @DeleteMapping(value = "/stocks/{stockId}")
    public ResponseEntity<Integer> deleteStock(
            @PathVariable(name = "stockId") final Integer stockId) {
        // Delegate to the service layer.
        final Integer deletedInstance = stockService.deleteStock(stockId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(deletedInstance);
    }
}

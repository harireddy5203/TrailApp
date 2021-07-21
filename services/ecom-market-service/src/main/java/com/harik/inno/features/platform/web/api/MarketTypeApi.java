/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.web.api;

import com.harik.inno.commons.data.utils.PageUtils;
import com.harik.inno.commons.web.api.AbstractApi;
import com.harik.inno.commons.web.configuration.properties.ApiDocumentationSettings;
import com.harik.inno.features.platform.data.model.experience.markettype.CreateMarketTypeRequest;
import com.harik.inno.features.platform.data.model.experience.markettype.MarketType;
import com.harik.inno.features.platform.data.model.experience.markettype.UpdateMarketTypeRequest;
import com.harik.inno.features.platform.web.service.MarketTypeService;
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
 * com.harik.inno.features.platform.data.model.persistence.MarketTypeEntity}.
 *
 * @author Admin
 */
@Slf4j
@RestController
@RequestMapping(MarketTypeApi.rootEndPoint)
public class MarketTypeApi extends AbstractApi {
    /** Tag for this API. */
    public static final String API_TAG = "MarketTypes";

    /** Root end point. */
    public static final String rootEndPoint = "/ecom-market";

    /** Service implementation of type {@link MarketTypeService}. */
    private final MarketTypeService marketTypeService;

    /**
     * Constructor.
     *
     * @param marketTypeService Service instance of type {@link MarketTypeService}.
     */
    public MarketTypeApi(final MarketTypeService marketTypeService) {
        this.marketTypeService = marketTypeService;
    }

    /**
     * This API provides the capability to add a new instance of type {@link
     * com.harik.inno.features.platform.data.model.persistence.MarketTypeEntity} into the system.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     com.harik.inno.features.platform.data.model.persistence.MarketTypeEntity}.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     MarketType}.
     */
    @Operation(
            method = "createMarketType",
            summary = "Create a new MarketType.",
            description = "This API is used to create a new MarketType in the system.",
            tags = {MarketTypeApi.API_TAG},
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
                        description = "Successfully created a new MarketType in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "hasRole('Admin')")
    @PostMapping(value = "/marketTypes")
    public ResponseEntity<MarketType> createMarketType(
            @Valid @RequestBody final CreateMarketTypeRequest payload) {
        // Delegate to the service layer.
        final MarketType newInstance = marketTypeService.createMarketType(payload);

        // Build a response entity object and return it.
        return ResponseEntity.status(HttpStatus.CREATED).body(newInstance);
    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.harik.inno.features.platform.data.model.persistence.MarketTypeEntity} in the system.
     *
     * @param marketTypeId Unique identifier of MarketType in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing MarketType, which needs
     *     to be updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     MarketType}.
     */
    @Operation(
            method = "updateMarketType",
            summary = "Update an existing MarketType.",
            description = "This API is used to update an existing MarketType in the system.",
            tags = {MarketTypeApi.API_TAG},
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
                        description = "Successfully updated an existing MarketType in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PutMapping(value = "/marketTypes/{marketTypeId}")
    public ResponseEntity<MarketType> updateMarketType(
            @PathVariable(name = "marketTypeId") final Integer marketTypeId,
            @Valid @RequestBody final UpdateMarketTypeRequest payload) {
        // Delegate to the service layer.
        final MarketType updatedInstance =
                marketTypeService.updateMarketType(marketTypeId, payload);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to retrieve the details of an existing {@link
     * com.harik.inno.features.platform.data.model.persistence.MarketTypeEntity} in the system.
     *
     * @param marketTypeId Unique identifier of MarketType in the system, whose details have to be
     *     retrieved.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     MarketType}.
     */
    @Operation(
            method = "findMarketType",
            summary = "Find an existing MarketType.",
            description = "This API is used to find an existing MarketType in the system.",
            tags = {MarketTypeApi.API_TAG},
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
                                "Successfully retrieved the details of an existing MarketType in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "hasAnyRole('Admin','SalesHead')")
    @GetMapping(value = "/marketTypes/{marketTypeId}")
    public ResponseEntity<MarketType> findMarketType(
            @PathVariable(name = "marketTypeId") final Integer marketTypeId) {
        // Delegate to the service layer.
        final MarketType matchingInstance = marketTypeService.findMarketType(marketTypeId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstance);
    }

    /**
     * This API provides the capability to retrieve all instances of type {@link
     * com.harik.inno.features.platform.data.model.persistence.MarketTypeEntity} in the system in a
     * paginated manner.
     *
     * @param page Page number.
     * @param size Page size.
     * @return Response of type {@link ResponseEntity} that holds a page of instances of type
     *     MarketType based on the provided pagination settings.
     */
    @Operation(
            method = "findAllMarketTypes",
            summary = "Find all MarketTypes.",
            description = "This API is used to find all MarketTypes in the system.",
            tags = {MarketTypeApi.API_TAG},
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
                                "Successfully retrieved the MarketTypes in the system based on the provided pagination settings.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/marketTypes")
    public ResponseEntity<Page<MarketType>> findAllMarketTypes(
            @RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "20")
                    final Integer size) {
        // Delegate to the service layer.
        final Pageable pageSettings = PageUtils.createPaginationConfiguration(page, size);
        final Page<MarketType> matchingInstances =
                marketTypeService.findAllMarketTypes(pageSettings);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstances);
    }

    /**
     * This API provides the capability to delete an existing instance of type {@link
     * com.harik.inno.features.platform.data.model.persistence.MarketTypeEntity} in the system.
     *
     * @param marketTypeId Unique identifier of MarketType in the system, which needs to be deleted.
     * @return Response of type {@link ResponseEntity} that holds the unique identifier of the
     *     {@link com.harik.inno.features.platform.data.model.persistence.MarketTypeEntity} that was
     *     deleted from the system.
     */
    @Operation(
            method = "deleteMarketType",
            summary = "Delete an existing MarketType.",
            description = "This API is used to delete an existing MarketType in the system.",
            tags = {MarketTypeApi.API_TAG},
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
                        description = "Successfully deleted an existing MarketType in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @DeleteMapping(value = "/marketTypes/{marketTypeId}")
    public ResponseEntity<Integer> deleteMarketType(
            @PathVariable(name = "marketTypeId") final Integer marketTypeId) {
        // Delegate to the service layer.
        final Integer deletedInstance = marketTypeService.deleteMarketType(marketTypeId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(deletedInstance);
    }
}

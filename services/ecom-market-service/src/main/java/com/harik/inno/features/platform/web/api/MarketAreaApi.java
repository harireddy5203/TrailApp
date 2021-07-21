/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.web.api;

import com.harik.inno.commons.data.utils.PageUtils;
import com.harik.inno.commons.web.api.AbstractApi;
import com.harik.inno.commons.web.configuration.properties.ApiDocumentationSettings;
import com.harik.inno.features.platform.data.model.experience.marketarea.CreateMarketAreaRequest;
import com.harik.inno.features.platform.data.model.experience.marketarea.MarketArea;
import com.harik.inno.features.platform.data.model.experience.marketarea.UpdateMarketAreaRequest;
import com.harik.inno.features.platform.web.service.MarketAreaService;
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
 * com.harik.inno.features.platform.data.model.persistence.MarketAreaEntity}.
 *
 * @author Admin
 */
@Slf4j
@RestController
@RequestMapping(MarketAreaApi.rootEndPoint)
public class MarketAreaApi extends AbstractApi {
    /** Tag for this API. */
    public static final String API_TAG = "MarketAreas";

    /** Root end point. */
    public static final String rootEndPoint = "/ecom-market";

    /** Service implementation of type {@link MarketAreaService}. */
    private final MarketAreaService marketAreaService;

    /**
     * Constructor.
     *
     * @param marketAreaService Service instance of type {@link MarketAreaService}.
     */
    public MarketAreaApi(final MarketAreaService marketAreaService) {
        this.marketAreaService = marketAreaService;
    }

    /**
     * This API provides the capability to add a new instance of type {@link
     * com.harik.inno.features.platform.data.model.persistence.MarketAreaEntity} into the system.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     com.harik.inno.features.platform.data.model.persistence.MarketAreaEntity}.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     MarketArea}.
     */
    @Operation(
            method = "createMarketArea",
            summary = "Create a new MarketArea.",
            description = "This API is used to create a new MarketArea in the system.",
            tags = {MarketAreaApi.API_TAG},
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
                        description = "Successfully created a new MarketArea in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PostMapping(value = "/marketAreas")
    public ResponseEntity<MarketArea> createMarketArea(
            @Valid @RequestBody final CreateMarketAreaRequest payload) {
        // Delegate to the service layer.
        final MarketArea newInstance = marketAreaService.createMarketArea(payload);

        // Build a response entity object and return it.
        return ResponseEntity.status(HttpStatus.CREATED).body(newInstance);
    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.harik.inno.features.platform.data.model.persistence.MarketAreaEntity} in the system.
     *
     * @param marketAreaId Unique identifier of MarketArea in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing MarketArea, which needs
     *     to be updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     MarketArea}.
     */
    @Operation(
            method = "updateMarketArea",
            summary = "Update an existing MarketArea.",
            description = "This API is used to update an existing MarketArea in the system.",
            tags = {MarketAreaApi.API_TAG},
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
                        description = "Successfully updated an existing MarketArea in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PutMapping(value = "/marketAreas/{marketAreaId}")
    public ResponseEntity<MarketArea> updateMarketArea(
            @PathVariable(name = "marketAreaId") final Integer marketAreaId,
            @Valid @RequestBody final UpdateMarketAreaRequest payload) {
        // Delegate to the service layer.
        final MarketArea updatedInstance =
                marketAreaService.updateMarketArea(marketAreaId, payload);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to retrieve the details of an existing {@link
     * com.harik.inno.features.platform.data.model.persistence.MarketAreaEntity} in the system.
     *
     * @param marketAreaId Unique identifier of MarketArea in the system, whose details have to be
     *     retrieved.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     MarketArea}.
     */
    @Operation(
            method = "findMarketArea",
            summary = "Find an existing MarketArea.",
            description = "This API is used to find an existing MarketArea in the system.",
            tags = {MarketAreaApi.API_TAG},
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
                                "Successfully retrieved the details of an existing MarketArea in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/marketAreas/{marketAreaId}")
    public ResponseEntity<MarketArea> findMarketArea(
            @PathVariable(name = "marketAreaId") final Integer marketAreaId) {
        // Delegate to the service layer.
        final MarketArea matchingInstance = marketAreaService.findMarketArea(marketAreaId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstance);
    }

    /**
     * This API provides the capability to retrieve all instances of type {@link
     * com.harik.inno.features.platform.data.model.persistence.MarketAreaEntity} in the system in a
     * paginated manner.
     *
     * @param page Page number.
     * @param size Page size.
     * @return Response of type {@link ResponseEntity} that holds a page of instances of type
     *     MarketArea based on the provided pagination settings.
     */
    @Operation(
            method = "findAllMarketAreas",
            summary = "Find all MarketAreas.",
            description = "This API is used to find all MarketAreas in the system.",
            tags = {MarketAreaApi.API_TAG},
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
                                "Successfully retrieved the MarketAreas in the system based on the provided pagination settings.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/marketAreas")
    public ResponseEntity<Page<MarketArea>> findAllMarketAreas(
            @RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "20")
                    final Integer size) {
        // Delegate to the service layer.
        final Pageable pageSettings = PageUtils.createPaginationConfiguration(page, size);
        final Page<MarketArea> matchingInstances =
                marketAreaService.findAllMarketAreas(pageSettings);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstances);
    }

    /**
     * This API provides the capability to delete an existing instance of type {@link
     * com.harik.inno.features.platform.data.model.persistence.MarketAreaEntity} in the system.
     *
     * @param marketAreaId Unique identifier of MarketArea in the system, which needs to be deleted.
     * @return Response of type {@link ResponseEntity} that holds the unique identifier of the
     *     {@link com.harik.inno.features.platform.data.model.persistence.MarketAreaEntity} that was
     *     deleted from the system.
     */
    @Operation(
            method = "deleteMarketArea",
            summary = "Delete an existing MarketArea.",
            description = "This API is used to delete an existing MarketArea in the system.",
            tags = {MarketAreaApi.API_TAG},
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
                        description = "Successfully deleted an existing MarketArea in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @DeleteMapping(value = "/marketAreas/{marketAreaId}")
    public ResponseEntity<Integer> deleteMarketArea(
            @PathVariable(name = "marketAreaId") final Integer marketAreaId) {
        // Delegate to the service layer.
        final Integer deletedInstance = marketAreaService.deleteMarketArea(marketAreaId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(deletedInstance);
    }
}

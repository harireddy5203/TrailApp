/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.web.api;

import com.harik.inno.commons.data.utils.PageUtils;
import com.harik.inno.commons.web.api.AbstractApi;
import com.harik.inno.commons.web.configuration.properties.ApiDocumentationSettings;
import com.harik.inno.features.platform.data.model.experience.salesperson.CreateSalesPersonRequest;
import com.harik.inno.features.platform.data.model.experience.salesperson.SalesPerson;
import com.harik.inno.features.platform.data.model.experience.salesperson.UpdateSalesPersonRequest;
import com.harik.inno.features.platform.web.service.SalesPersonService;
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
 * com.harik.inno.features.platform.data.model.persistence.SalesPersonEntity}.
 *
 * @author Admin
 */
@Slf4j
@RestController
@RequestMapping(SalesPersonApi.rootEndPoint)
public class SalesPersonApi extends AbstractApi {
    /** Tag for this API. */
    public static final String API_TAG = "SalesPersons";

    /** Root end point. */
    public static final String rootEndPoint = "/ecom-sales";

    /** Service implementation of type {@link SalesPersonService}. */
    private final SalesPersonService salesPersonService;

    /**
     * Constructor.
     *
     * @param salesPersonService Service instance of type {@link SalesPersonService}.
     */
    public SalesPersonApi(final SalesPersonService salesPersonService) {
        this.salesPersonService = salesPersonService;
    }

    /**
     * This API provides the capability to add a new instance of type {@link
     * com.harik.inno.features.platform.data.model.persistence.SalesPersonEntity} into the system.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     com.harik.inno.features.platform.data.model.persistence.SalesPersonEntity}.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     SalesPerson}.
     */
    @Operation(
            method = "createSalesPerson",
            summary = "Create a new SalesPerson.",
            description = "This API is used to create a new SalesPerson in the system.",
            tags = {SalesPersonApi.API_TAG},
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
                        description = "Successfully created a new SalesPerson in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PostMapping(value = "/salesPersons")
    public ResponseEntity<SalesPerson> createSalesPerson(
            @Valid @RequestBody final CreateSalesPersonRequest payload) {
        // Delegate to the service layer.
        final SalesPerson newInstance = salesPersonService.createSalesPerson(payload);

        // Build a response entity object and return it.
        return ResponseEntity.status(HttpStatus.CREATED).body(newInstance);
    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.harik.inno.features.platform.data.model.persistence.SalesPersonEntity} in the system.
     *
     * @param salesPersonId Unique identifier of SalesPerson in the system, which needs to be
     *     updated.
     * @param payload Request payload containing the details of an existing SalesPerson, which needs
     *     to be updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     SalesPerson}.
     */
    @Operation(
            method = "updateSalesPerson",
            summary = "Update an existing SalesPerson.",
            description = "This API is used to update an existing SalesPerson in the system.",
            tags = {SalesPersonApi.API_TAG},
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
                        description = "Successfully updated an existing SalesPerson in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PutMapping(value = "/salesPersons/{salesPersonId}")
    public ResponseEntity<SalesPerson> updateSalesPerson(
            @PathVariable(name = "salesPersonId") final String salesPersonId,
            @Valid @RequestBody final UpdateSalesPersonRequest payload) {
        // Delegate to the service layer.
        final SalesPerson updatedInstance =
                salesPersonService.updateSalesPerson(salesPersonId, payload);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to retrieve the details of an existing {@link
     * com.harik.inno.features.platform.data.model.persistence.SalesPersonEntity} in the system.
     *
     * @param salesPersonId Unique identifier of SalesPerson in the system, whose details have to be
     *     retrieved.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     SalesPerson}.
     */
    @Operation(
            method = "findSalesPerson",
            summary = "Find an existing SalesPerson.",
            description = "This API is used to find an existing SalesPerson in the system.",
            tags = {SalesPersonApi.API_TAG},
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
                                "Successfully retrieved the details of an existing SalesPerson in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/salesPersons/{salesPersonId}")
    public ResponseEntity<SalesPerson> findSalesPerson(
            @PathVariable(name = "salesPersonId") final String salesPersonId) {
        // Delegate to the service layer.
        final SalesPerson matchingInstance = salesPersonService.findSalesPerson(salesPersonId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstance);
    }

    /**
     * This API provides the capability to retrieve all instances of type {@link
     * com.harik.inno.features.platform.data.model.persistence.SalesPersonEntity} in the system in a
     * paginated manner.
     *
     * @param page Page number.
     * @param size Page size.
     * @return Response of type {@link ResponseEntity} that holds a page of instances of type
     *     SalesPerson based on the provided pagination settings.
     */
    @Operation(
            method = "findAllSalesPersons",
            summary = "Find all SalesPersons.",
            description = "This API is used to find all SalesPersons in the system.",
            tags = {SalesPersonApi.API_TAG},
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
                                "Successfully retrieved the SalesPersons in the system based on the provided pagination settings.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/salesPersons")
    public ResponseEntity<Page<SalesPerson>> findAllSalesPersons(
            @RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "20")
                    final Integer size) {
        // Delegate to the service layer.
        final Pageable pageSettings = PageUtils.createPaginationConfiguration(page, size);
        final Page<SalesPerson> matchingInstances =
                salesPersonService.findAllSalesPersons(pageSettings);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstances);
    }

    /**
     * This API provides the capability to delete an existing instance of type {@link
     * com.harik.inno.features.platform.data.model.persistence.SalesPersonEntity} in the system.
     *
     * @param salesPersonId Unique identifier of SalesPerson in the system, which needs to be
     *     deleted.
     * @return Response of type {@link ResponseEntity} that holds the unique identifier of the
     *     {@link com.harik.inno.features.platform.data.model.persistence.SalesPersonEntity} that
     *     was deleted from the system.
     */
    @Operation(
            method = "deleteSalesPerson",
            summary = "Delete an existing SalesPerson.",
            description = "This API is used to delete an existing SalesPerson in the system.",
            tags = {SalesPersonApi.API_TAG},
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
                        description = "Successfully deleted an existing SalesPerson in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @DeleteMapping(value = "/salesPersons/{salesPersonId}")
    public ResponseEntity<String> deleteSalesPerson(
            @PathVariable(name = "salesPersonId") final String salesPersonId) {
        // Delegate to the service layer.
        final String deletedInstance = salesPersonService.deleteSalesPerson(salesPersonId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(deletedInstance);
    }
}

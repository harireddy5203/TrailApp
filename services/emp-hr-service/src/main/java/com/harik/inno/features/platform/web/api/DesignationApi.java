/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.web.api;

import com.harik.inno.commons.data.utils.PageUtils;
import com.harik.inno.commons.web.api.AbstractApi;
import com.harik.inno.commons.web.configuration.properties.ApiDocumentationSettings;
import com.harik.inno.features.platform.data.model.experience.designation.CreateDesignationRequest;
import com.harik.inno.features.platform.data.model.experience.designation.Designation;
import com.harik.inno.features.platform.data.model.experience.designation.UpdateDesignationRequest;
import com.harik.inno.features.platform.web.service.DesignationService;
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
 * com.harik.inno.features.platform.data.model.persistence.DesignationEntity}.
 *
 * @author Admin
 */
@Slf4j
@RestController
@RequestMapping(DesignationApi.rootEndPoint)
public class DesignationApi extends AbstractApi {
    /** Tag for this API. */
    public static final String API_TAG = "Designations";

    /** Root end point. */
    public static final String rootEndPoint = "/emp-hr";

    /** Service implementation of type {@link DesignationService}. */
    private final DesignationService designationService;

    /**
     * Constructor.
     *
     * @param designationService Service instance of type {@link DesignationService}.
     */
    public DesignationApi(final DesignationService designationService) {
        this.designationService = designationService;
    }

    /**
     * This API provides the capability to add a new instance of type {@link
     * com.harik.inno.features.platform.data.model.persistence.DesignationEntity} into the system.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     com.harik.inno.features.platform.data.model.persistence.DesignationEntity}.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     Designation}.
     */
    @Operation(
            method = "createDesignation",
            summary = "Create a new Designation.",
            description = "This API is used to create a new Designation in the system.",
            tags = {DesignationApi.API_TAG},
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
                        description = "Successfully created a new Designation in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PostMapping(value = "/designations")
    public ResponseEntity<Designation> createDesignation(
            @Valid @RequestBody final CreateDesignationRequest payload) {
        // Delegate to the service layer.
        final Designation newInstance = designationService.createDesignation(payload);

        // Build a response entity object and return it.
        return ResponseEntity.status(HttpStatus.CREATED).body(newInstance);
    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.harik.inno.features.platform.data.model.persistence.DesignationEntity} in the system.
     *
     * @param designationId Unique identifier of Designation in the system, which needs to be
     *     updated.
     * @param payload Request payload containing the details of an existing Designation, which needs
     *     to be updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     Designation}.
     */
    @Operation(
            method = "updateDesignation",
            summary = "Update an existing Designation.",
            description = "This API is used to update an existing Designation in the system.",
            tags = {DesignationApi.API_TAG},
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
                        description = "Successfully updated an existing Designation in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PutMapping(value = "/designations/{designationId}")
    public ResponseEntity<Designation> updateDesignation(
            @PathVariable(name = "designationId") final String designationId,
            @Valid @RequestBody final UpdateDesignationRequest payload) {
        // Delegate to the service layer.
        final Designation updatedInstance =
                designationService.updateDesignation(designationId, payload);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to retrieve the details of an existing {@link
     * com.harik.inno.features.platform.data.model.persistence.DesignationEntity} in the system.
     *
     * @param designationId Unique identifier of Designation in the system, whose details have to be
     *     retrieved.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     Designation}.
     */
    @Operation(
            method = "findDesignation",
            summary = "Find an existing Designation.",
            description = "This API is used to find an existing Designation in the system.",
            tags = {DesignationApi.API_TAG},
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
                                "Successfully retrieved the details of an existing Designation in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/designations/{designationId}")
    public ResponseEntity<Designation> findDesignation(
            @PathVariable(name = "designationId") final String designationId) {
        // Delegate to the service layer.
        final Designation matchingInstance = designationService.findDesignation(designationId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstance);
    }

    /**
     * This API provides the capability to retrieve all instances of type {@link
     * com.harik.inno.features.platform.data.model.persistence.DesignationEntity} in the system in a
     * paginated manner.
     *
     * @param page Page number.
     * @param size Page size.
     * @return Response of type {@link ResponseEntity} that holds a page of instances of type
     *     Designation based on the provided pagination settings.
     */
    @Operation(
            method = "findAllDesignations",
            summary = "Find all Designations.",
            description = "This API is used to find all Designations in the system.",
            tags = {DesignationApi.API_TAG},
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
                                "Successfully retrieved the Designations in the system based on the provided pagination settings.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/designations")
    public ResponseEntity<Page<Designation>> findAllDesignations(
            @RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "20")
                    final Integer size) {
        // Delegate to the service layer.
        final Pageable pageSettings = PageUtils.createPaginationConfiguration(page, size);
        final Page<Designation> matchingInstances =
                designationService.findAllDesignations(pageSettings);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstances);
    }

    /**
     * This API provides the capability to delete an existing instance of type {@link
     * com.harik.inno.features.platform.data.model.persistence.DesignationEntity} in the system.
     *
     * @param designationId Unique identifier of Designation in the system, which needs to be
     *     deleted.
     * @return Response of type {@link ResponseEntity} that holds the unique identifier of the
     *     {@link com.harik.inno.features.platform.data.model.persistence.DesignationEntity} that
     *     was deleted from the system.
     */
    @Operation(
            method = "deleteDesignation",
            summary = "Delete an existing Designation.",
            description = "This API is used to delete an existing Designation in the system.",
            tags = {DesignationApi.API_TAG},
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
                        description = "Successfully deleted an existing Designation in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @DeleteMapping(value = "/designations/{designationId}")
    public ResponseEntity<String> deleteDesignation(
            @PathVariable(name = "designationId") final String designationId) {
        // Delegate to the service layer.
        final String deletedInstance = designationService.deleteDesignation(designationId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(deletedInstance);
    }
}

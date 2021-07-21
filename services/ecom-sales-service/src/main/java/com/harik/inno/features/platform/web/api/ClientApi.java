/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.web.api;

import com.harik.inno.commons.data.utils.PageUtils;
import com.harik.inno.commons.web.api.AbstractApi;
import com.harik.inno.commons.web.configuration.properties.ApiDocumentationSettings;
import com.harik.inno.features.platform.data.model.experience.client.Client;
import com.harik.inno.features.platform.data.model.experience.client.CreateClientRequest;
import com.harik.inno.features.platform.data.model.experience.client.UpdateClientRequest;
import com.harik.inno.features.platform.web.service.ClientService;
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
 * com.harik.inno.features.platform.data.model.persistence.ClientEntity}.
 *
 * @author Admin
 */
@Slf4j
@RestController
@RequestMapping(ClientApi.rootEndPoint)
public class ClientApi extends AbstractApi {
    /** Tag for this API. */
    public static final String API_TAG = "Clients";

    /** Root end point. */
    public static final String rootEndPoint = "/ecom-sales";

    /** Service implementation of type {@link ClientService}. */
    private final ClientService clientService;

    /**
     * Constructor.
     *
     * @param clientService Service instance of type {@link ClientService}.
     */
    public ClientApi(final ClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * This API provides the capability to add a new instance of type {@link
     * com.harik.inno.features.platform.data.model.persistence.ClientEntity} into the system.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     com.harik.inno.features.platform.data.model.persistence.ClientEntity}.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     Client}.
     */
    @Operation(
            method = "createClient",
            summary = "Create a new Client.",
            description = "This API is used to create a new Client in the system.",
            tags = {ClientApi.API_TAG},
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
                        description = "Successfully created a new Client in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PostMapping(value = "/clients")
    public ResponseEntity<Client> createClient(
            @Valid @RequestBody final CreateClientRequest payload) {
        // Delegate to the service layer.
        final Client newInstance = clientService.createClient(payload);

        // Build a response entity object and return it.
        return ResponseEntity.status(HttpStatus.CREATED).body(newInstance);
    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.harik.inno.features.platform.data.model.persistence.ClientEntity} in the system.
     *
     * @param clientId Unique identifier of Client in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Client, which needs to
     *     be updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     Client}.
     */
    @Operation(
            method = "updateClient",
            summary = "Update an existing Client.",
            description = "This API is used to update an existing Client in the system.",
            tags = {ClientApi.API_TAG},
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
                        description = "Successfully updated an existing Client in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PutMapping(value = "/clients/{clientId}")
    public ResponseEntity<Client> updateClient(
            @PathVariable(name = "clientId") final Integer clientId,
            @Valid @RequestBody final UpdateClientRequest payload) {
        // Delegate to the service layer.
        final Client updatedInstance = clientService.updateClient(clientId, payload);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to retrieve the details of an existing {@link
     * com.harik.inno.features.platform.data.model.persistence.ClientEntity} in the system.
     *
     * @param clientId Unique identifier of Client in the system, whose details have to be
     *     retrieved.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     Client}.
     */
    @Operation(
            method = "findClient",
            summary = "Find an existing Client.",
            description = "This API is used to find an existing Client in the system.",
            tags = {ClientApi.API_TAG},
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
                                "Successfully retrieved the details of an existing Client in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/clients/{clientId}")
    public ResponseEntity<Client> findClient(
            @PathVariable(name = "clientId") final Integer clientId) {
        // Delegate to the service layer.
        final Client matchingInstance = clientService.findClient(clientId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstance);
    }

    /**
     * This API provides the capability to retrieve all instances of type {@link
     * com.harik.inno.features.platform.data.model.persistence.ClientEntity} in the system in a
     * paginated manner.
     *
     * @param page Page number.
     * @param size Page size.
     * @return Response of type {@link ResponseEntity} that holds a page of instances of type Client
     *     based on the provided pagination settings.
     */
    @Operation(
            method = "findAllClients",
            summary = "Find all Clients.",
            description = "This API is used to find all Clients in the system.",
            tags = {ClientApi.API_TAG},
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
                                "Successfully retrieved the Clients in the system based on the provided pagination settings.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/clients")
    public ResponseEntity<Page<Client>> findAllClients(
            @RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "20")
                    final Integer size) {
        // Delegate to the service layer.
        final Pageable pageSettings = PageUtils.createPaginationConfiguration(page, size);
        final Page<Client> matchingInstances = clientService.findAllClients(pageSettings);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstances);
    }

    /**
     * This API provides the capability to delete an existing instance of type {@link
     * com.harik.inno.features.platform.data.model.persistence.ClientEntity} in the system.
     *
     * @param clientId Unique identifier of Client in the system, which needs to be deleted.
     * @return Response of type {@link ResponseEntity} that holds the unique identifier of the
     *     {@link com.harik.inno.features.platform.data.model.persistence.ClientEntity} that was
     *     deleted from the system.
     */
    @Operation(
            method = "deleteClient",
            summary = "Delete an existing Client.",
            description = "This API is used to delete an existing Client in the system.",
            tags = {ClientApi.API_TAG},
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
                        description = "Successfully deleted an existing Client in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @DeleteMapping(value = "/clients/{clientId}")
    public ResponseEntity<Integer> deleteClient(
            @PathVariable(name = "clientId") final Integer clientId) {
        // Delegate to the service layer.
        final Integer deletedInstance = clientService.deleteClient(clientId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(deletedInstance);
    }
}

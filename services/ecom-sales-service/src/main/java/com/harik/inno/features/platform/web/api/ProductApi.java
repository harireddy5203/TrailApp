/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.web.api;

import com.harik.inno.commons.data.utils.PageUtils;
import com.harik.inno.commons.web.api.AbstractApi;
import com.harik.inno.commons.web.configuration.properties.ApiDocumentationSettings;
import com.harik.inno.features.platform.data.model.experience.product.CreateProductRequest;
import com.harik.inno.features.platform.data.model.experience.product.Product;
import com.harik.inno.features.platform.data.model.experience.product.UpdateProductRequest;
import com.harik.inno.features.platform.web.service.ProductService;
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
 * com.harik.inno.features.platform.data.model.persistence.ProductEntity}.
 *
 * @author Admin
 */
@Slf4j
@RestController
@RequestMapping(ProductApi.rootEndPoint)
public class ProductApi extends AbstractApi {
    /** Tag for this API. */
    public static final String API_TAG = "Products";

    /** Root end point. */
    public static final String rootEndPoint = "/ecom-sales";

    /** Service implementation of type {@link ProductService}. */
    private final ProductService productService;

    /**
     * Constructor.
     *
     * @param productService Service instance of type {@link ProductService}.
     */
    public ProductApi(final ProductService productService) {
        this.productService = productService;
    }

    /**
     * This API provides the capability to add a new instance of type {@link
     * com.harik.inno.features.platform.data.model.persistence.ProductEntity} into the system.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     com.harik.inno.features.platform.data.model.persistence.ProductEntity}.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     Product}.
     */
    @Operation(
            method = "createProduct",
            summary = "Create a new Product.",
            description = "This API is used to create a new Product in the system.",
            tags = {ProductApi.API_TAG},
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
                        description = "Successfully created a new Product in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PostMapping(value = "/products")
    public ResponseEntity<Product> createProduct(
            @Valid @RequestBody final CreateProductRequest payload) {
        // Delegate to the service layer.
        final Product newInstance = productService.createProduct(payload);

        // Build a response entity object and return it.
        return ResponseEntity.status(HttpStatus.CREATED).body(newInstance);
    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.harik.inno.features.platform.data.model.persistence.ProductEntity} in the system.
     *
     * @param productId Unique identifier of Product in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Product, which needs to
     *     be updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     Product}.
     */
    @Operation(
            method = "updateProduct",
            summary = "Update an existing Product.",
            description = "This API is used to update an existing Product in the system.",
            tags = {ProductApi.API_TAG},
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
                        description = "Successfully updated an existing Product in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PutMapping(value = "/products/{productId}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable(name = "productId") final Integer productId,
            @Valid @RequestBody final UpdateProductRequest payload) {
        // Delegate to the service layer.
        final Product updatedInstance = productService.updateProduct(productId, payload);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to retrieve the details of an existing {@link
     * com.harik.inno.features.platform.data.model.persistence.ProductEntity} in the system.
     *
     * @param productId Unique identifier of Product in the system, whose details have to be
     *     retrieved.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     Product}.
     */
    @Operation(
            method = "findProduct",
            summary = "Find an existing Product.",
            description = "This API is used to find an existing Product in the system.",
            tags = {ProductApi.API_TAG},
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
                                "Successfully retrieved the details of an existing Product in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/products/{productId}")
    public ResponseEntity<Product> findProduct(
            @PathVariable(name = "productId") final Integer productId) {
        // Delegate to the service layer.
        final Product matchingInstance = productService.findProduct(productId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstance);
    }

    /**
     * This API provides the capability to retrieve all instances of type {@link
     * com.harik.inno.features.platform.data.model.persistence.ProductEntity} in the system in a
     * paginated manner.
     *
     * @param page Page number.
     * @param size Page size.
     * @return Response of type {@link ResponseEntity} that holds a page of instances of type
     *     Product based on the provided pagination settings.
     */
    @Operation(
            method = "findAllProducts",
            summary = "Find all Products.",
            description = "This API is used to find all Products in the system.",
            tags = {ProductApi.API_TAG},
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
                                "Successfully retrieved the Products in the system based on the provided pagination settings.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/products")
    public ResponseEntity<Page<Product>> findAllProducts(
            @RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "20")
                    final Integer size) {
        // Delegate to the service layer.
        final Pageable pageSettings = PageUtils.createPaginationConfiguration(page, size);
        final Page<Product> matchingInstances = productService.findAllProducts(pageSettings);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstances);
    }

    /**
     * This API provides the capability to delete an existing instance of type {@link
     * com.harik.inno.features.platform.data.model.persistence.ProductEntity} in the system.
     *
     * @param productId Unique identifier of Product in the system, which needs to be deleted.
     * @return Response of type {@link ResponseEntity} that holds the unique identifier of the
     *     {@link com.harik.inno.features.platform.data.model.persistence.ProductEntity} that was
     *     deleted from the system.
     */
    @Operation(
            method = "deleteProduct",
            summary = "Delete an existing Product.",
            description = "This API is used to delete an existing Product in the system.",
            tags = {ProductApi.API_TAG},
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
                        description = "Successfully deleted an existing Product in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @DeleteMapping(value = "/products/{productId}")
    public ResponseEntity<Integer> deleteProduct(
            @PathVariable(name = "productId") final Integer productId) {
        // Delegate to the service layer.
        final Integer deletedInstance = productService.deleteProduct(productId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(deletedInstance);
    }
}

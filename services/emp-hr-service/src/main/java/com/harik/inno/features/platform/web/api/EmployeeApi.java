/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.web.api;

import com.harik.inno.commons.data.utils.PageUtils;
import com.harik.inno.commons.web.api.AbstractApi;
import com.harik.inno.commons.web.configuration.properties.ApiDocumentationSettings;
import com.harik.inno.features.platform.data.model.experience.employee.CreateEmployeeRequest;
import com.harik.inno.features.platform.data.model.experience.employee.Employee;
import com.harik.inno.features.platform.data.model.experience.employee.UpdateEmployeeRequest;
import com.harik.inno.features.platform.web.service.EmployeeService;
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
 * com.harik.inno.features.platform.data.model.persistence.EmployeeEntity}.
 *
 * @author Admin
 */
@Slf4j
@RestController
@RequestMapping(EmployeeApi.rootEndPoint)
public class EmployeeApi extends AbstractApi {
    /** Tag for this API. */
    public static final String API_TAG = "Employees";

    /** Root end point. */
    public static final String rootEndPoint = "/emp-hr";

    /** Service implementation of type {@link EmployeeService}. */
    private final EmployeeService employeeService;

    /**
     * Constructor.
     *
     * @param employeeService Service instance of type {@link EmployeeService}.
     */
    public EmployeeApi(final EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * This API provides the capability to add a new instance of type {@link
     * com.harik.inno.features.platform.data.model.persistence.EmployeeEntity} into the system.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     com.harik.inno.features.platform.data.model.persistence.EmployeeEntity}.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     Employee}.
     */
    @Operation(
            method = "createEmployee",
            summary = "Create a new Employee.",
            description = "This API is used to create a new Employee in the system.",
            tags = {EmployeeApi.API_TAG},
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
                        description = "Successfully created a new Employee in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PostMapping(value = "/employees")
    public ResponseEntity<Employee> createEmployee(
            @Valid @RequestBody final CreateEmployeeRequest payload) {
        // Delegate to the service layer.
        final Employee newInstance = employeeService.createEmployee(payload);

        // Build a response entity object and return it.
        return ResponseEntity.status(HttpStatus.CREATED).body(newInstance);
    }

    /**
     * This API provides the capability to update an existing instance of type {@link
     * com.harik.inno.features.platform.data.model.persistence.EmployeeEntity} in the system.
     *
     * @param employeeId Unique identifier of Employee in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Employee, which needs to
     *     be updated in the system.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     Employee}.
     */
    @Operation(
            method = "updateEmployee",
            summary = "Update an existing Employee.",
            description = "This API is used to update an existing Employee in the system.",
            tags = {EmployeeApi.API_TAG},
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
                        description = "Successfully updated an existing Employee in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @PutMapping(value = "/employees/{employeeId}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable(name = "employeeId") final Integer employeeId,
            @Valid @RequestBody final UpdateEmployeeRequest payload) {
        // Delegate to the service layer.
        final Employee updatedInstance = employeeService.updateEmployee(employeeId, payload);

        // Build a response entity object and return it.
        return ResponseEntity.ok(updatedInstance);
    }

    /**
     * This API provides the capability to retrieve the details of an existing {@link
     * com.harik.inno.features.platform.data.model.persistence.EmployeeEntity} in the system.
     *
     * @param employeeId Unique identifier of Employee in the system, whose details have to be
     *     retrieved.
     * @return Response of type {@link ResponseEntity} that wraps an instance of type {@link
     *     Employee}.
     */
    @Operation(
            method = "findEmployee",
            summary = "Find an existing Employee.",
            description = "This API is used to find an existing Employee in the system.",
            tags = {EmployeeApi.API_TAG},
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
                                "Successfully retrieved the details of an existing Employee in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/employees/{employeeId}")
    public ResponseEntity<Employee> findEmployee(
            @PathVariable(name = "employeeId") final Integer employeeId) {
        // Delegate to the service layer.
        final Employee matchingInstance = employeeService.findEmployee(employeeId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstance);
    }

    /**
     * This API provides the capability to retrieve all instances of type {@link
     * com.harik.inno.features.platform.data.model.persistence.EmployeeEntity} in the system in a
     * paginated manner.
     *
     * @param page Page number.
     * @param size Page size.
     * @return Response of type {@link ResponseEntity} that holds a page of instances of type
     *     Employee based on the provided pagination settings.
     */
    @Operation(
            method = "findAllEmployees",
            summary = "Find all Employees.",
            description = "This API is used to find all Employees in the system.",
            tags = {EmployeeApi.API_TAG},
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
                                "Successfully retrieved the Employees in the system based on the provided pagination settings.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping(value = "/employees")
    public ResponseEntity<Page<Employee>> findAllEmployees(
            @RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "20")
                    final Integer size) {
        // Delegate to the service layer.
        final Pageable pageSettings = PageUtils.createPaginationConfiguration(page, size);
        final Page<Employee> matchingInstances = employeeService.findAllEmployees(pageSettings);

        // Build a response entity object and return it.
        return ResponseEntity.ok(matchingInstances);
    }

    /**
     * This API provides the capability to delete an existing instance of type {@link
     * com.harik.inno.features.platform.data.model.persistence.EmployeeEntity} in the system.
     *
     * @param employeeId Unique identifier of Employee in the system, which needs to be deleted.
     * @return Response of type {@link ResponseEntity} that holds the unique identifier of the
     *     {@link com.harik.inno.features.platform.data.model.persistence.EmployeeEntity} that was
     *     deleted from the system.
     */
    @Operation(
            method = "deleteEmployee",
            summary = "Delete an existing Employee.",
            description = "This API is used to delete an existing Employee in the system.",
            tags = {EmployeeApi.API_TAG},
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
                        description = "Successfully deleted an existing Employee in the system.",
                        content = @Content),
                @ApiResponse(
                        responseCode = "403",
                        description = "You do not have permissions to perform this operation.",
                        content = @Content)
            })
    @PreAuthorize(value = "isAuthenticated()")
    @DeleteMapping(value = "/employees/{employeeId}")
    public ResponseEntity<Integer> deleteEmployee(
            @PathVariable(name = "employeeId") final Integer employeeId) {
        // Delegate to the service layer.
        final Integer deletedInstance = employeeService.deleteEmployee(employeeId);

        // Build a response entity object and return it.
        return ResponseEntity.ok(deletedInstance);
    }
}

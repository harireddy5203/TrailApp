/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.web.service;

import com.harik.inno.commons.data.utils.PageUtils;
import com.harik.inno.commons.instrumentation.Instrument;
import com.harik.inno.features.platform.data.mapper.EmployeeMapper;
import com.harik.inno.features.platform.data.model.experience.employee.CreateEmployeeRequest;
import com.harik.inno.features.platform.data.model.experience.employee.Employee;
import com.harik.inno.features.platform.data.model.experience.employee.UpdateEmployeeRequest;
import com.harik.inno.features.platform.data.model.persistence.DesignationEntity;
import com.harik.inno.features.platform.data.model.persistence.EmployeeEntity;
import com.harik.inno.features.platform.data.repository.DesignationRepository;
import com.harik.inno.features.platform.data.repository.EmployeeRepository;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * Service implementation that provides CRUD (Create, Read, Update, Delete) capabilities for
 * entities of type {@link EmployeeEntity}.
 *
 * @author Admin
 */
@Slf4j
@Validated
@Service
public class EmployeeService {

    /** Repository implementation of type {@link EmployeeRepository}. */
    private final EmployeeRepository employeeRepository;

    /**
     * Mapper implementation of type {@link EmployeeMapper} to transform between different types.
     */
    private final EmployeeMapper employeeMapper;

    /** Repository implementation of type {@link DesignationRepository}. */
    private final DesignationRepository designationRepository;

    /**
     * Constructor.
     *
     * @param employeeRepository Repository instance of type {@link EmployeeRepository}.
     * @param employeeMapper Mapper instance of type {@link EmployeeMapper}.
     * @param designationRepository Repository instance of type {@link DesignationRepository}.
     */
    public EmployeeService(
            final EmployeeRepository employeeRepository,
            final EmployeeMapper employeeMapper,
            final DesignationRepository designationRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.designationRepository = designationRepository;
    }

    /**
     * This method attempts to create an instance of type {@link EmployeeEntity} in the system based
     * on the provided payload.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     EmployeeEntity}.
     * @return An experience model of type {@link Employee} that represents the newly created entity
     *     of type {@link EmployeeEntity}.
     */
    @Instrument
    @Transactional
    public Employee createEmployee(@Valid final CreateEmployeeRequest payload) {
        // 1. Transform the experience model to a persistence model.
        final EmployeeEntity employeeEntity = employeeMapper.transform(payload);

        // 2. Save the entity.
        EmployeeService.LOGGER.debug("Saving a new instance of type - EmployeeEntity");
        final EmployeeEntity newInstance = employeeRepository.save(employeeEntity);

        // 3. Transform the created entity to an experience model and return it.
        return employeeMapper.transform(newInstance);
    }

    /**
     * This method attempts to update an existing instance of type {@link EmployeeEntity} using the
     * details from the provided input, which is an instance of type {@link UpdateEmployeeRequest}.
     *
     * @param employeeId Unique identifier of Employee in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Employee, which needs to
     *     be updated in the system.
     * @return A instance of type {@link Employee} containing the updated details.
     */
    @Instrument
    @Transactional
    public Employee updateEmployee(
            final Integer employeeId, @Valid final UpdateEmployeeRequest payload) {
        // 1. Verify that the entity being updated truly exists.
        final EmployeeEntity matchingInstance = employeeRepository.findByIdOrThrow(employeeId);

        // 2. Transform the experience model to a persistence model and delegate to the save(..)
        // method.
        employeeMapper.transform(payload, matchingInstance);

        // 3. Save the entity
        EmployeeService.LOGGER.debug("Saving the updated entity - EmployeeEntity");
        final EmployeeEntity updatedInstance = employeeRepository.save(matchingInstance);

        // 4. Transform updated entity to output object
        return employeeMapper.transform(updatedInstance);
    }

    /**
     * This method attempts to find a {@link EmployeeEntity} whose unique identifier matches the
     * provided identifier.
     *
     * @param employeeId Unique identifier of Employee in the system, whose details have to be
     *     retrieved.
     * @return Matching entity of type {@link Employee} if found, else returns null.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Employee findEmployee(final Integer employeeId) {
        // 1. Find a matching entity and throw an exception if not found.
        final EmployeeEntity matchingInstance = employeeRepository.findByIdOrThrow(employeeId);

        // 2. Transform the matching entity to the desired output.
        return employeeMapper.transform(matchingInstance);
    }

    /**
     * This method attempts to find instances of type EmployeeEntity based on the provided page
     * definition. If the page definition is null or contains invalid values, this method attempts
     * to return the data for the first page (i.e. page index is 0) with a default page size as 20.
     *
     * @return Returns a page of objects based on the provided page definition. Each object in the
     *     returned page is an instance of type {@link Employee}.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Page<Employee> findAllEmployees(final Pageable page) {
        // 1. Validate the provided pagination settings.
        final Pageable pageSettings = PageUtils.validateAndUpdatePaginationConfiguration(page);
        EmployeeService.LOGGER.debug(
                "Page settings: page number {}, page size {}",
                pageSettings.getPageNumber(),
                pageSettings.getPageSize());

        // 2. Delegate to the super class method to find the data (page settings are verified in
        // that method).
        final Page<EmployeeEntity> pageData = employeeRepository.findAll(pageSettings);

        // 3. If the page has data, transform each element into target type.
        if (pageData.hasContent()) {
            final List<Employee> dataToReturn =
                    pageData.getContent().stream()
                            .map(employeeMapper::transform)
                            .collect(Collectors.toList());

            return PageUtils.createPage(dataToReturn, pageSettings, pageData.getTotalElements());
        }

        // Return empty page.
        return PageUtils.emptyPage(pageSettings);
    }

    /**
     * This method attempts to delete an existing instance of type {@link EmployeeEntity} whose
     * unique identifier matches the provided identifier.
     *
     * @param employeeId Unique identifier of Employee in the system, which needs to be deleted.
     * @return Unique identifier of the instance of type EmployeeEntity that was deleted.
     */
    @Instrument
    @Transactional
    public Integer deleteEmployee(final Integer employeeId) {
        // 1. Delegate to our repository method to handle the deletion.
        return employeeRepository.deleteOne(employeeId);
    }

    /**
     * This method attempts to update an existing instance of type {@link EmployeeEntity} by adding
     * designations.
     *
     * @param employeeId Unique identifier of Employee in the system, whose details have to be
     *     retrieved.
     * @param ids Collection of unique identifiers pertaining to designation.
     * @return An instance of type {@link Employee} containing the updated details.
     */
    @Instrument
    @Transactional
    public Employee addDesignationsToEmployee(
            final Integer employeeId, final Collection<String> ids) {

        // 1. Find a matching entity and throw an exception if not found.
        final EmployeeEntity matchingEmployee = employeeRepository.findByIdOrThrow(employeeId);

        // 2. Find all entities based on the given id's.
        final Iterable<DesignationEntity> matchingDesignations =
                designationRepository.findAllOrThrow(ids);

        // 3. Set the other models to matching instance.
        matchingEmployee.setDesignations(
                StreamSupport.stream(matchingDesignations.spliterator(), false)
                        .collect(Collectors.toSet()));

        // 4. Persist the updated instance.
        final EmployeeEntity updatedEmployee = employeeRepository.save(matchingEmployee);

        // 5. Transform updated entity to output object.
        return employeeMapper.transform(updatedEmployee);
    }

    /**
     * This method attempts to delete some of other instances from an existing instance of type
     * {@link EmployeeEntity} using the details from the provided input.
     *
     * @param employeeId Unique identifier of Employee in the system, whose details have to be
     *     retrieved.
     * @param ids unique identifiers pertaining to designation.
     * @return Instance of type {@link Employee} containing the updated details.
     */
    @Instrument
    @Transactional
    public Employee deleteDesignationsFromEmployee(
            final Integer employeeId, final Collection<String> ids) {
        // 1. Find a matching entity and throw an exception if not found.
        final EmployeeEntity matchingEmployee = employeeRepository.findByIdOrThrow(employeeId);

        // 2. Find all entities based on the given id's.
        final Iterable<DesignationEntity> matchingDesignations =
                designationRepository.findAllOrThrow(ids);

        // 3. Convert iterable to collection of entities.
        final Collection<DesignationEntity> designationsToDelete =
                StreamSupport.stream(matchingDesignations.spliterator(), false)
                        .collect(Collectors.toSet());

        // 4. Delete the requested entities from the matchingInstance.
        matchingEmployee.setDesignations(
                matchingEmployee.getDesignations().stream()
                        .filter(designationsToDelete::contains)
                        .collect(Collectors.toSet()));

        // 5. Save the updated roles to user.
        final EmployeeEntity updatedEmployee = employeeRepository.save(matchingEmployee);

        // 6. Transform updated entity to output object.
        return employeeMapper.transform(updatedEmployee);
    }
}

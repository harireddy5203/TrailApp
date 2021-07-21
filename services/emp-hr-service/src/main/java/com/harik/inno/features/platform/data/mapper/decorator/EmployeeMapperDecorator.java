/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.data.mapper.decorator;

import com.harik.inno.features.platform.data.mapper.DesignationMapper;
import com.harik.inno.features.platform.data.mapper.EmployeeMapper;
import com.harik.inno.features.platform.data.model.experience.employee.CreateEmployeeRequest;
import com.harik.inno.features.platform.data.model.experience.employee.Employee;
import com.harik.inno.features.platform.data.model.experience.employee.UpdateEmployeeRequest;
import com.harik.inno.features.platform.data.model.persistence.EmployeeEntity;
import com.harik.inno.features.platform.data.repository.DesignationRepository;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;

/**
 * Decorator implementation that maps / transforms data from an instance of type {@link EmployeeEntity to {@link Employee and vice-versa.
 *
 * @author Admin
 */
@Slf4j
public abstract class EmployeeMapperDecorator implements EmployeeMapper {

    @Autowired private PasswordEncoder passwordEncoder;

    /** Repository implementation of type {@link DesignationRepository}. */
    @Autowired private DesignationRepository designationRepository;

    /** Mapper implementation of type {@link DesignationMapper}. */
    @Autowired private DesignationMapper designationMapper;

    /** Mapper implementation of type {@link EmployeeMapper}. */
    @Autowired private EmployeeMapper employeeMapper;

    @Override
    public EmployeeEntity transform(final CreateEmployeeRequest source) {
        // 1. Transform the CreateEmployeeRequest to EmployeeEntity object.
        final EmployeeEntity employee = employeeMapper.transform(source);
        if (Objects.nonNull(source.getPassword()) && !source.getPassword().isEmpty()) {
            source.setPassword(passwordEncoder.encode(source.getPassword()));
        }

        if (!CollectionUtils.isEmpty(source.getDesignations())) {
            employee.setDesignations(designationRepository.findAllById(source.getDesignations()));
        }
        // Return the transformed object.
        return employee;
    }

    @Override
    public Employee transform(final EmployeeEntity source) {
        // 1. Transform the EmployeeEntity to Employee object.
        final Employee employee = employeeMapper.transform(source);
        if (Objects.nonNull(source.getPassword()) && !source.getPassword().isEmpty()) {
            source.setPassword(passwordEncoder.encode(source.getPassword()));
        }

        if (!CollectionUtils.isEmpty(source.getDesignations())) {
            employee.setDesignations(
                    source.getDesignations().stream()
                            .map(designation -> designationMapper.transform(designation))
                            .collect(Collectors.toList()));
        }
        // Return the transformed object.
        return employee;
    }

    @Override
    public void transform(
            final UpdateEmployeeRequest source, final @MappingTarget EmployeeEntity target) {

        // Transform from source to the target.
        employeeMapper.transform(source, target);

        if (!CollectionUtils.isEmpty(source.getDesignations())) {
            target.setDesignations(designationRepository.findAllById(source.getDesignations()));
        }
    }

    @Override
    public EmployeeEntity transform(final UpdateEmployeeRequest source) {

        // Transform from source to the target.
        final EmployeeEntity employee = employeeMapper.transform(source);

        if (!CollectionUtils.isEmpty(source.getDesignations())) {
            employee.setDesignations(designationRepository.findAllById(source.getDesignations()));
        }

        // Return the response.
        return employee;
    }
}

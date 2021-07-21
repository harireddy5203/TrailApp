/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.data.mapper;

import com.harik.inno.features.platform.data.mapper.decorator.EmployeeMapperDecorator;
import com.harik.inno.features.platform.data.model.experience.employee.CreateEmployeeRequest;
import com.harik.inno.features.platform.data.model.experience.employee.Employee;
import com.harik.inno.features.platform.data.model.experience.employee.UpdateEmployeeRequest;
import com.harik.inno.features.platform.data.model.persistence.EmployeeEntity;
import java.util.Collection;
import java.util.stream.Collectors;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper contract that maps / transforms data from an instance of type {@link EmployeeEntity to {@link Employee and vice-versa.
 *
 * @author Admin
 */
@DecoratedWith(value = EmployeeMapperDecorator.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface EmployeeMapper {

    /**
     * This method transforms an instance of type {@link CreateEmployeeRequest} to an instance of
     * type {@link EmployeeEntity}.
     *
     * @param source Instance of type {@link CreateEmployeeRequest} that needs to be transformed to
     *     {@link EmployeeEntity}.
     * @return Instance of type {@link EmployeeEntity}.
     */
    @Mapping(source = "designations", target = "designations", ignore = true)
    EmployeeEntity transform(CreateEmployeeRequest source);

    /**
     * This method transforms an instance of type {@link EmployeeEntity} to an instance of type
     * {@link Employee}.
     *
     * @param source Instance of type {@link EmployeeEntity} that needs to be transformed to {@link
     *     Employee}.
     * @return Instance of type {@link Employee}.
     */
    @Mapping(source = "designations", target = "designations", ignore = true)
    Employee transform(EmployeeEntity source);

    /**
     * This method converts / transforms the provided collection of {@link EmployeeEntity} instances
     * to a collection of instances of type {@link Employee}.
     *
     * @param source Instance of type {@link EmployeeEntity} that needs to be transformed to {@link
     *     Employee}.
     * @return Collection of instance of type {@link Employee}.
     */
    default Collection<Employee> transformListTo(Collection<EmployeeEntity> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
    /**
     * This method transforms an instance of type {@link UpdateEmployeeRequest} to an instance of
     * type {@link EmployeeEntity}.
     *
     * <p>The provided instance ({@code target}) will be updated instead of creating a new instance.
     *
     * @param source Instance of type {@link UpdateEmployeeRequest} that needs to be transformed to
     *     {@link EmployeeEntity}.
     * @param target Instance of type {@link EmployeeEntity} that will be updated instead of
     *     creating and returning a new instance.
     */
    @Mapping(source = "designations", target = "designations", ignore = true)
    void transform(UpdateEmployeeRequest source, @MappingTarget EmployeeEntity target);

    /**
     * This method transforms an instance of type {@link UpdateEmployeeRequest} to an instance of
     * type {@link EmployeeEntity}.
     *
     * @param source Instance of type {@link UpdateEmployeeRequest} that needs to be transformed to
     *     {@link EmployeeEntity}.
     * @return Instance of type {@link EmployeeEntity}.
     */
    @Mapping(source = "designations", target = "designations", ignore = true)
    EmployeeEntity transform(UpdateEmployeeRequest source);

    /**
     * This method converts / transforms the provided collection of {@link UpdateEmployeeRequest}
     * instances to a collection of instances of type {@link EmployeeEntity}.
     *
     * @param source Instance of type {@link UpdateEmployeeRequest} that needs to be transformed to
     *     {@link EmployeeEntity}.
     * @return Instance of type {@link EmployeeEntity}.
     */
    default Collection<EmployeeEntity> transformList(Collection<UpdateEmployeeRequest> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
}

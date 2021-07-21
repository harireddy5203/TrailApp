/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.data.mapper;

import com.harik.inno.features.platform.data.model.experience.designation.CreateDesignationRequest;
import com.harik.inno.features.platform.data.model.experience.designation.Designation;
import com.harik.inno.features.platform.data.model.experience.designation.UpdateDesignationRequest;
import com.harik.inno.features.platform.data.model.persistence.DesignationEntity;
import java.util.Collection;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper contract that maps / transforms data from an instance of type {@link DesignationEntity to {@link Designation and vice-versa.
 *
 * @author Admin
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface DesignationMapper {

    /**
     * This method transforms an instance of type {@link CreateDesignationRequest} to an instance of
     * type {@link DesignationEntity}.
     *
     * @param source Instance of type {@link CreateDesignationRequest} that needs to be transformed
     *     to {@link DesignationEntity}.
     * @return Instance of type {@link DesignationEntity}.
     */
    DesignationEntity transform(CreateDesignationRequest source);

    /**
     * This method transforms an instance of type {@link DesignationEntity} to an instance of type
     * {@link Designation}.
     *
     * @param source Instance of type {@link DesignationEntity} that needs to be transformed to
     *     {@link Designation}.
     * @return Instance of type {@link Designation}.
     */
    Designation transform(DesignationEntity source);

    /**
     * This method converts / transforms the provided collection of {@link DesignationEntity}
     * instances to a collection of instances of type {@link Designation}.
     *
     * @param source Instance of type {@link DesignationEntity} that needs to be transformed to
     *     {@link Designation}.
     * @return Collection of instance of type {@link Designation}.
     */
    default Collection<Designation> transformListTo(Collection<DesignationEntity> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
    /**
     * This method transforms an instance of type {@link UpdateDesignationRequest} to an instance of
     * type {@link DesignationEntity}.
     *
     * <p>The provided instance ({@code target}) will be updated instead of creating a new instance.
     *
     * @param source Instance of type {@link UpdateDesignationRequest} that needs to be transformed
     *     to {@link DesignationEntity}.
     * @param target Instance of type {@link DesignationEntity} that will be updated instead of
     *     creating and returning a new instance.
     */
    void transform(UpdateDesignationRequest source, @MappingTarget DesignationEntity target);

    /**
     * This method transforms an instance of type {@link UpdateDesignationRequest} to an instance of
     * type {@link DesignationEntity}.
     *
     * @param source Instance of type {@link UpdateDesignationRequest} that needs to be transformed
     *     to {@link DesignationEntity}.
     * @return Instance of type {@link DesignationEntity}.
     */
    DesignationEntity transform(UpdateDesignationRequest source);

    /**
     * This method converts / transforms the provided collection of {@link UpdateDesignationRequest}
     * instances to a collection of instances of type {@link DesignationEntity}.
     *
     * @param source Instance of type {@link UpdateDesignationRequest} that needs to be transformed
     *     to {@link DesignationEntity}.
     * @return Instance of type {@link DesignationEntity}.
     */
    default Collection<DesignationEntity> transformList(
            Collection<UpdateDesignationRequest> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
}

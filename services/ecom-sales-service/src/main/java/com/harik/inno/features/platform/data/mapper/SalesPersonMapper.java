/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.data.mapper;

import com.harik.inno.features.platform.data.mapper.decorator.SalesPersonMapperDecorator;
import com.harik.inno.features.platform.data.model.experience.salesperson.CreateSalesPersonRequest;
import com.harik.inno.features.platform.data.model.experience.salesperson.SalesPerson;
import com.harik.inno.features.platform.data.model.experience.salesperson.UpdateSalesPersonRequest;
import com.harik.inno.features.platform.data.model.persistence.SalesPersonEntity;
import java.util.Collection;
import java.util.stream.Collectors;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper contract that maps / transforms data from an instance of type {@link SalesPersonEntity to {@link SalesPerson and vice-versa.
 *
 * @author Admin
 */
@DecoratedWith(value = SalesPersonMapperDecorator.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface SalesPersonMapper {

    /**
     * This method transforms an instance of type {@link CreateSalesPersonRequest} to an instance of
     * type {@link SalesPersonEntity}.
     *
     * @param source Instance of type {@link CreateSalesPersonRequest} that needs to be transformed
     *     to {@link SalesPersonEntity}.
     * @return Instance of type {@link SalesPersonEntity}.
     */
    @Mapping(source = "clients", target = "clients", ignore = true)
    @Mapping(source = "products", target = "products", ignore = true)
    SalesPersonEntity transform(CreateSalesPersonRequest source);

    /**
     * This method transforms an instance of type {@link SalesPersonEntity} to an instance of type
     * {@link SalesPerson}.
     *
     * @param source Instance of type {@link SalesPersonEntity} that needs to be transformed to
     *     {@link SalesPerson}.
     * @return Instance of type {@link SalesPerson}.
     */
    @Mapping(source = "clients", target = "clients", ignore = true)
    @Mapping(source = "products", target = "products", ignore = true)
    SalesPerson transform(SalesPersonEntity source);

    /**
     * This method converts / transforms the provided collection of {@link SalesPersonEntity}
     * instances to a collection of instances of type {@link SalesPerson}.
     *
     * @param source Instance of type {@link SalesPersonEntity} that needs to be transformed to
     *     {@link SalesPerson}.
     * @return Collection of instance of type {@link SalesPerson}.
     */
    default Collection<SalesPerson> transformListTo(Collection<SalesPersonEntity> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
    /**
     * This method transforms an instance of type {@link UpdateSalesPersonRequest} to an instance of
     * type {@link SalesPersonEntity}.
     *
     * <p>The provided instance ({@code target}) will be updated instead of creating a new instance.
     *
     * @param source Instance of type {@link UpdateSalesPersonRequest} that needs to be transformed
     *     to {@link SalesPersonEntity}.
     * @param target Instance of type {@link SalesPersonEntity} that will be updated instead of
     *     creating and returning a new instance.
     */
    @Mapping(source = "clients", target = "clients", ignore = true)
    @Mapping(source = "products", target = "products", ignore = true)
    void transform(UpdateSalesPersonRequest source, @MappingTarget SalesPersonEntity target);

    /**
     * This method transforms an instance of type {@link UpdateSalesPersonRequest} to an instance of
     * type {@link SalesPersonEntity}.
     *
     * @param source Instance of type {@link UpdateSalesPersonRequest} that needs to be transformed
     *     to {@link SalesPersonEntity}.
     * @return Instance of type {@link SalesPersonEntity}.
     */
    @Mapping(source = "clients", target = "clients", ignore = true)
    @Mapping(source = "products", target = "products", ignore = true)
    SalesPersonEntity transform(UpdateSalesPersonRequest source);

    /**
     * This method converts / transforms the provided collection of {@link UpdateSalesPersonRequest}
     * instances to a collection of instances of type {@link SalesPersonEntity}.
     *
     * @param source Instance of type {@link UpdateSalesPersonRequest} that needs to be transformed
     *     to {@link SalesPersonEntity}.
     * @return Instance of type {@link SalesPersonEntity}.
     */
    default Collection<SalesPersonEntity> transformList(
            Collection<UpdateSalesPersonRequest> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
}

/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.data.mapper.decorator;

import com.harik.inno.features.platform.data.mapper.ClientMapper;
import com.harik.inno.features.platform.data.mapper.ProductMapper;
import com.harik.inno.features.platform.data.mapper.SalesPersonMapper;
import com.harik.inno.features.platform.data.model.experience.salesperson.CreateSalesPersonRequest;
import com.harik.inno.features.platform.data.model.experience.salesperson.SalesPerson;
import com.harik.inno.features.platform.data.model.experience.salesperson.UpdateSalesPersonRequest;
import com.harik.inno.features.platform.data.model.persistence.SalesPersonEntity;
import com.harik.inno.features.platform.data.repository.ClientRepository;
import com.harik.inno.features.platform.data.repository.ProductRepository;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

/**
 * Decorator implementation that maps / transforms data from an instance of type {@link SalesPersonEntity to {@link SalesPerson and vice-versa.
 *
 * @author Admin
 */
@Slf4j
public abstract class SalesPersonMapperDecorator implements SalesPersonMapper {

    /** Repository implementation of type {@link ProductRepository}. */
    @Autowired private ProductRepository productRepository;

    /** Repository implementation of type {@link ClientRepository}. */
    @Autowired private ClientRepository clientRepository;

    /** Mapper implementation of type {@link ClientMapper}. */
    @Autowired private ClientMapper clientMapper;

    /** Mapper implementation of type {@link SalesPersonMapper}. */
    @Autowired private SalesPersonMapper salesPersonMapper;

    /** Mapper implementation of type {@link ProductMapper}. */
    @Autowired private ProductMapper productMapper;

    @Override
    public SalesPersonEntity transform(final CreateSalesPersonRequest source) {
        // 1. Transform the CreateSalesPersonRequest to SalesPersonEntity object.
        final SalesPersonEntity sales_person = salesPersonMapper.transform(source);

        sales_person.setClients(clientRepository.findAllById(source.getClients()));

        if (!CollectionUtils.isEmpty(source.getProducts())) {
            sales_person.setProducts(productRepository.findAllById(source.getProducts()));
        }
        // Return the transformed object.
        return sales_person;
    }

    @Override
    public SalesPerson transform(final SalesPersonEntity source) {
        // 1. Transform the SalesPersonEntity to SalesPerson object.
        final SalesPerson sales_person = salesPersonMapper.transform(source);

        sales_person.setClients(clientMapper.transformListTo(source.getClients()));

        if (!CollectionUtils.isEmpty(source.getProducts())) {
            sales_person.setProducts(
                    source.getProducts().stream()
                            .map(product -> productMapper.transform(product))
                            .collect(Collectors.toList()));
        }
        // Return the transformed object.
        return sales_person;
    }

    @Override
    public void transform(
            final UpdateSalesPersonRequest source, final @MappingTarget SalesPersonEntity target) {

        // Transform from source to the target.
        salesPersonMapper.transform(source, target);

        if (!CollectionUtils.isEmpty(source.getProducts())) {
            target.setProducts(productRepository.findAllById(source.getProducts()));
        }

        if (!CollectionUtils.isEmpty(source.getClients())) {
            target.setClients(clientRepository.findAllById(source.getClients()));
        }
    }

    @Override
    public SalesPersonEntity transform(final UpdateSalesPersonRequest source) {

        // Transform from source to the target.
        final SalesPersonEntity sales_person = salesPersonMapper.transform(source);

        if (!CollectionUtils.isEmpty(source.getProducts())) {
            sales_person.setProducts(productRepository.findAllById(source.getProducts()));
        }

        if (!CollectionUtils.isEmpty(source.getClients())) {
            sales_person.setClients(clientRepository.findAllById(source.getClients()));
        }
        // Return the response.
        return sales_person;
    }
}

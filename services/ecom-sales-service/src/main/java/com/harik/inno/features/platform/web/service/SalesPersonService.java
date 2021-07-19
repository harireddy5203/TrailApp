/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.web.service;

import com.harik.inno.commons.data.utils.PageUtils;
import com.harik.inno.commons.instrumentation.Instrument;
import com.harik.inno.features.platform.data.mapper.SalesPersonMapper;
import com.harik.inno.features.platform.data.model.experience.salesperson.CreateSalesPersonRequest;
import com.harik.inno.features.platform.data.model.experience.salesperson.SalesPerson;
import com.harik.inno.features.platform.data.model.experience.salesperson.UpdateSalesPersonRequest;
import com.harik.inno.features.platform.data.model.persistence.ProductEntity;
import com.harik.inno.features.platform.data.model.persistence.SalesPersonEntity;
import com.harik.inno.features.platform.data.repository.ProductRepository;
import com.harik.inno.features.platform.data.repository.SalesPersonRepository;
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
 * entities of type {@link SalesPersonEntity}.
 *
 * @author Admin
 */
@Slf4j
@Validated
@Service
public class SalesPersonService {

    /** Repository implementation of type {@link SalesPersonRepository}. */
    private final SalesPersonRepository salesPersonRepository;

    /**
     * Mapper implementation of type {@link SalesPersonMapper} to transform between different types.
     */
    private final SalesPersonMapper salesPersonMapper;

    /** Repository implementation of type {@link ProductRepository}. */
    private final ProductRepository productRepository;

    /**
     * Constructor.
     *
     * @param salesPersonRepository Repository instance of type {@link SalesPersonRepository}.
     * @param salesPersonMapper Mapper instance of type {@link SalesPersonMapper}.
     * @param productRepository Repository instance of type {@link ProductRepository}.
     */
    public SalesPersonService(
            final SalesPersonRepository salesPersonRepository,
            final SalesPersonMapper salesPersonMapper,
            final ProductRepository productRepository) {
        this.salesPersonRepository = salesPersonRepository;
        this.salesPersonMapper = salesPersonMapper;
        this.productRepository = productRepository;
    }

    /**
     * This method attempts to create an instance of type {@link SalesPersonEntity} in the system
     * based on the provided payload.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     SalesPersonEntity}.
     * @return An experience model of type {@link SalesPerson} that represents the newly created
     *     entity of type {@link SalesPersonEntity}.
     */
    @Instrument
    @Transactional
    public SalesPerson createSalesPerson(@Valid final CreateSalesPersonRequest payload) {
        // 1. Transform the experience model to a persistence model.
        final SalesPersonEntity salesPersonEntity = salesPersonMapper.transform(payload);

        // 2. Save the entity.
        SalesPersonService.LOGGER.debug("Saving a new instance of type - SalesPersonEntity");
        final SalesPersonEntity newInstance = salesPersonRepository.save(salesPersonEntity);

        // 3. Transform the created entity to an experience model and return it.
        return salesPersonMapper.transform(newInstance);
    }

    /**
     * This method attempts to update an existing instance of type {@link SalesPersonEntity} using
     * the details from the provided input, which is an instance of type {@link
     * UpdateSalesPersonRequest}.
     *
     * @param salesPersonId Unique identifier of SalesPerson in the system, which needs to be
     *     updated.
     * @param payload Request payload containing the details of an existing SalesPerson, which needs
     *     to be updated in the system.
     * @return A instance of type {@link SalesPerson} containing the updated details.
     */
    @Instrument
    @Transactional
    public SalesPerson updateSalesPerson(
            final String salesPersonId, @Valid final UpdateSalesPersonRequest payload) {
        // 1. Verify that the entity being updated truly exists.
        final SalesPersonEntity matchingInstance =
                salesPersonRepository.findByIdOrThrow(salesPersonId);

        // 2. Transform the experience model to a persistence model and delegate to the save(..)
        // method.
        salesPersonMapper.transform(payload, matchingInstance);

        // 3. Save the entity
        SalesPersonService.LOGGER.debug("Saving the updated entity - SalesPersonEntity");
        final SalesPersonEntity updatedInstance = salesPersonRepository.save(matchingInstance);

        // 4. Transform updated entity to output object
        return salesPersonMapper.transform(updatedInstance);
    }

    /**
     * This method attempts to find a {@link SalesPersonEntity} whose unique identifier matches the
     * provided identifier.
     *
     * @param salesPersonId Unique identifier of SalesPerson in the system, whose details have to be
     *     retrieved.
     * @return Matching entity of type {@link SalesPerson} if found, else returns null.
     */
    @Instrument
    @Transactional(readOnly = true)
    public SalesPerson findSalesPerson(final String salesPersonId) {
        // 1. Find a matching entity and throw an exception if not found.
        final SalesPersonEntity matchingInstance =
                salesPersonRepository.findByIdOrThrow(salesPersonId);

        // 2. Transform the matching entity to the desired output.
        return salesPersonMapper.transform(matchingInstance);
    }

    /**
     * This method attempts to find instances of type SalesPersonEntity based on the provided page
     * definition. If the page definition is null or contains invalid values, this method attempts
     * to return the data for the first page (i.e. page index is 0) with a default page size as 20.
     *
     * @return Returns a page of objects based on the provided page definition. Each object in the
     *     returned page is an instance of type {@link SalesPerson}.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Page<SalesPerson> findAllSalesPersons(final Pageable page) {
        // 1. Validate the provided pagination settings.
        final Pageable pageSettings = PageUtils.validateAndUpdatePaginationConfiguration(page);
        SalesPersonService.LOGGER.debug(
                "Page settings: page number {}, page size {}",
                pageSettings.getPageNumber(),
                pageSettings.getPageSize());

        // 2. Delegate to the super class method to find the data (page settings are verified in
        // that method).
        final Page<SalesPersonEntity> pageData = salesPersonRepository.findAll(pageSettings);

        // 3. If the page has data, transform each element into target type.
        if (pageData.hasContent()) {
            final List<SalesPerson> dataToReturn =
                    pageData.getContent().stream()
                            .map(salesPersonMapper::transform)
                            .collect(Collectors.toList());

            return PageUtils.createPage(dataToReturn, pageSettings, pageData.getTotalElements());
        }

        // Return empty page.
        return PageUtils.emptyPage(pageSettings);
    }

    /**
     * This method attempts to delete an existing instance of type {@link SalesPersonEntity} whose
     * unique identifier matches the provided identifier.
     *
     * @param salesPersonId Unique identifier of SalesPerson in the system, which needs to be
     *     deleted.
     * @return Unique identifier of the instance of type SalesPersonEntity that was deleted.
     */
    @Instrument
    @Transactional
    public String deleteSalesPerson(final String salesPersonId) {
        // 1. Delegate to our repository method to handle the deletion.
        return salesPersonRepository.deleteOne(salesPersonId);
    }

    /**
     * This method attempts to update an existing instance of type {@link SalesPersonEntity} by
     * adding products.
     *
     * @param salesPersonId Unique identifier of SalesPerson in the system, whose details have to be
     *     retrieved.
     * @param ids Collection of unique identifiers pertaining to product.
     * @return An instance of type {@link SalesPerson} containing the updated details.
     */
    @Instrument
    @Transactional
    public SalesPerson addProductsToSalesPerson(
            final String salesPersonId, final Collection<Integer> ids) {

        // 1. Find a matching entity and throw an exception if not found.
        final SalesPersonEntity matchingSalesPerson =
                salesPersonRepository.findByIdOrThrow(salesPersonId);

        // 2. Find all entities based on the given id's.
        final Iterable<ProductEntity> matchingProducts = productRepository.findAllOrThrow(ids);

        // 3. Set the other models to matching instance.
        matchingSalesPerson.setProducts(
                StreamSupport.stream(matchingProducts.spliterator(), false)
                        .collect(Collectors.toSet()));

        // 4. Persist the updated instance.
        final SalesPersonEntity updatedSalesPerson =
                salesPersonRepository.save(matchingSalesPerson);

        // 5. Transform updated entity to output object.
        return salesPersonMapper.transform(updatedSalesPerson);
    }

    /**
     * This method attempts to delete some of other instances from an existing instance of type
     * {@link SalesPersonEntity} using the details from the provided input.
     *
     * @param salesPersonId Unique identifier of SalesPerson in the system, whose details have to be
     *     retrieved.
     * @param ids unique identifiers pertaining to product.
     * @return Instance of type {@link SalesPerson} containing the updated details.
     */
    @Instrument
    @Transactional
    public SalesPerson deleteProductsFromSalesPerson(
            final String salesPersonId, final Collection<Integer> ids) {
        // 1. Find a matching entity and throw an exception if not found.
        final SalesPersonEntity matchingSalesPerson =
                salesPersonRepository.findByIdOrThrow(salesPersonId);

        // 2. Find all entities based on the given id's.
        final Iterable<ProductEntity> matchingProducts = productRepository.findAllOrThrow(ids);

        // 3. Convert iterable to collection of entities.
        final Collection<ProductEntity> productsToDelete =
                StreamSupport.stream(matchingProducts.spliterator(), false)
                        .collect(Collectors.toSet());

        // 4. Delete the requested entities from the matchingInstance.
        matchingSalesPerson.setProducts(
                matchingSalesPerson.getProducts().stream()
                        .filter(productsToDelete::contains)
                        .collect(Collectors.toSet()));

        // 5. Save the updated roles to user.
        final SalesPersonEntity updatedSalesPerson =
                salesPersonRepository.save(matchingSalesPerson);

        // 6. Transform updated entity to output object.
        return salesPersonMapper.transform(updatedSalesPerson);
    }
}

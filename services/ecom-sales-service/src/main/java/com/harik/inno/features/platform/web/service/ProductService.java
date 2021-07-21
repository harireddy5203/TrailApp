/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.web.service;

import com.harik.inno.commons.data.utils.PageUtils;
import com.harik.inno.commons.instrumentation.Instrument;
import com.harik.inno.features.platform.data.mapper.ProductMapper;
import com.harik.inno.features.platform.data.model.experience.product.CreateProductRequest;
import com.harik.inno.features.platform.data.model.experience.product.Product;
import com.harik.inno.features.platform.data.model.experience.product.UpdateProductRequest;
import com.harik.inno.features.platform.data.model.persistence.ProductEntity;
import com.harik.inno.features.platform.data.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * Service implementation that provides CRUD (Create, Read, Update, Delete) capabilities for
 * entities of type {@link ProductEntity}.
 *
 * @author Admin
 */
@Slf4j
@Validated
@Service
public class ProductService {
    /** Repository implementation of type {@link ProductRepository}. */
    private final ProductRepository productRepository;

    /** Mapper implementation of type {@link ProductMapper} to transform between different types. */
    private final ProductMapper productMapper;

    /**
     * Constructor.
     *
     * @param productRepository Repository instance of type {@link ProductRepository}.
     * @param productMapper Mapper instance of type {@link ProductMapper}.
     */
    public ProductService(
            final ProductRepository productRepository, final ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    /**
     * This method attempts to create an instance of type {@link ProductEntity} in the system based
     * on the provided payload.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     ProductEntity}.
     * @return An experience model of type {@link Product} that represents the newly created entity
     *     of type {@link ProductEntity}.
     */
    @Instrument
    @Transactional
    public Product createProduct(@Valid final CreateProductRequest payload) {
        // 1. Transform the experience model to a persistence model.
        final ProductEntity productEntity = productMapper.transform(payload);

        // 2. Save the entity.
        ProductService.LOGGER.debug("Saving a new instance of type - ProductEntity");
        final ProductEntity newInstance = productRepository.save(productEntity);

        // 3. Transform the created entity to an experience model and return it.
        return productMapper.transform(newInstance);
    }

    /**
     * This method attempts to update an existing instance of type {@link ProductEntity} using the
     * details from the provided input, which is an instance of type {@link UpdateProductRequest}.
     *
     * @param productId Unique identifier of Product in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Product, which needs to
     *     be updated in the system.
     * @return A instance of type {@link Product} containing the updated details.
     */
    @Instrument
    @Transactional
    public Product updateProduct(
            final Integer productId, @Valid final UpdateProductRequest payload) {
        // 1. Verify that the entity being updated truly exists.
        final ProductEntity matchingInstance = productRepository.findByIdOrThrow(productId);

        // 2. Transform the experience model to a persistence model and delegate to the save()
        // method.
        productMapper.transform(payload, matchingInstance);

        // 3. Save the entity
        ProductService.LOGGER.debug("Saving the updated entity - ProductEntity");
        final ProductEntity updatedInstance = productRepository.save(matchingInstance);

        // 4. Transform updated entity to output object
        return productMapper.transform(updatedInstance);
    }

    /**
     * This method attempts to find a {@link ProductEntity} whose unique identifier matches the
     * provided identifier.
     *
     * @param productId Unique identifier of Product in the system, whose details have to be
     *     retrieved.
     * @return Matching entity of type {@link Product} if found, else returns null.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Product findProduct(final Integer productId) {
        // 1. Find a matching entity and throw an exception if not found.
        final ProductEntity matchingInstance = productRepository.findByIdOrThrow(productId);

        // 2. Transform the matching entity to the desired output.
        return productMapper.transform(matchingInstance);
    }

    /**
     * This method attempts to find instances of type ProductEntity based on the provided page
     * definition. If the page definition is null or contains invalid values, this method attempts
     * to return the data for the first page (i.e. page index is 0) with a default page size as 20.
     *
     * @return Returns a page of objects based on the provided page definition. Each object in the
     *     returned page is an instance of type {@link Product}.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Page<Product> findAllProducts(final Pageable page) {
        // 1. Validate the provided pagination settings.
        final Pageable pageSettings = PageUtils.validateAndUpdatePaginationConfiguration(page);
        ProductService.LOGGER.debug(
                "Page settings: page number {}, page size {}",
                pageSettings.getPageNumber(),
                pageSettings.getPageSize());

        // 2. Delegate to the super class method to find the data (page settings are verified in
        // that method).
        final Page<ProductEntity> pageData = productRepository.findAll(pageSettings);

        // 3. If the page has data, transform each element into target type.
        if (pageData.hasContent()) {
            final List<Product> dataToReturn =
                    pageData.getContent().stream()
                            .map(productMapper::transform)
                            .collect(Collectors.toList());

            return PageUtils.createPage(dataToReturn, pageSettings, pageData.getTotalElements());
        }

        // Return empty page.
        return PageUtils.emptyPage(pageSettings);
    }

    /**
     * This method attempts to delete an existing instance of type {@link ProductEntity} whose
     * unique identifier matches the provided identifier.
     *
     * @param productId Unique identifier of Product in the system, which needs to be deleted.
     * @return Unique identifier of the instance of type ProductEntity that was deleted.
     */
    @Instrument
    @Transactional
    public Integer deleteProduct(final Integer productId) {
        // 1. Delegate to our repository method to handle the deletion.
        return productRepository.deleteOne(productId);
    }
}

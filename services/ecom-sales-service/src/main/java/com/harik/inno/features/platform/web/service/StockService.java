/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.web.service;

import com.harik.inno.commons.data.utils.PageUtils;
import com.harik.inno.commons.instrumentation.Instrument;
import com.harik.inno.features.platform.data.mapper.StockMapper;
import com.harik.inno.features.platform.data.model.experience.stock.CreateStockRequest;
import com.harik.inno.features.platform.data.model.experience.stock.Stock;
import com.harik.inno.features.platform.data.model.experience.stock.UpdateStockRequest;
import com.harik.inno.features.platform.data.model.persistence.StockEntity;
import com.harik.inno.features.platform.data.repository.StockRepository;
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
 * entities of type {@link StockEntity}.
 *
 * @author Admin
 */
@Slf4j
@Validated
@Service
public class StockService {
    /** Repository implementation of type {@link StockRepository}. */
    private final StockRepository stockRepository;

    /** Mapper implementation of type {@link StockMapper} to transform between different types. */
    private final StockMapper stockMapper;

    /**
     * Constructor.
     *
     * @param stockRepository Repository instance of type {@link StockRepository}.
     * @param stockMapper Mapper instance of type {@link StockMapper}.
     */
    public StockService(final StockRepository stockRepository, final StockMapper stockMapper) {
        this.stockRepository = stockRepository;
        this.stockMapper = stockMapper;
    }

    /**
     * This method attempts to create an instance of type {@link StockEntity} in the system based on
     * the provided payload.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     StockEntity}.
     * @return An experience model of type {@link Stock} that represents the newly created entity of
     *     type {@link StockEntity}.
     */
    @Instrument
    @Transactional
    public Stock createStock(@Valid final CreateStockRequest payload) {
        // 1. Transform the experience model to a persistence model.
        final StockEntity stockEntity = stockMapper.transform(payload);

        // 2. Save the entity.
        StockService.LOGGER.debug("Saving a new instance of type - StockEntity");
        final StockEntity newInstance = stockRepository.save(stockEntity);

        // 3. Transform the created entity to an experience model and return it.
        return stockMapper.transform(newInstance);
    }

    /**
     * This method attempts to update an existing instance of type {@link StockEntity} using the
     * details from the provided input, which is an instance of type {@link UpdateStockRequest}.
     *
     * @param stockId Unique identifier of Stock in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Stock, which needs to be
     *     updated in the system.
     * @return A instance of type {@link Stock} containing the updated details.
     */
    @Instrument
    @Transactional
    public Stock updateStock(final Integer stockId, @Valid final UpdateStockRequest payload) {
        // 1. Verify that the entity being updated truly exists.
        final StockEntity matchingInstance = stockRepository.findByIdOrThrow(stockId);

        // 2. Transform the experience model to a persistence model and delegate to the save()
        // method.
        stockMapper.transform(payload, matchingInstance);

        // 3. Save the entity
        StockService.LOGGER.debug("Saving the updated entity - StockEntity");
        final StockEntity updatedInstance = stockRepository.save(matchingInstance);

        // 4. Transform updated entity to output object
        return stockMapper.transform(updatedInstance);
    }

    /**
     * This method attempts to find a {@link StockEntity} whose unique identifier matches the
     * provided identifier.
     *
     * @param stockId Unique identifier of Stock in the system, whose details have to be retrieved.
     * @return Matching entity of type {@link Stock} if found, else returns null.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Stock findStock(final Integer stockId) {
        // 1. Find a matching entity and throw an exception if not found.
        final StockEntity matchingInstance = stockRepository.findByIdOrThrow(stockId);

        // 2. Transform the matching entity to the desired output.
        return stockMapper.transform(matchingInstance);
    }

    /**
     * This method attempts to find instances of type StockEntity based on the provided page
     * definition. If the page definition is null or contains invalid values, this method attempts
     * to return the data for the first page (i.e. page index is 0) with a default page size as 20.
     *
     * @return Returns a page of objects based on the provided page definition. Each object in the
     *     returned page is an instance of type {@link Stock}.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Page<Stock> findAllStocks(final Pageable page) {
        // 1. Validate the provided pagination settings.
        final Pageable pageSettings = PageUtils.validateAndUpdatePaginationConfiguration(page);
        StockService.LOGGER.debug(
                "Page settings: page number {}, page size {}",
                pageSettings.getPageNumber(),
                pageSettings.getPageSize());

        // 2. Delegate to the super class method to find the data (page settings are verified in
        // that method).
        final Page<StockEntity> pageData = stockRepository.findAll(pageSettings);

        // 3. If the page has data, transform each element into target type.
        if (pageData.hasContent()) {
            final List<Stock> dataToReturn =
                    pageData.getContent().stream()
                            .map(stockMapper::transform)
                            .collect(Collectors.toList());

            return PageUtils.createPage(dataToReturn, pageSettings, pageData.getTotalElements());
        }

        // Return empty page.
        return PageUtils.emptyPage(pageSettings);
    }

    /**
     * This method attempts to delete an existing instance of type {@link StockEntity} whose unique
     * identifier matches the provided identifier.
     *
     * @param stockId Unique identifier of Stock in the system, which needs to be deleted.
     * @return Unique identifier of the instance of type StockEntity that was deleted.
     */
    @Instrument
    @Transactional
    public Integer deleteStock(final Integer stockId) {
        // 1. Delegate to our repository method to handle the deletion.
        return stockRepository.deleteOne(stockId);
    }
}

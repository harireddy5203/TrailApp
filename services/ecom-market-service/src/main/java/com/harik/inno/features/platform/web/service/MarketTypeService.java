/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.web.service;

import com.harik.inno.commons.data.utils.PageUtils;
import com.harik.inno.commons.instrumentation.Instrument;
import com.harik.inno.features.platform.data.mapper.MarketTypeMapper;
import com.harik.inno.features.platform.data.model.experience.markettype.CreateMarketTypeRequest;
import com.harik.inno.features.platform.data.model.experience.markettype.MarketType;
import com.harik.inno.features.platform.data.model.experience.markettype.UpdateMarketTypeRequest;
import com.harik.inno.features.platform.data.model.persistence.MarketTypeEntity;
import com.harik.inno.features.platform.data.repository.MarketTypeRepository;
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
 * entities of type {@link MarketTypeEntity}.
 *
 * @author Admin
 */
@Slf4j
@Validated
@Service
public class MarketTypeService {
    /** Repository implementation of type {@link MarketTypeRepository}. */
    private final MarketTypeRepository marketTypeRepository;

    /**
     * Mapper implementation of type {@link MarketTypeMapper} to transform between different types.
     */
    private final MarketTypeMapper marketTypeMapper;

    /**
     * Constructor.
     *
     * @param marketTypeRepository Repository instance of type {@link MarketTypeRepository}.
     * @param marketTypeMapper Mapper instance of type {@link MarketTypeMapper}.
     */
    public MarketTypeService(
            final MarketTypeRepository marketTypeRepository,
            final MarketTypeMapper marketTypeMapper) {
        this.marketTypeRepository = marketTypeRepository;
        this.marketTypeMapper = marketTypeMapper;
    }

    /**
     * This method attempts to create an instance of type {@link MarketTypeEntity} in the system
     * based on the provided payload.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     MarketTypeEntity}.
     * @return An experience model of type {@link MarketType} that represents the newly created
     *     entity of type {@link MarketTypeEntity}.
     */
    @Instrument
    @Transactional
    public MarketType createMarketType(@Valid final CreateMarketTypeRequest payload) {
        // 1. Transform the experience model to a persistence model.
        final MarketTypeEntity marketTypeEntity = marketTypeMapper.transform(payload);

        // 2. Save the entity.
        MarketTypeService.LOGGER.debug("Saving a new instance of type - MarketTypeEntity");
        final MarketTypeEntity newInstance = marketTypeRepository.save(marketTypeEntity);

        // 3. Transform the created entity to an experience model and return it.
        return marketTypeMapper.transform(newInstance);
    }

    /**
     * This method attempts to update an existing instance of type {@link MarketTypeEntity} using
     * the details from the provided input, which is an instance of type {@link
     * UpdateMarketTypeRequest}.
     *
     * @param marketTypeId Unique identifier of MarketType in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing MarketType, which needs
     *     to be updated in the system.
     * @return A instance of type {@link MarketType} containing the updated details.
     */
    @Instrument
    @Transactional
    public MarketType updateMarketType(
            final Integer marketTypeId, @Valid final UpdateMarketTypeRequest payload) {
        // 1. Verify that the entity being updated truly exists.
        final MarketTypeEntity matchingInstance =
                marketTypeRepository.findByIdOrThrow(marketTypeId);

        // 2. Transform the experience model to a persistence model and delegate to the save()
        // method.
        marketTypeMapper.transform(payload, matchingInstance);

        // 3. Save the entity
        MarketTypeService.LOGGER.debug("Saving the updated entity - MarketTypeEntity");
        final MarketTypeEntity updatedInstance = marketTypeRepository.save(matchingInstance);

        // 4. Transform updated entity to output object
        return marketTypeMapper.transform(updatedInstance);
    }

    /**
     * This method attempts to find a {@link MarketTypeEntity} whose unique identifier matches the
     * provided identifier.
     *
     * @param marketTypeId Unique identifier of MarketType in the system, whose details have to be
     *     retrieved.
     * @return Matching entity of type {@link MarketType} if found, else returns null.
     */
    @Instrument
    @Transactional(readOnly = true)
    public MarketType findMarketType(final Integer marketTypeId) {
        // 1. Find a matching entity and throw an exception if not found.
        final MarketTypeEntity matchingInstance =
                marketTypeRepository.findByIdOrThrow(marketTypeId);

        // 2. Transform the matching entity to the desired output.
        return marketTypeMapper.transform(matchingInstance);
    }

    /**
     * This method attempts to find instances of type MarketTypeEntity based on the provided page
     * definition. If the page definition is null or contains invalid values, this method attempts
     * to return the data for the first page (i.e. page index is 0) with a default page size as 20.
     *
     * @return Returns a page of objects based on the provided page definition. Each object in the
     *     returned page is an instance of type {@link MarketType}.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Page<MarketType> findAllMarketTypes(final Pageable page) {
        // 1. Validate the provided pagination settings.
        final Pageable pageSettings = PageUtils.validateAndUpdatePaginationConfiguration(page);
        MarketTypeService.LOGGER.debug(
                "Page settings: page number {}, page size {}",
                pageSettings.getPageNumber(),
                pageSettings.getPageSize());

        // 2. Delegate to the super class method to find the data (page settings are verified in
        // that method).
        final Page<MarketTypeEntity> pageData = marketTypeRepository.findAll(pageSettings);

        // 3. If the page has data, transform each element into target type.
        if (pageData.hasContent()) {
            final List<MarketType> dataToReturn =
                    pageData.getContent().stream()
                            .map(marketTypeMapper::transform)
                            .collect(Collectors.toList());

            return PageUtils.createPage(dataToReturn, pageSettings, pageData.getTotalElements());
        }

        // Return empty page.
        return PageUtils.emptyPage(pageSettings);
    }

    /**
     * This method attempts to delete an existing instance of type {@link MarketTypeEntity} whose
     * unique identifier matches the provided identifier.
     *
     * @param marketTypeId Unique identifier of MarketType in the system, which needs to be deleted.
     * @return Unique identifier of the instance of type MarketTypeEntity that was deleted.
     */
    @Instrument
    @Transactional
    public Integer deleteMarketType(final Integer marketTypeId) {
        // 1. Delegate to our repository method to handle the deletion.
        return marketTypeRepository.deleteOne(marketTypeId);
    }
}

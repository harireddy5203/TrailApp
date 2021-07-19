/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.web.service;

import com.harik.inno.commons.data.utils.PageUtils;
import com.harik.inno.commons.instrumentation.Instrument;
import com.harik.inno.features.platform.data.mapper.MarketAreaMapper;
import com.harik.inno.features.platform.data.model.experience.marketarea.CreateMarketAreaRequest;
import com.harik.inno.features.platform.data.model.experience.marketarea.MarketArea;
import com.harik.inno.features.platform.data.model.experience.marketarea.UpdateMarketAreaRequest;
import com.harik.inno.features.platform.data.model.persistence.MarketAreaEntity;
import com.harik.inno.features.platform.data.repository.MarketAreaRepository;
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
 * entities of type {@link MarketAreaEntity}.
 *
 * @author Admin
 */
@Slf4j
@Validated
@Service
public class MarketAreaService {
    /** Repository implementation of type {@link MarketAreaRepository}. */
    private final MarketAreaRepository marketAreaRepository;

    /**
     * Mapper implementation of type {@link MarketAreaMapper} to transform between different types.
     */
    private final MarketAreaMapper marketAreaMapper;

    /**
     * Constructor.
     *
     * @param marketAreaRepository Repository instance of type {@link MarketAreaRepository}.
     * @param marketAreaMapper Mapper instance of type {@link MarketAreaMapper}.
     */
    public MarketAreaService(
            final MarketAreaRepository marketAreaRepository,
            final MarketAreaMapper marketAreaMapper) {
        this.marketAreaRepository = marketAreaRepository;
        this.marketAreaMapper = marketAreaMapper;
    }

    /**
     * This method attempts to create an instance of type {@link MarketAreaEntity} in the system
     * based on the provided payload.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     MarketAreaEntity}.
     * @return An experience model of type {@link MarketArea} that represents the newly created
     *     entity of type {@link MarketAreaEntity}.
     */
    @Instrument
    @Transactional
    public MarketArea createMarketArea(@Valid final CreateMarketAreaRequest payload) {
        // 1. Transform the experience model to a persistence model.
        final MarketAreaEntity marketAreaEntity = marketAreaMapper.transform(payload);

        // 2. Save the entity.
        MarketAreaService.LOGGER.debug("Saving a new instance of type - MarketAreaEntity");
        final MarketAreaEntity newInstance = marketAreaRepository.save(marketAreaEntity);

        // 3. Transform the created entity to an experience model and return it.
        return marketAreaMapper.transform(newInstance);
    }

    /**
     * This method attempts to update an existing instance of type {@link MarketAreaEntity} using
     * the details from the provided input, which is an instance of type {@link
     * UpdateMarketAreaRequest}.
     *
     * @param marketAreaId Unique identifier of MarketArea in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing MarketArea, which needs
     *     to be updated in the system.
     * @return A instance of type {@link MarketArea} containing the updated details.
     */
    @Instrument
    @Transactional
    public MarketArea updateMarketArea(
            final Integer marketAreaId, @Valid final UpdateMarketAreaRequest payload) {
        // 1. Verify that the entity being updated truly exists.
        final MarketAreaEntity matchingInstance =
                marketAreaRepository.findByIdOrThrow(marketAreaId);

        // 2. Transform the experience model to a persistence model and delegate to the save()
        // method.
        marketAreaMapper.transform(payload, matchingInstance);

        // 3. Save the entity
        MarketAreaService.LOGGER.debug("Saving the updated entity - MarketAreaEntity");
        final MarketAreaEntity updatedInstance = marketAreaRepository.save(matchingInstance);

        // 4. Transform updated entity to output object
        return marketAreaMapper.transform(updatedInstance);
    }

    /**
     * This method attempts to find a {@link MarketAreaEntity} whose unique identifier matches the
     * provided identifier.
     *
     * @param marketAreaId Unique identifier of MarketArea in the system, whose details have to be
     *     retrieved.
     * @return Matching entity of type {@link MarketArea} if found, else returns null.
     */
    @Instrument
    @Transactional(readOnly = true)
    public MarketArea findMarketArea(final Integer marketAreaId) {
        // 1. Find a matching entity and throw an exception if not found.
        final MarketAreaEntity matchingInstance =
                marketAreaRepository.findByIdOrThrow(marketAreaId);

        // 2. Transform the matching entity to the desired output.
        return marketAreaMapper.transform(matchingInstance);
    }

    /**
     * This method attempts to find instances of type MarketAreaEntity based on the provided page
     * definition. If the page definition is null or contains invalid values, this method attempts
     * to return the data for the first page (i.e. page index is 0) with a default page size as 20.
     *
     * @return Returns a page of objects based on the provided page definition. Each object in the
     *     returned page is an instance of type {@link MarketArea}.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Page<MarketArea> findAllMarketAreas(final Pageable page) {
        // 1. Validate the provided pagination settings.
        final Pageable pageSettings = PageUtils.validateAndUpdatePaginationConfiguration(page);
        MarketAreaService.LOGGER.debug(
                "Page settings: page number {}, page size {}",
                pageSettings.getPageNumber(),
                pageSettings.getPageSize());

        // 2. Delegate to the super class method to find the data (page settings are verified in
        // that method).
        final Page<MarketAreaEntity> pageData = marketAreaRepository.findAll(pageSettings);

        // 3. If the page has data, transform each element into target type.
        if (pageData.hasContent()) {
            final List<MarketArea> dataToReturn =
                    pageData.getContent().stream()
                            .map(marketAreaMapper::transform)
                            .collect(Collectors.toList());

            return PageUtils.createPage(dataToReturn, pageSettings, pageData.getTotalElements());
        }

        // Return empty page.
        return PageUtils.emptyPage(pageSettings);
    }

    /**
     * This method attempts to delete an existing instance of type {@link MarketAreaEntity} whose
     * unique identifier matches the provided identifier.
     *
     * @param marketAreaId Unique identifier of MarketArea in the system, which needs to be deleted.
     * @return Unique identifier of the instance of type MarketAreaEntity that was deleted.
     */
    @Instrument
    @Transactional
    public Integer deleteMarketArea(final Integer marketAreaId) {
        // 1. Delegate to our repository method to handle the deletion.
        return marketAreaRepository.deleteOne(marketAreaId);
    }
}

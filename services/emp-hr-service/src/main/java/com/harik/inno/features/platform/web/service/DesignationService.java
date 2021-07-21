/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.web.service;

import com.harik.inno.commons.data.utils.PageUtils;
import com.harik.inno.commons.instrumentation.Instrument;
import com.harik.inno.features.platform.data.mapper.DesignationMapper;
import com.harik.inno.features.platform.data.model.experience.designation.CreateDesignationRequest;
import com.harik.inno.features.platform.data.model.experience.designation.Designation;
import com.harik.inno.features.platform.data.model.experience.designation.UpdateDesignationRequest;
import com.harik.inno.features.platform.data.model.persistence.DesignationEntity;
import com.harik.inno.features.platform.data.repository.DesignationRepository;
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
 * entities of type {@link DesignationEntity}.
 *
 * @author Admin
 */
@Slf4j
@Validated
@Service
public class DesignationService {
    /** Repository implementation of type {@link DesignationRepository}. */
    private final DesignationRepository designationRepository;

    /**
     * Mapper implementation of type {@link DesignationMapper} to transform between different types.
     */
    private final DesignationMapper designationMapper;

    /**
     * Constructor.
     *
     * @param designationRepository Repository instance of type {@link DesignationRepository}.
     * @param designationMapper Mapper instance of type {@link DesignationMapper}.
     */
    public DesignationService(
            final DesignationRepository designationRepository,
            final DesignationMapper designationMapper) {
        this.designationRepository = designationRepository;
        this.designationMapper = designationMapper;
    }

    /**
     * This method attempts to create an instance of type {@link DesignationEntity} in the system
     * based on the provided payload.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     DesignationEntity}.
     * @return An experience model of type {@link Designation} that represents the newly created
     *     entity of type {@link DesignationEntity}.
     */
    @Instrument
    @Transactional
    public Designation createDesignation(@Valid final CreateDesignationRequest payload) {
        // 1. Transform the experience model to a persistence model.
        final DesignationEntity designationEntity = designationMapper.transform(payload);

        // 2. Save the entity.
        DesignationService.LOGGER.debug("Saving a new instance of type - DesignationEntity");
        final DesignationEntity newInstance = designationRepository.save(designationEntity);

        // 3. Transform the created entity to an experience model and return it.
        return designationMapper.transform(newInstance);
    }

    /**
     * This method attempts to update an existing instance of type {@link DesignationEntity} using
     * the details from the provided input, which is an instance of type {@link
     * UpdateDesignationRequest}.
     *
     * @param designationId Unique identifier of Designation in the system, which needs to be
     *     updated.
     * @param payload Request payload containing the details of an existing Designation, which needs
     *     to be updated in the system.
     * @return A instance of type {@link Designation} containing the updated details.
     */
    @Instrument
    @Transactional
    public Designation updateDesignation(
            final String designationId, @Valid final UpdateDesignationRequest payload) {
        // 1. Verify that the entity being updated truly exists.
        final DesignationEntity matchingInstance =
                designationRepository.findByIdOrThrow(designationId);

        // 2. Transform the experience model to a persistence model and delegate to the save()
        // method.
        designationMapper.transform(payload, matchingInstance);

        // 3. Save the entity
        DesignationService.LOGGER.debug("Saving the updated entity - DesignationEntity");
        final DesignationEntity updatedInstance = designationRepository.save(matchingInstance);

        // 4. Transform updated entity to output object
        return designationMapper.transform(updatedInstance);
    }

    /**
     * This method attempts to find a {@link DesignationEntity} whose unique identifier matches the
     * provided identifier.
     *
     * @param designationId Unique identifier of Designation in the system, whose details have to be
     *     retrieved.
     * @return Matching entity of type {@link Designation} if found, else returns null.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Designation findDesignation(final String designationId) {
        // 1. Find a matching entity and throw an exception if not found.
        final DesignationEntity matchingInstance =
                designationRepository.findByIdOrThrow(designationId);

        // 2. Transform the matching entity to the desired output.
        return designationMapper.transform(matchingInstance);
    }

    /**
     * This method attempts to find instances of type DesignationEntity based on the provided page
     * definition. If the page definition is null or contains invalid values, this method attempts
     * to return the data for the first page (i.e. page index is 0) with a default page size as 20.
     *
     * @return Returns a page of objects based on the provided page definition. Each object in the
     *     returned page is an instance of type {@link Designation}.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Page<Designation> findAllDesignations(final Pageable page) {
        // 1. Validate the provided pagination settings.
        final Pageable pageSettings = PageUtils.validateAndUpdatePaginationConfiguration(page);
        DesignationService.LOGGER.debug(
                "Page settings: page number {}, page size {}",
                pageSettings.getPageNumber(),
                pageSettings.getPageSize());

        // 2. Delegate to the super class method to find the data (page settings are verified in
        // that method).
        final Page<DesignationEntity> pageData = designationRepository.findAll(pageSettings);

        // 3. If the page has data, transform each element into target type.
        if (pageData.hasContent()) {
            final List<Designation> dataToReturn =
                    pageData.getContent().stream()
                            .map(designationMapper::transform)
                            .collect(Collectors.toList());

            return PageUtils.createPage(dataToReturn, pageSettings, pageData.getTotalElements());
        }

        // Return empty page.
        return PageUtils.emptyPage(pageSettings);
    }

    /**
     * This method attempts to delete an existing instance of type {@link DesignationEntity} whose
     * unique identifier matches the provided identifier.
     *
     * @param designationId Unique identifier of Designation in the system, which needs to be
     *     deleted.
     * @return Unique identifier of the instance of type DesignationEntity that was deleted.
     */
    @Instrument
    @Transactional
    public String deleteDesignation(final String designationId) {
        // 1. Delegate to our repository method to handle the deletion.
        return designationRepository.deleteOne(designationId);
    }
}

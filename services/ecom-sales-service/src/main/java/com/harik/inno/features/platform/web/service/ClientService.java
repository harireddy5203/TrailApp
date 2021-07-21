/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.web.service;

import com.harik.inno.commons.data.utils.PageUtils;
import com.harik.inno.commons.instrumentation.Instrument;
import com.harik.inno.features.platform.data.mapper.ClientMapper;
import com.harik.inno.features.platform.data.model.experience.client.Client;
import com.harik.inno.features.platform.data.model.experience.client.CreateClientRequest;
import com.harik.inno.features.platform.data.model.experience.client.UpdateClientRequest;
import com.harik.inno.features.platform.data.model.persistence.ClientEntity;
import com.harik.inno.features.platform.data.repository.ClientRepository;
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
 * entities of type {@link ClientEntity}.
 *
 * @author Admin
 */
@Slf4j
@Validated
@Service
public class ClientService {
    /** Repository implementation of type {@link ClientRepository}. */
    private final ClientRepository clientRepository;

    /** Mapper implementation of type {@link ClientMapper} to transform between different types. */
    private final ClientMapper clientMapper;

    /**
     * Constructor.
     *
     * @param clientRepository Repository instance of type {@link ClientRepository}.
     * @param clientMapper Mapper instance of type {@link ClientMapper}.
     */
    public ClientService(final ClientRepository clientRepository, final ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    /**
     * This method attempts to create an instance of type {@link ClientEntity} in the system based
     * on the provided payload.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     ClientEntity}.
     * @return An experience model of type {@link Client} that represents the newly created entity
     *     of type {@link ClientEntity}.
     */
    @Instrument
    @Transactional
    public Client createClient(@Valid final CreateClientRequest payload) {
        // 1. Transform the experience model to a persistence model.
        final ClientEntity clientEntity = clientMapper.transform(payload);

        // 2. Save the entity.
        ClientService.LOGGER.debug("Saving a new instance of type - ClientEntity");
        final ClientEntity newInstance = clientRepository.save(clientEntity);

        // 3. Transform the created entity to an experience model and return it.
        return clientMapper.transform(newInstance);
    }

    /**
     * This method attempts to update an existing instance of type {@link ClientEntity} using the
     * details from the provided input, which is an instance of type {@link UpdateClientRequest}.
     *
     * @param clientId Unique identifier of Client in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing Client, which needs to
     *     be updated in the system.
     * @return A instance of type {@link Client} containing the updated details.
     */
    @Instrument
    @Transactional
    public Client updateClient(final Integer clientId, @Valid final UpdateClientRequest payload) {
        // 1. Verify that the entity being updated truly exists.
        final ClientEntity matchingInstance = clientRepository.findByIdOrThrow(clientId);

        // 2. Transform the experience model to a persistence model and delegate to the save()
        // method.
        clientMapper.transform(payload, matchingInstance);

        // 3. Save the entity
        ClientService.LOGGER.debug("Saving the updated entity - ClientEntity");
        final ClientEntity updatedInstance = clientRepository.save(matchingInstance);

        // 4. Transform updated entity to output object
        return clientMapper.transform(updatedInstance);
    }

    /**
     * This method attempts to find a {@link ClientEntity} whose unique identifier matches the
     * provided identifier.
     *
     * @param clientId Unique identifier of Client in the system, whose details have to be
     *     retrieved.
     * @return Matching entity of type {@link Client} if found, else returns null.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Client findClient(final Integer clientId) {
        // 1. Find a matching entity and throw an exception if not found.
        final ClientEntity matchingInstance = clientRepository.findByIdOrThrow(clientId);

        // 2. Transform the matching entity to the desired output.
        return clientMapper.transform(matchingInstance);
    }

    /**
     * This method attempts to find instances of type ClientEntity based on the provided page
     * definition. If the page definition is null or contains invalid values, this method attempts
     * to return the data for the first page (i.e. page index is 0) with a default page size as 20.
     *
     * @return Returns a page of objects based on the provided page definition. Each object in the
     *     returned page is an instance of type {@link Client}.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Page<Client> findAllClients(final Pageable page) {
        // 1. Validate the provided pagination settings.
        final Pageable pageSettings = PageUtils.validateAndUpdatePaginationConfiguration(page);
        ClientService.LOGGER.debug(
                "Page settings: page number {}, page size {}",
                pageSettings.getPageNumber(),
                pageSettings.getPageSize());

        // 2. Delegate to the super class method to find the data (page settings are verified in
        // that method).
        final Page<ClientEntity> pageData = clientRepository.findAll(pageSettings);

        // 3. If the page has data, transform each element into target type.
        if (pageData.hasContent()) {
            final List<Client> dataToReturn =
                    pageData.getContent().stream()
                            .map(clientMapper::transform)
                            .collect(Collectors.toList());

            return PageUtils.createPage(dataToReturn, pageSettings, pageData.getTotalElements());
        }

        // Return empty page.
        return PageUtils.emptyPage(pageSettings);
    }

    /**
     * This method attempts to delete an existing instance of type {@link ClientEntity} whose unique
     * identifier matches the provided identifier.
     *
     * @param clientId Unique identifier of Client in the system, which needs to be deleted.
     * @return Unique identifier of the instance of type ClientEntity that was deleted.
     */
    @Instrument
    @Transactional
    public Integer deleteClient(final Integer clientId) {
        // 1. Delegate to our repository method to handle the deletion.
        return clientRepository.deleteOne(clientId);
    }
}

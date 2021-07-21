/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.data.mapper;

import com.harik.inno.features.platform.data.model.experience.client.Client;
import com.harik.inno.features.platform.data.model.experience.client.CreateClientRequest;
import com.harik.inno.features.platform.data.model.experience.client.UpdateClientRequest;
import com.harik.inno.features.platform.data.model.persistence.ClientEntity;
import java.util.Collection;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper contract that maps / transforms data from an instance of type {@link ClientEntity to {@link Client and vice-versa.
 *
 * @author Admin
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface ClientMapper {

    /**
     * This method transforms an instance of type {@link CreateClientRequest} to an instance of type
     * {@link ClientEntity}.
     *
     * @param source Instance of type {@link CreateClientRequest} that needs to be transformed to
     *     {@link ClientEntity}.
     * @return Instance of type {@link ClientEntity}.
     */
    ClientEntity transform(CreateClientRequest source);

    /**
     * This method transforms an instance of type {@link ClientEntity} to an instance of type {@link
     * Client}.
     *
     * @param source Instance of type {@link ClientEntity} that needs to be transformed to {@link
     *     Client}.
     * @return Instance of type {@link Client}.
     */
    Client transform(ClientEntity source);

    /**
     * This method converts / transforms the provided collection of {@link ClientEntity} instances
     * to a collection of instances of type {@link Client}.
     *
     * @param source Instance of type {@link ClientEntity} that needs to be transformed to {@link
     *     Client}.
     * @return Collection of instance of type {@link Client}.
     */
    default Collection<Client> transformListTo(Collection<ClientEntity> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
    /**
     * This method transforms an instance of type {@link UpdateClientRequest} to an instance of type
     * {@link ClientEntity}.
     *
     * <p>The provided instance ({@code target}) will be updated instead of creating a new instance.
     *
     * @param source Instance of type {@link UpdateClientRequest} that needs to be transformed to
     *     {@link ClientEntity}.
     * @param target Instance of type {@link ClientEntity} that will be updated instead of creating
     *     and returning a new instance.
     */
    void transform(UpdateClientRequest source, @MappingTarget ClientEntity target);

    /**
     * This method transforms an instance of type {@link UpdateClientRequest} to an instance of type
     * {@link ClientEntity}.
     *
     * @param source Instance of type {@link UpdateClientRequest} that needs to be transformed to
     *     {@link ClientEntity}.
     * @return Instance of type {@link ClientEntity}.
     */
    ClientEntity transform(UpdateClientRequest source);

    /**
     * This method converts / transforms the provided collection of {@link UpdateClientRequest}
     * instances to a collection of instances of type {@link ClientEntity}.
     *
     * @param source Instance of type {@link UpdateClientRequest} that needs to be transformed to
     *     {@link ClientEntity}.
     * @return Instance of type {@link ClientEntity}.
     */
    default Collection<ClientEntity> transformList(Collection<UpdateClientRequest> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
}

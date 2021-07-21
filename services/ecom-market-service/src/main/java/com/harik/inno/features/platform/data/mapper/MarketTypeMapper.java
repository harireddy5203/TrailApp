/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.data.mapper;

import com.harik.inno.features.platform.data.model.experience.markettype.CreateMarketTypeRequest;
import com.harik.inno.features.platform.data.model.experience.markettype.MarketType;
import com.harik.inno.features.platform.data.model.experience.markettype.UpdateMarketTypeRequest;
import com.harik.inno.features.platform.data.model.persistence.MarketTypeEntity;
import java.util.Collection;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper contract that maps / transforms data from an instance of type {@link MarketTypeEntity to {@link MarketType and vice-versa.
 *
 * @author Admin
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface MarketTypeMapper {

    /**
     * This method transforms an instance of type {@link CreateMarketTypeRequest} to an instance of
     * type {@link MarketTypeEntity}.
     *
     * @param source Instance of type {@link CreateMarketTypeRequest} that needs to be transformed
     *     to {@link MarketTypeEntity}.
     * @return Instance of type {@link MarketTypeEntity}.
     */
    MarketTypeEntity transform(CreateMarketTypeRequest source);

    /**
     * This method transforms an instance of type {@link MarketTypeEntity} to an instance of type
     * {@link MarketType}.
     *
     * @param source Instance of type {@link MarketTypeEntity} that needs to be transformed to
     *     {@link MarketType}.
     * @return Instance of type {@link MarketType}.
     */
    MarketType transform(MarketTypeEntity source);

    /**
     * This method converts / transforms the provided collection of {@link MarketTypeEntity}
     * instances to a collection of instances of type {@link MarketType}.
     *
     * @param source Instance of type {@link MarketTypeEntity} that needs to be transformed to
     *     {@link MarketType}.
     * @return Collection of instance of type {@link MarketType}.
     */
    default Collection<MarketType> transformListTo(Collection<MarketTypeEntity> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
    /**
     * This method transforms an instance of type {@link UpdateMarketTypeRequest} to an instance of
     * type {@link MarketTypeEntity}.
     *
     * <p>The provided instance ({@code target}) will be updated instead of creating a new instance.
     *
     * @param source Instance of type {@link UpdateMarketTypeRequest} that needs to be transformed
     *     to {@link MarketTypeEntity}.
     * @param target Instance of type {@link MarketTypeEntity} that will be updated instead of
     *     creating and returning a new instance.
     */
    void transform(UpdateMarketTypeRequest source, @MappingTarget MarketTypeEntity target);

    /**
     * This method transforms an instance of type {@link UpdateMarketTypeRequest} to an instance of
     * type {@link MarketTypeEntity}.
     *
     * @param source Instance of type {@link UpdateMarketTypeRequest} that needs to be transformed
     *     to {@link MarketTypeEntity}.
     * @return Instance of type {@link MarketTypeEntity}.
     */
    MarketTypeEntity transform(UpdateMarketTypeRequest source);

    /**
     * This method converts / transforms the provided collection of {@link UpdateMarketTypeRequest}
     * instances to a collection of instances of type {@link MarketTypeEntity}.
     *
     * @param source Instance of type {@link UpdateMarketTypeRequest} that needs to be transformed
     *     to {@link MarketTypeEntity}.
     * @return Instance of type {@link MarketTypeEntity}.
     */
    default Collection<MarketTypeEntity> transformList(Collection<UpdateMarketTypeRequest> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
}

/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.data.mapper;

import com.harik.inno.features.platform.data.mapper.decorator.MarketAreaMapperDecorator;
import com.harik.inno.features.platform.data.model.experience.marketarea.CreateMarketAreaRequest;
import com.harik.inno.features.platform.data.model.experience.marketarea.MarketArea;
import com.harik.inno.features.platform.data.model.experience.marketarea.UpdateMarketAreaRequest;
import com.harik.inno.features.platform.data.model.persistence.MarketAreaEntity;
import java.util.Collection;
import java.util.stream.Collectors;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper contract that maps / transforms data from an instance of type {@link MarketAreaEntity to {@link MarketArea and vice-versa.
 *
 * @author Admin
 */
@DecoratedWith(value = MarketAreaMapperDecorator.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface MarketAreaMapper {

    /**
     * This method transforms an instance of type {@link CreateMarketAreaRequest} to an instance of
     * type {@link MarketAreaEntity}.
     *
     * @param source Instance of type {@link CreateMarketAreaRequest} that needs to be transformed
     *     to {@link MarketAreaEntity}.
     * @return Instance of type {@link MarketAreaEntity}.
     */
    @Mapping(source = "markettypes", target = "markettypes", ignore = true)
    MarketAreaEntity transform(CreateMarketAreaRequest source);

    /**
     * This method transforms an instance of type {@link MarketAreaEntity} to an instance of type
     * {@link MarketArea}.
     *
     * @param source Instance of type {@link MarketAreaEntity} that needs to be transformed to
     *     {@link MarketArea}.
     * @return Instance of type {@link MarketArea}.
     */
    @Mapping(source = "markettypes", target = "markettypes", ignore = true)
    MarketArea transform(MarketAreaEntity source);

    /**
     * This method converts / transforms the provided collection of {@link MarketAreaEntity}
     * instances to a collection of instances of type {@link MarketArea}.
     *
     * @param source Instance of type {@link MarketAreaEntity} that needs to be transformed to
     *     {@link MarketArea}.
     * @return Collection of instance of type {@link MarketArea}.
     */
    default Collection<MarketArea> transformListTo(Collection<MarketAreaEntity> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
    /**
     * This method transforms an instance of type {@link UpdateMarketAreaRequest} to an instance of
     * type {@link MarketAreaEntity}.
     *
     * <p>The provided instance ({@code target}) will be updated instead of creating a new instance.
     *
     * @param source Instance of type {@link UpdateMarketAreaRequest} that needs to be transformed
     *     to {@link MarketAreaEntity}.
     * @param target Instance of type {@link MarketAreaEntity} that will be updated instead of
     *     creating and returning a new instance.
     */
    @Mapping(source = "markettypes", target = "markettypes", ignore = true)
    void transform(UpdateMarketAreaRequest source, @MappingTarget MarketAreaEntity target);

    /**
     * This method transforms an instance of type {@link UpdateMarketAreaRequest} to an instance of
     * type {@link MarketAreaEntity}.
     *
     * @param source Instance of type {@link UpdateMarketAreaRequest} that needs to be transformed
     *     to {@link MarketAreaEntity}.
     * @return Instance of type {@link MarketAreaEntity}.
     */
    @Mapping(source = "markettypes", target = "markettypes", ignore = true)
    MarketAreaEntity transform(UpdateMarketAreaRequest source);

    /**
     * This method converts / transforms the provided collection of {@link UpdateMarketAreaRequest}
     * instances to a collection of instances of type {@link MarketAreaEntity}.
     *
     * @param source Instance of type {@link UpdateMarketAreaRequest} that needs to be transformed
     *     to {@link MarketAreaEntity}.
     * @return Instance of type {@link MarketAreaEntity}.
     */
    default Collection<MarketAreaEntity> transformList(Collection<UpdateMarketAreaRequest> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
}

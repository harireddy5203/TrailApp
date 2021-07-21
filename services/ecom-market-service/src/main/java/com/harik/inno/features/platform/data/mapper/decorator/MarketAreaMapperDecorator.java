/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.data.mapper.decorator;

import com.harik.inno.features.platform.data.mapper.MarketAreaMapper;
import com.harik.inno.features.platform.data.mapper.MarketTypeMapper;
import com.harik.inno.features.platform.data.model.experience.marketarea.CreateMarketAreaRequest;
import com.harik.inno.features.platform.data.model.experience.marketarea.MarketArea;
import com.harik.inno.features.platform.data.model.experience.marketarea.UpdateMarketAreaRequest;
import com.harik.inno.features.platform.data.model.persistence.MarketAreaEntity;
import com.harik.inno.features.platform.data.repository.MarketTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

/**
 * Decorator implementation that maps / transforms data from an instance of type {@link MarketAreaEntity to {@link MarketArea and vice-versa.
 *
 * @author Admin
 */
@Slf4j
public abstract class MarketAreaMapperDecorator implements MarketAreaMapper {

    /** Repository implementation of type {@link MarketTypeRepository}. */
    @Autowired private MarketTypeRepository marketTypeRepository;

    /** Mapper implementation of type {@link MarketAreaMapper}. */
    @Autowired private MarketAreaMapper marketAreaMapper;

    /** Mapper implementation of type {@link MarketTypeMapper}. */
    @Autowired private MarketTypeMapper marketTypeMapper;

    @Override
    public MarketAreaEntity transform(final CreateMarketAreaRequest source) {
        // 1. Transform the CreateMarketAreaRequest to MarketAreaEntity object.
        final MarketAreaEntity market_area = marketAreaMapper.transform(source);

        market_area.setMarkettypes(marketTypeRepository.findAllById(source.getMarkettypes()));

        // Return the transformed object.
        return market_area;
    }

    @Override
    public MarketArea transform(final MarketAreaEntity source) {
        // 1. Transform the MarketAreaEntity to MarketArea object.
        final MarketArea market_area = marketAreaMapper.transform(source);

        market_area.setMarkettypes(marketTypeMapper.transformListTo(source.getMarkettypes()));

        // Return the transformed object.
        return market_area;
    }

    @Override
    public void transform(
            final UpdateMarketAreaRequest source, final @MappingTarget MarketAreaEntity target) {

        // Transform from source to the target.
        marketAreaMapper.transform(source, target);

        if (!CollectionUtils.isEmpty(source.getMarkettypes())) {
            target.setMarkettypes(marketTypeRepository.findAllById(source.getMarkettypes()));
        }
    }

    @Override
    public MarketAreaEntity transform(final UpdateMarketAreaRequest source) {

        // Transform from source to the target.
        final MarketAreaEntity market_area = marketAreaMapper.transform(source);

        if (!CollectionUtils.isEmpty(source.getMarkettypes())) {
            market_area.setMarkettypes(marketTypeRepository.findAllById(source.getMarkettypes()));
        }
        // Return the response.
        return market_area;
    }
}

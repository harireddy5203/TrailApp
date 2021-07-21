/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.data.mapper.decorator;

import com.harik.inno.features.platform.data.mapper.ProductMapper;
import com.harik.inno.features.platform.data.mapper.StockMapper;
import com.harik.inno.features.platform.data.model.experience.stock.CreateStockRequest;
import com.harik.inno.features.platform.data.model.experience.stock.Stock;
import com.harik.inno.features.platform.data.model.experience.stock.UpdateStockRequest;
import com.harik.inno.features.platform.data.model.persistence.StockEntity;
import com.harik.inno.features.platform.data.repository.ProductRepository;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Decorator implementation that maps / transforms data from an instance of type {@link StockEntity to {@link Stock and vice-versa.
 *
 * @author Admin
 */
@Slf4j
public abstract class StockMapperDecorator implements StockMapper {

    /** Repository implementation of type {@link ProductRepository}. */
    @Autowired private ProductRepository productRepository;

    /** Mapper implementation of type {@link StockMapper}. */
    @Autowired private StockMapper stockMapper;

    /** Mapper implementation of type {@link ProductMapper}. */
    @Autowired private ProductMapper productMapper;

    @Override
    public StockEntity transform(final CreateStockRequest source) {
        // 1. Transform the CreateStockRequest to StockEntity object.
        final StockEntity stock = stockMapper.transform(source);

        stock.setProduct(productRepository.findByIdOrThrow(source.getProduct()));

        // Return the transformed object.
        return stock;
    }

    @Override
    public Stock transform(final StockEntity source) {
        // 1. Transform the StockEntity to Stock object.
        final Stock stock = stockMapper.transform(source);

        stock.setProduct(productMapper.transform(source.getProduct()));

        // Return the transformed object.
        return stock;
    }

    @Override
    public void transform(
            final UpdateStockRequest source, final @MappingTarget StockEntity target) {

        // Transform from source to the target.
        stockMapper.transform(source, target);

        if (Objects.nonNull(source.getProduct())) {
            target.setProduct(productRepository.findByIdOrThrow(source.getProduct()));
        }
    }

    @Override
    public StockEntity transform(final UpdateStockRequest source) {

        // Transform from source to the target.
        final StockEntity stock = stockMapper.transform(source);

        if (Objects.nonNull(source.getProduct())) {
            stock.setProduct(productRepository.findByIdOrThrow(source.getProduct()));
        }
        // Return the response.
        return stock;
    }
}

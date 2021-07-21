/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.data.mapper;

import com.harik.inno.features.platform.data.mapper.decorator.StockMapperDecorator;
import com.harik.inno.features.platform.data.model.experience.stock.CreateStockRequest;
import com.harik.inno.features.platform.data.model.experience.stock.Stock;
import com.harik.inno.features.platform.data.model.experience.stock.UpdateStockRequest;
import com.harik.inno.features.platform.data.model.persistence.StockEntity;
import java.util.Collection;
import java.util.stream.Collectors;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper contract that maps / transforms data from an instance of type {@link StockEntity to {@link Stock and vice-versa.
 *
 * @author Admin
 */
@DecoratedWith(value = StockMapperDecorator.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface StockMapper {

    /**
     * This method transforms an instance of type {@link CreateStockRequest} to an instance of type
     * {@link StockEntity}.
     *
     * @param source Instance of type {@link CreateStockRequest} that needs to be transformed to
     *     {@link StockEntity}.
     * @return Instance of type {@link StockEntity}.
     */
    @Mapping(source = "product", target = "product", ignore = true)
    StockEntity transform(CreateStockRequest source);

    /**
     * This method transforms an instance of type {@link StockEntity} to an instance of type {@link
     * Stock}.
     *
     * @param source Instance of type {@link StockEntity} that needs to be transformed to {@link
     *     Stock}.
     * @return Instance of type {@link Stock}.
     */
    @Mapping(source = "product", target = "product", ignore = true)
    Stock transform(StockEntity source);

    /**
     * This method converts / transforms the provided collection of {@link StockEntity} instances to
     * a collection of instances of type {@link Stock}.
     *
     * @param source Instance of type {@link StockEntity} that needs to be transformed to {@link
     *     Stock}.
     * @return Collection of instance of type {@link Stock}.
     */
    default Collection<Stock> transformListTo(Collection<StockEntity> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
    /**
     * This method transforms an instance of type {@link UpdateStockRequest} to an instance of type
     * {@link StockEntity}.
     *
     * <p>The provided instance ({@code target}) will be updated instead of creating a new instance.
     *
     * @param source Instance of type {@link UpdateStockRequest} that needs to be transformed to
     *     {@link StockEntity}.
     * @param target Instance of type {@link StockEntity} that will be updated instead of creating
     *     and returning a new instance.
     */
    @Mapping(source = "product", target = "product", ignore = true)
    void transform(UpdateStockRequest source, @MappingTarget StockEntity target);

    /**
     * This method transforms an instance of type {@link UpdateStockRequest} to an instance of type
     * {@link StockEntity}.
     *
     * @param source Instance of type {@link UpdateStockRequest} that needs to be transformed to
     *     {@link StockEntity}.
     * @return Instance of type {@link StockEntity}.
     */
    @Mapping(source = "product", target = "product", ignore = true)
    StockEntity transform(UpdateStockRequest source);

    /**
     * This method converts / transforms the provided collection of {@link UpdateStockRequest}
     * instances to a collection of instances of type {@link StockEntity}.
     *
     * @param source Instance of type {@link UpdateStockRequest} that needs to be transformed to
     *     {@link StockEntity}.
     * @return Instance of type {@link StockEntity}.
     */
    default Collection<StockEntity> transformList(Collection<UpdateStockRequest> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
}

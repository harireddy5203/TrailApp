/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.data.mapper;

import com.harik.inno.features.platform.data.model.experience.product.CreateProductRequest;
import com.harik.inno.features.platform.data.model.experience.product.Product;
import com.harik.inno.features.platform.data.model.experience.product.UpdateProductRequest;
import com.harik.inno.features.platform.data.model.persistence.ProductEntity;
import java.util.Collection;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper contract that maps / transforms data from an instance of type {@link ProductEntity to {@link Product and vice-versa.
 *
 * @author Admin
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface ProductMapper {

    /**
     * This method transforms an instance of type {@link CreateProductRequest} to an instance of
     * type {@link ProductEntity}.
     *
     * @param source Instance of type {@link CreateProductRequest} that needs to be transformed to
     *     {@link ProductEntity}.
     * @return Instance of type {@link ProductEntity}.
     */
    ProductEntity transform(CreateProductRequest source);

    /**
     * This method transforms an instance of type {@link ProductEntity} to an instance of type
     * {@link Product}.
     *
     * @param source Instance of type {@link ProductEntity} that needs to be transformed to {@link
     *     Product}.
     * @return Instance of type {@link Product}.
     */
    Product transform(ProductEntity source);

    /**
     * This method converts / transforms the provided collection of {@link ProductEntity} instances
     * to a collection of instances of type {@link Product}.
     *
     * @param source Instance of type {@link ProductEntity} that needs to be transformed to {@link
     *     Product}.
     * @return Collection of instance of type {@link Product}.
     */
    default Collection<Product> transformListTo(Collection<ProductEntity> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
    /**
     * This method transforms an instance of type {@link UpdateProductRequest} to an instance of
     * type {@link ProductEntity}.
     *
     * <p>The provided instance ({@code target}) will be updated instead of creating a new instance.
     *
     * @param source Instance of type {@link UpdateProductRequest} that needs to be transformed to
     *     {@link ProductEntity}.
     * @param target Instance of type {@link ProductEntity} that will be updated instead of creating
     *     and returning a new instance.
     */
    void transform(UpdateProductRequest source, @MappingTarget ProductEntity target);

    /**
     * This method transforms an instance of type {@link UpdateProductRequest} to an instance of
     * type {@link ProductEntity}.
     *
     * @param source Instance of type {@link UpdateProductRequest} that needs to be transformed to
     *     {@link ProductEntity}.
     * @return Instance of type {@link ProductEntity}.
     */
    ProductEntity transform(UpdateProductRequest source);

    /**
     * This method converts / transforms the provided collection of {@link UpdateProductRequest}
     * instances to a collection of instances of type {@link ProductEntity}.
     *
     * @param source Instance of type {@link UpdateProductRequest} that needs to be transformed to
     *     {@link ProductEntity}.
     * @return Instance of type {@link ProductEntity}.
     */
    default Collection<ProductEntity> transformList(Collection<UpdateProductRequest> source) {
        return source.stream().map(this::transform).collect(Collectors.toSet());
    }
}

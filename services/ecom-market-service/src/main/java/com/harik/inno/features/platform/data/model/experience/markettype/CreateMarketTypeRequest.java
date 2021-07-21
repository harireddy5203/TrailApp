/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.data.model.experience.markettype;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Implementation of an experience model that is meant to be used by the API Layer for communication
 * either with the front-end or to the service-layer.
 *
 * @author Admin
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class CreateMarketTypeRequest {
    /** Reference to the typename. */
    @NotBlank(message = "market.type.typename.not.blank.message")
    @Size(max = 20, message = "market.type.typename.size.message")
    private String typename;

    /** Reference to the column_1. */
    @NotNull(message = "market.type.column1.not.null.message")
    private Integer column1;
}

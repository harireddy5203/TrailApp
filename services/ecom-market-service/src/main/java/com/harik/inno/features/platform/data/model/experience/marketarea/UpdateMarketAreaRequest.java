/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.data.model.experience.marketarea;

import java.util.Collection;
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
public class UpdateMarketAreaRequest {
    /** Reference to the id. */
    @NotNull(message = "market.area.id.not.null.message")
    private Integer id;

    /** Reference to the areaname. */
    @NotBlank(message = "market.area.areaname.not.blank.message")
    @Size(max = 20, message = "market.area.areaname.size.message")
    private String areaname;

    /** Reference to the zip. */
    @NotNull(message = "market.area.zip.not.null.message")
    private Integer zip;

    /** Reference to the markettypes. */
    @NotNull(message = "market.area.markettypes.not.null.message")
    private Collection<Integer> markettypes;
}

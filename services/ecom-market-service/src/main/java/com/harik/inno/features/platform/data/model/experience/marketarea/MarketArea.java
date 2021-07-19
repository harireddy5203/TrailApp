/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.data.model.experience.marketarea;

import com.harik.inno.features.platform.data.model.experience.markettype.MarketType;
import java.util.Collection;
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
public class MarketArea {
    /** Reference to the id. */
    private Integer id;

    /** Reference to the areaname. */
    private String areaname;

    /** Reference to the zip. */
    private Integer zip;

    /** Reference to the markettypes. */
    private Collection<MarketType> markettypes;
}

/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.data.repository;

import com.harik.inno.commons.data.jpa.repository.ExtendedJpaRepository;
import com.harik.inno.features.platform.data.model.persistence.MarketAreaEntity;
import org.springframework.stereotype.Repository;

/**
 * Repository interface to handle the operations pertaining to domain models of type
 * "MarketAreaEntity".
 *
 * @author Admin
 */
@Repository
public interface MarketAreaRepository extends ExtendedJpaRepository<MarketAreaEntity, Integer> {
    // Any custom methods can be added here.
}

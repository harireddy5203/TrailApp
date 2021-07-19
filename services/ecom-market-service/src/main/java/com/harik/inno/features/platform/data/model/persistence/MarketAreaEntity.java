/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.data.model.persistence;

import com.harik.inno.commons.data.jpa.persistence.AbstractIdGeneratedEntity;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Implementation that maps the "market_area" table in the database to an entity in the ORM world.
 *
 * @author Admin
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@Table(name = "market_area")
@Entity
public class MarketAreaEntity extends AbstractIdGeneratedEntity<Integer> {

    /** Reference to the areaname. */
    @Column(name = "areaname", nullable = false, length = 20)
    private String areaname;

    /** Reference to the zip. */
    @Column(name = "zip", nullable = false)
    private Integer zip;

    /** Reference to the markettypes. */
    @OneToMany
    @JoinColumn(name = "market_area_id", referencedColumnName = "id")
    private Collection<MarketTypeEntity> markettypes;
}

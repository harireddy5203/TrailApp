/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.data.model.persistence;

import com.harik.inno.commons.data.jpa.persistence.AbstractPrimaryKeyEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Implementation that maps the "role" table in the database to an entity in the ORM world.
 *
 * @author Admin
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@Table(name = "role")
@Entity
public class RoleEntity extends AbstractPrimaryKeyEntity<String> {

    /** Reference to the description. */
    @Column(name = "description", length = 128)
    private String description;

    /** Reference to the name. */
    @Column(name = "name", nullable = false, length = 64)
    private String name;

    /** Reference to the id. */
    @Column(name = "id", unique = true, nullable = false, length = 36)
    @Id
    private String id;
}

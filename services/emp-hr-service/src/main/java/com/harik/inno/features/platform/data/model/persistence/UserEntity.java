/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.data.model.persistence;

import com.harik.inno.commons.data.jpa.persistence.AbstractPrimaryKeyEntity;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Implementation that maps the "user" table in the database to an entity in the ORM world.
 *
 * @author Admin
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user")
@Entity
public class UserEntity extends AbstractPrimaryKeyEntity<Integer> {

    /** Reference to the roles. */
    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Collection<RoleEntity> roles;
    /** Reference to the id. */
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    /** Reference to the username. */
    @Column(name = "username", nullable = false, length = 32)
    private String username;

    /** Reference to the password. */
    @Column(name = "password", nullable = false)
    private String password;
}

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
 * Implementation that maps the "employee" table in the database to an entity in the ORM world.
 *
 * @author Admin
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@Table(name = "employee")
@Entity
public class EmployeeEntity extends AbstractPrimaryKeyEntity<Integer> {

    /** Reference to the deleted_ts. */
    @Column(name = "deleted_ts")
    private Long deletedTs;

    /** Reference to the email. */
    @Column(name = "email", nullable = false, length = 32)
    private String email;

    /** Reference to the id. */
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    /** Reference to the designations. */
    @ManyToMany
    @JoinTable(
            name = "emp_desi",
            joinColumns = {@JoinColumn(name = "emp_id")},
            inverseJoinColumns = {@JoinColumn(name = "des_id")})
    private Collection<DesignationEntity> designations;

    /** Reference to the deleted. */
    @Column(name = "deleted")
    private Boolean deleted;

    /** Reference to the password. */
    @Column(name = "password", nullable = false)
    private String password;
}

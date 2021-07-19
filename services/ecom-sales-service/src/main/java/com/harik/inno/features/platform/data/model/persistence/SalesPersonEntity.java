/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.data.model.persistence;

import com.harik.inno.commons.data.jpa.persistence.AbstractUUIDGeneratedEntity;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Implementation that maps the "sales_person" table in the database to an entity in the ORM world.
 *
 * @author Admin
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@Table(name = "sales_person")
@Entity
public class SalesPersonEntity extends AbstractUUIDGeneratedEntity {

    /** Reference to the name. */
    @Column(name = "name", nullable = false, length = 20)
    private String name;

    /** Reference to the jod. */
    @Column(name = "jod", nullable = false)
    private Date jod;

    /** Reference to the targets. */
    @Column(name = "targets", nullable = false, precision = 0, scale = 0)
    private Double targets;

    /** Reference to the products. */
    @ManyToMany
    @JoinTable(
            name = "sales_person_product",
            joinColumns = {@JoinColumn(name = "sales_person_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_id")})
    private Collection<ProductEntity> products;

    /** Reference to the clients. */
    @OneToMany
    @JoinColumn(name = "sales_person_id", referencedColumnName = "id")
    private Collection<ClientEntity> clients;
}

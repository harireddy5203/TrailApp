/*
 * Copyright (c) 2021 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of TrailApp.
 *
 * TrailApp project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.harik.inno.commons.data.jpa.persistence;

import com.harik.inno.commons.data.persistence.ISoftDeletable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Abstract implementation of an entity that supports soft-deletion functionality for an entity.
 *
 *
 * @author Admin
 */
@ToString(callSuper = true, of = {})
@EqualsAndHashCode(callSuper = true, of = {})
@Getter
@Setter
@MappedSuperclass
public abstract class AbstractSoftDeletablePrimaryKeyEntity<ID extends Serializable> extends AbstractPrimaryKeyEntity<ID> implements ISoftDeletable {
    /** Boolean to indicate if this record is deleted or not. */
    @Column(name = "deleted")
    private boolean deleted;

    /** Timestamp in epoch format indicating when the record was deleted. */
    @Column(name = "deleted_ts")
    private Long deletedTimestamp;
}
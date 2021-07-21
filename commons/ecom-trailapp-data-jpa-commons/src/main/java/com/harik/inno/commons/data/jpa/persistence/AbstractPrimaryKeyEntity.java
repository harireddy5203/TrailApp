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

import com.harik.inno.commons.data.persistence.IEntity;

import java.io.Serializable;

/**
 * Abstract implementation of an entity where the value of the primary key.
 *
 * @author Admin
 */
public abstract class AbstractPrimaryKeyEntity<ID extends Serializable> implements IEntity<ID> {

}

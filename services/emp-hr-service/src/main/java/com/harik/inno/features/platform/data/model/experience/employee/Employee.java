/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.data.model.experience.employee;

import com.harik.inno.features.platform.data.model.experience.designation.Designation;
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
public class Employee {
    /** Reference to the deleted_ts. */
    private Long deletedTs;

    /** Reference to the email. */
    private String email;

    /** Reference to the id. */
    private Integer id;

    /** Reference to the designations. */
    private Collection<Designation> designations;

    /** Reference to the deleted. */
    private Boolean deleted;

    /** Reference to the password. */
    private String password;
}

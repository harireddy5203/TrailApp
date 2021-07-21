/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.data.model.experience.employee;

import java.util.Collection;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
public class UpdateEmployeeRequest {
    /** Reference to the deleted_ts. */
    private Long deletedTs;

    /** Reference to the email. */
    @NotBlank(message = "employee.email.not.blank.message")
    @Size(max = 32, message = "employee.email.size.message")
    private String email;

    /** Reference to the id. */
    @NotNull(message = "employee.id.not.null.message")
    private Integer id;

    /** Reference to the designations. */
    @NotNull(message = "employee.designations.not.null.message")
    private Collection<String> designations;

    /** Reference to the deleted. */
    private Boolean deleted;

    /** Reference to the password. */
    @NotBlank(message = "employee.password.not.blank.message")
    @Size(max = 255, message = "employee.password.size.message")
    private String password;
}

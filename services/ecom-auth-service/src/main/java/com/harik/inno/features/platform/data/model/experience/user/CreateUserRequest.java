/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.data.model.experience.user;

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
public class CreateUserRequest {
    /** User name of the user. */
    @NotBlank(message = "user.username.not.blank.message")
    @Size(max = 32, message = "user.username.size.message")
    private String username;

    /** Password of the user. */
    @NotBlank(message = "user.password.not.blank.message")
    private String password;

    /** Reference to the roles. */
    @NotNull(message = "user.roles.not.null.message")
    private Collection<String> roles;
}

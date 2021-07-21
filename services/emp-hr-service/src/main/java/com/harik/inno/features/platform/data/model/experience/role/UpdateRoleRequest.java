/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.data.model.experience.role;

import javax.validation.constraints.NotBlank;
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
public class UpdateRoleRequest {
    /** Reference to the description. */
    @Size(max = 128, message = "role.description.size.message")
    private String description;

    /** Reference to the name. */
    @NotBlank(message = "role.name.not.blank.message")
    @Size(max = 64, message = "role.name.size.message")
    private String name;

    /** Reference to the id. */
    @NotBlank(message = "role.id.not.blank.message")
    private String id;
}

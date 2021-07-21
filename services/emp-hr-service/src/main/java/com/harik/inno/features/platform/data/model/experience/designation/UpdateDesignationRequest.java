/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.data.model.experience.designation;

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
public class UpdateDesignationRequest {
    /** Reference to the description. */
    @Size(max = 128, message = "designation.description.size.message")
    private String description;

    /** Reference to the deleted_ts. */
    private Long deletedTs;

    /** Reference to the name. */
    @NotBlank(message = "designation.name.not.blank.message")
    @Size(max = 64, message = "designation.name.size.message")
    private String name;

    /** Reference to the id. */
    @NotBlank(message = "designation.id.not.blank.message")
    private String id;

    /** Reference to the deleted. */
    private Boolean deleted;
}

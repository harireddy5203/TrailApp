/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.data.model.experience.salesperson;

import java.util.Collection;
import java.util.Date;
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
public class CreateSalesPersonRequest {
    /** Reference to the name. */
    @NotBlank(message = "sales.person.name.not.blank.message")
    @Size(max = 20, message = "sales.person.name.size.message")
    private String name;

    /** Reference to the jod. */
    @NotNull(message = "sales.person.jod.not.null.message")
    private Date jod;

    /** Reference to the targets. */
    @NotNull(message = "sales.person.targets.not.null.message")
    private Double targets;

    /** Reference to the products. */
    @NotNull(message = "sales.person.products.not.null.message")
    private Collection<Integer> products;

    /** Reference to the clients. */
    @NotNull(message = "sales.person.clients.not.null.message")
    private Collection<Integer> clients;
}

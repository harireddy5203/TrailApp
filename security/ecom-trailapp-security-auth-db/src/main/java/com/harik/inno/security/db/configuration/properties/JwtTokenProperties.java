/*
 * Copyright (c) 2021 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of TrailApp.
 *
 * TrailApp project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.harik.inno.security.db.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * Class that is meant to capture the properties that are meant for configuring JWT token details.
 *
 * @author Admin
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "app.security.auth.token")
public class JwtTokenProperties {
    /** Secret that is used for signing the token. */
    private String secret;

    /** JWT Token expiration interval in milliseconds. Defaulted to 7 days. */
    private Integer expirationIntervalInMillis = 7 * 24 * 60 * 60 * 1000;
}

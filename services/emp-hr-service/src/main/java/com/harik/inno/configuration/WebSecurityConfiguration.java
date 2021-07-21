/*
 * Copyright (c) 2021 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of emp-hr-service.
 *
 * emp-hr-service project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.harik.inno.configuration;

import com.harik.inno.commons.web.filter.SecurityAuthenticationFilter;
import com.harik.inno.security.token.JwtTokenProvider;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import lombok.extern.slf4j.Slf4j;

import com.harik.inno.commons.web.security.AbstractWebSecurityConfigurerAdapter;
import com.harik.inno.security.properties.ApplicationSecurityProperties;


/**
 * Configuration class responsible to configure the security aspects for the microservice / application in
 * consideration.
 *
 * @author Admin
 */
@Slf4j
@Order(SecurityProperties.BASIC_AUTH_ORDER - 2)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class WebSecurityConfiguration extends AbstractWebSecurityConfigurerAdapter {
    /**
     * Constructor.
     *
     * @param applicationSecurityProperties
     *         Configurable properties instance of type {@link ApplicationSecurityProperties}.
     * @param securityAuthenticationFilter
     *         Filter instance of type {@link SecurityAuthenticationFilter}.
     */
    public WebSecurityConfiguration(final ApplicationSecurityProperties applicationSecurityProperties,
                                    final SecurityAuthenticationFilter securityAuthenticationFilter, final JwtTokenProvider jwtTokenProvider) {
        super(applicationSecurityProperties, securityAuthenticationFilter,jwtTokenProvider);
    }
}
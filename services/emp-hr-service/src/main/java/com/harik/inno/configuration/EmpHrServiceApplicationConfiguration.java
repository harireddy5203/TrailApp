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

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import com.harik.inno.commons.web.configuration.WebConfiguration;
import com.harik.inno.commons.web.configuration.properties.ApiDocumentationSettings;
import com.harik.inno.security.properties.ApplicationSecurityProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Configuration class responsible to configure the beans, etc. for the microservice that contains this configuration.
 *
 * @author Admin
 */
@Import(value = {WebConfiguration.class})
@PropertySource("classpath:/l10n/ValidationMessages.properties")
@PropertySource("classpath:/l10n/error_messages.properties")
@EnableJpaRepositories(basePackages = {"com.harik.inno.features.platform.data.repository"})
@EntityScan(basePackages = {"com.harik.inno.features.platform.data.model.persistence"})
@Configuration
public class EmpHrServiceApplicationConfiguration {
    /**
     * Bean that captures the API documentation settings.
     *
     * @return Bean instance of type {@link ApiDocumentationSettings}.
     */
    @ConfigurationProperties(prefix = "com.harik.configuration")
    @Bean
    public ApiDocumentationSettings apiDocumentationSettings() {
        return new ApiDocumentationSettings();
    }

    /**
     * Bean that captures the application security settings.
     *
     * @return Bean instance of type {@link ApplicationSecurityProperties}.
     */
    @ConfigurationProperties(prefix = "com.harik.configuration.security")
    @Bean
    public ApplicationSecurityProperties applicationSecurityProperties() {
        return new ApplicationSecurityProperties();
    }
}

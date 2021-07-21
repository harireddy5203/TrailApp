/*
 * Copyright (c) 2021 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of TrailApp.
 *
 * TrailApp project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.harik.inno.security;

import com.harik.inno.security.db.EnableDBAuthentication;
import com.harik.inno.security.properties.ApplicationSecurityProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Main application class that is responsible to start the db auth service and exposes the functionality over the specified
 * port.
 *
 * @author Admin
 */
@EnableConfigurationProperties(value = {ApplicationSecurityProperties.class})
@EnableDBAuthentication
@EnableDiscoveryClient
@SpringBootApplication
public class AuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationApplication.class, args);
	}

}
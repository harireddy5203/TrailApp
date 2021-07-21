/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.web.client;

import com.harik.inno.commons.web.api.client.DefaultRestClient;
import com.harik.inno.commons.web.api.client.RestApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestClientException;

/**
 * Service implementation that provides CRUD (Create, Read, Update, Delete) capabilities for entities of type {@link
 *
 *
 * @author Admin
 */
@Slf4j
@Validated
@Service
public class DefaultClient {

    /** Reference to the DefaultRestClient */
    private final DefaultRestClient restClient;

    /**
     * Constructor.
     *
     * @param restClient Rest template instance of type {@link DefaultRestClient}.
     */
    public DefaultClient(final DefaultRestClient restClient) {
        this.restClient = restClient;
    }

    /**
     * This method invokes rest api service of findWeatherrepo
     *
     * @param q Request parameter
     * @param appid Request parameter
     * @return Returns rest api definition of type {@link ResponseEntity} with target api response
     *     status code and body.
     */
    public ResponseEntity<Object> findWeatherrepo(final String q, final String appid) {

        RestApi restApi = new RestApi();

        // 1.Set request method
        restApi.requestMethod(HttpMethod.GET);

        // 2.Set request url
        restApi.url("https://api.openweathermap.org/data/2.5/weather");

        restApi.requestParameter("q", q);
        restApi.requestParameter("appid", appid);

        /* invoke the REST API*/
        return invokeApi(restApi);
    }

    /**
     * This method attempts invoke target RestApi with the given {@link RestApi} object.
     *
     * @param restApi Details of rest api.
     * @return Returns rest service response of type {@link ResponseEntity} with target api response
     *     status code and body.
     */
    private ResponseEntity<Object> invokeApi(final RestApi restApi) {

        final ResponseEntity<Object> response;
        try {
            // Invoke API
            response = restClient.invoke(restApi, new ParameterizedTypeReference<Object>() {});
        } catch (RestClientException e) {
            DefaultClient.LOGGER.debug(
                    "Exception occurred while invoking target api, Exception is: "
                            + e.getMessage());
            throw e;
        }

        return response;
    }
}

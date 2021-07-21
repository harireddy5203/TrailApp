/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.security.db.model.experience;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.harik.inno.security.db.model.persistence.UserPrincipalEntity;
import com.harik.inno.security.userdetails.UserPrincipal;
import java.util.Collection;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * A data transfer object that returns the details of the user.
 *
 * @author {runtimeModel.authorName}
 */
@ToString(of = {"userName"})
@EqualsAndHashCode(of = {"id"})
@Builder
@Data
public class UserProfile extends AbstractUserProfile<Integer> {
    /** GrantedAuthority format as required by Spring Security. */
    private static final String GRANTED_AUTHORITY_FORMAT = "ROLE_{0}";

    /** Unique identifier of the user. */
    private Integer id;

    /** User name of the user. */
    private String username;

    /** Reference to the roles. */
    private Collection<String> roles;

    /**
     * This method transforms the provided user entity object of type {@link UserPrincipalEntity} to
     * an instance of type {@link UserProfile}.
     *
     * @param user User entity of type {@link UserPrincipalEntity} that needs to be transformed to
     *     {@link UserProfile}.
     * @return Instance of type {@link UserProfile}.
     */
    @JsonIgnore
    public static UserProfile from(final UserPrincipalEntity user) {
        return UserProfile.builder()
                .id(user.getId())
                .username(user.getUsername())
                .roles(user.getAssignedRoleNames())
                .build();
    }

    /**
     * This method transforms the provided user principal object of type {@link UserPrincipal} to an
     * instance of type {@link UserProfile}.
     *
     * @param user User principal of type {@link UserPrincipal} that needs to be transformed to
     *     {@link UserProfile}.
     * @return Instance of type {@link UserProfile}.
     */
    @JsonIgnore
    public static UserProfile from(final UserPrincipal user) {
        return UserProfile.builder()
                .id(user.getId())
                .username(user.getUsername())
                .roles(user.getAssignedRoles())
                .build();
    }
}

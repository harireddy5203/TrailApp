/*
 * Copyright (c) 2020-2021 Innominds inc. All Rights Reserved. This software is \n * confidential and proprietary information of Innominds inc. You shall not disclose \n * Confidential Information and shall use it only in accordance with the terms \n *
 */
package com.harik.inno.features.platform.web.service;

import com.harik.inno.commons.data.utils.PageUtils;
import com.harik.inno.commons.instrumentation.Instrument;
import com.harik.inno.features.platform.data.mapper.UserMapper;
import com.harik.inno.features.platform.data.model.experience.user.CreateUserRequest;
import com.harik.inno.features.platform.data.model.experience.user.UpdateUserRequest;
import com.harik.inno.features.platform.data.model.experience.user.User;
import com.harik.inno.features.platform.data.model.persistence.RoleEntity;
import com.harik.inno.features.platform.data.model.persistence.UserEntity;
import com.harik.inno.features.platform.data.repository.RoleRepository;
import com.harik.inno.features.platform.data.repository.UserRepository;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * Service implementation that provides CRUD (Create, Read, Update, Delete) capabilities for
 * entities of type {@link UserEntity}.
 *
 * @author Admin
 */
@Slf4j
@Validated
@Service
public class UserService {

    /** Repository implementation of type {@link UserRepository}. */
    private final UserRepository userRepository;

    /** Mapper implementation of type {@link UserMapper} to transform between different types. */
    private final UserMapper userMapper;

    /** Repository implementation of type {@link RoleRepository}. */
    private final RoleRepository roleRepository;

    /**
     * Constructor.
     *
     * @param userRepository Repository instance of type {@link UserRepository}.
     * @param userMapper Mapper instance of type {@link UserMapper}.
     * @param roleRepository Repository instance of type {@link RoleRepository}.
     */
    public UserService(
            final UserRepository userRepository,
            final UserMapper userMapper,
            final RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
    }

    /**
     * This method attempts to create an instance of type {@link UserEntity} in the system based on
     * the provided payload.
     *
     * @param payload Payload containing the details required to create an instance of type {@link
     *     UserEntity}.
     * @return An experience model of type {@link User} that represents the newly created entity of
     *     type {@link UserEntity}.
     */
    @Instrument
    @Transactional
    public User createUser(@Valid final CreateUserRequest payload) {
        // 1. Transform the experience model to a persistence model.
        final UserEntity userEntity = userMapper.transform(payload);

        // 2. Save the entity.
        UserService.LOGGER.debug("Saving a new instance of type - UserEntity");
        final UserEntity newInstance = userRepository.save(userEntity);

        // 3. Transform the created entity to an experience model and return it.
        return userMapper.transform(newInstance);
    }

    /**
     * This method attempts to update an existing instance of type {@link UserEntity} using the
     * details from the provided input, which is an instance of type {@link UpdateUserRequest}.
     *
     * @param userId Unique identifier of User in the system, which needs to be updated.
     * @param payload Request payload containing the details of an existing User, which needs to be
     *     updated in the system.
     * @return A instance of type {@link User} containing the updated details.
     */
    @Instrument
    @Transactional
    public User updateUser(final Integer userId, @Valid final UpdateUserRequest payload) {
        // 1. Verify that the entity being updated truly exists.
        final UserEntity matchingInstance = userRepository.findByIdOrThrow(userId);

        // 2. Transform the experience model to a persistence model and delegate to the save(..)
        // method.
        userMapper.transform(payload, matchingInstance);

        // 3. Save the entity
        UserService.LOGGER.debug("Saving the updated entity - UserEntity");
        final UserEntity updatedInstance = userRepository.save(matchingInstance);

        // 4. Transform updated entity to output object
        return userMapper.transform(updatedInstance);
    }

    /**
     * This method attempts to find a {@link UserEntity} whose unique identifier matches the
     * provided identifier.
     *
     * @param userId Unique identifier of User in the system, whose details have to be retrieved.
     * @return Matching entity of type {@link User} if found, else returns null.
     */
    @Instrument
    @Transactional(readOnly = true)
    public User findUser(final Integer userId) {
        // 1. Find a matching entity and throw an exception if not found.
        final UserEntity matchingInstance = userRepository.findByIdOrThrow(userId);

        // 2. Transform the matching entity to the desired output.
        return userMapper.transform(matchingInstance);
    }

    /**
     * This method attempts to find instances of type UserEntity based on the provided page
     * definition. If the page definition is null or contains invalid values, this method attempts
     * to return the data for the first page (i.e. page index is 0) with a default page size as 20.
     *
     * @return Returns a page of objects based on the provided page definition. Each object in the
     *     returned page is an instance of type {@link User}.
     */
    @Instrument
    @Transactional(readOnly = true)
    public Page<User> findAllUsers(final Pageable page) {
        // 1. Validate the provided pagination settings.
        final Pageable pageSettings = PageUtils.validateAndUpdatePaginationConfiguration(page);
        UserService.LOGGER.debug(
                "Page settings: page number {}, page size {}",
                pageSettings.getPageNumber(),
                pageSettings.getPageSize());

        // 2. Delegate to the super class method to find the data (page settings are verified in
        // that method).
        final Page<UserEntity> pageData = userRepository.findAll(pageSettings);

        // 3. If the page has data, transform each element into target type.
        if (pageData.hasContent()) {
            final List<User> dataToReturn =
                    pageData.getContent().stream()
                            .map(userMapper::transform)
                            .collect(Collectors.toList());

            return PageUtils.createPage(dataToReturn, pageSettings, pageData.getTotalElements());
        }

        // Return empty page.
        return PageUtils.emptyPage(pageSettings);
    }

    /**
     * This method attempts to delete an existing instance of type {@link UserEntity} whose unique
     * identifier matches the provided identifier.
     *
     * @param userId Unique identifier of User in the system, which needs to be deleted.
     * @return Unique identifier of the instance of type UserEntity that was deleted.
     */
    @Instrument
    @Transactional
    public Integer deleteUser(final Integer userId) {
        // 1. Delegate to our repository method to handle the deletion.
        return userRepository.deleteOne(userId);
    }

    /**
     * This method attempts to update an existing instance of type {@link UserEntity} by adding
     * roles.
     *
     * @param userId Unique identifier of User in the system, whose details have to be retrieved.
     * @param ids Collection of unique identifiers pertaining to role.
     * @return An instance of type {@link User} containing the updated details.
     */
    @Instrument
    @Transactional
    public User addRolesToUser(final Integer userId, final Collection<String> ids) {

        // 1. Find a matching entity and throw an exception if not found.
        final UserEntity matchingUser = userRepository.findByIdOrThrow(userId);

        // 2. Find all entities based on the given id's.
        final Iterable<RoleEntity> matchingRoles = roleRepository.findAllOrThrow(ids);

        // 3. Set the other models to matching instance.
        matchingUser.setRoles(
                StreamSupport.stream(matchingRoles.spliterator(), false)
                        .collect(Collectors.toSet()));

        // 4. Persist the updated instance.
        final UserEntity updatedUser = userRepository.save(matchingUser);

        // 5. Transform updated entity to output object.
        return userMapper.transform(updatedUser);
    }

    /**
     * This method attempts to delete some of other instances from an existing instance of type
     * {@link UserEntity} using the details from the provided input.
     *
     * @param userId Unique identifier of User in the system, whose details have to be retrieved.
     * @param ids unique identifiers pertaining to role.
     * @return Instance of type {@link User} containing the updated details.
     */
    @Instrument
    @Transactional
    public User deleteRolesFromUser(final Integer userId, final Collection<String> ids) {
        // 1. Find a matching entity and throw an exception if not found.
        final UserEntity matchingUser = userRepository.findByIdOrThrow(userId);

        // 2. Find all entities based on the given id's.
        final Iterable<RoleEntity> matchingRoles = roleRepository.findAllOrThrow(ids);

        // 3. Convert iterable to collection of entities.
        final Collection<RoleEntity> rolesToDelete =
                StreamSupport.stream(matchingRoles.spliterator(), false)
                        .collect(Collectors.toSet());

        // 4. Delete the requested entities from the matchingInstance.
        matchingUser.setRoles(
                matchingUser.getRoles().stream()
                        .filter(rolesToDelete::contains)
                        .collect(Collectors.toSet()));

        // 5. Save the updated roles to user.
        final UserEntity updatedUser = userRepository.save(matchingUser);

        // 6. Transform updated entity to output object.
        return userMapper.transform(updatedUser);
    }
}

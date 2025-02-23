/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.process.instance.infrastructure.repository;

import de.muenchen.oss.digiwf.process.instance.infrastructure.entity.ServiceInstanceAuthorizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository to perform db operation on a {@link ServiceInstanceAuthorizationEntity}
 *
 * @author externer.dl.horn
 */
public interface ProcessInstanceAuthorizationRepository extends JpaRepository<ServiceInstanceAuthorizationEntity, String> {

    List<ServiceInstanceAuthorizationEntity> findAllByUserId(String userId);

    boolean existsByUserIdAndProcessInstanceId(String userId, String processInstanceId);
}

/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package io.muenchendigital.digiwf.service.config.infrastructure.repository;

import io.muenchendigital.digiwf.service.config.infrastructure.entity.ProcessConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository to perform db operation on a {@link ProcessConfigEntity}
 *
 * @author externer.dl.horn
 */
public interface ProcessConfigRepository extends JpaRepository<ProcessConfigEntity, String> {

    Optional<ProcessConfigEntity> findByKey(String key);

    List<ProcessConfigEntity> findByKeyIn(Iterable<String> keys);
}

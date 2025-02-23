/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.filters.infrastructure.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;

/**
 * Entity representation of a persistent filter.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Filter")
@Table(name = "DWF_FILTER", indexes = {@Index(name = "IDX_DWF_FILTER_USERID", columnList = "userid_")})
public class FilterEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id_", unique = true, nullable = false, length = 36)
    private String id;

    @Column(name = "filterstring_", nullable = false)
    private String filterString;

    @Column(name = "userid_", nullable = false)
    private String userId;

    @Column(name = "pageid_", nullable = false)
    private String pageId;

}

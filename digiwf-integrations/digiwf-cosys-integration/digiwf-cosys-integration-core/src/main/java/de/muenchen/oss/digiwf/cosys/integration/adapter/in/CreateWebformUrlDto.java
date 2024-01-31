/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf.cosys.integration.adapter.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto for generating documents.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateWebformUrlDto {

    private String client;

    private String role;

    private String guid;

}

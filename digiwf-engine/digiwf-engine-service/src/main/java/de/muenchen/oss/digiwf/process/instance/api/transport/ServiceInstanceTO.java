/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.process.instance.api.transport;

import de.muenchen.oss.digiwf.process.instance.domain.model.ServiceInstance;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Transport object of the {@link ServiceInstance}
 *
 * @author externer.dl.horn
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceInstanceTO {

    /**
     * Id of the process instance.
     */
    private String id;

    /**
     * Name of the corresponding process definition.
     */
    private String definitionName;

    /**
     * Key of the corresponding process definition.
     */
    private String definitionKey;

    /**
     * Start time
     */
    private Date startTime;

    /**
     * End time
     */
    private Date endTime;

    /**
     * Status
     */
    private String status;

    /**
     * description
     */
    private String description;

}

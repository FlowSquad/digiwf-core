/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */

package de.muenchen.oss.digiwf.process.config.api.resource;

import de.muenchen.oss.digiwf.process.config.api.mapper.ProcessConfigApiMapper;
import de.muenchen.oss.digiwf.process.config.api.transport.ProcessConfigTO;
import de.muenchen.oss.digiwf.process.config.domain.model.ProcessConfig;
import de.muenchen.oss.digiwf.process.config.domain.service.ProcessConfigService;
import de.muenchen.oss.digiwf.shared.exception.ObjectNotFoundException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Rest Api to interact with the process configuration.
 *
 * @author externer.dl.horn
 */
@Validated
@RestController
@Transactional
@RequestMapping("/rest/processconfig")
@RequiredArgsConstructor
@Tag(name = "ProcessConfigurationController", description = "API to interact with the process configuration")
public class ProcessConfigurationController {

    private final ProcessConfigService processConfigService;
    private final ProcessConfigApiMapper processConfigApiMapper;

    /**
     * Create a new Config.
     *
     * @param to process config that should be created
     * @return process config
     */
    @Deprecated
    @PostMapping
    @PreAuthorize("hasAuthority(T(de.muenchen.oss.digiwf.shared.security.AuthoritiesEnum).BACKEND_DEPLOY_RESOURCE.name())")
    public ResponseEntity<ProcessConfigTO> createConfig(@RequestBody @Valid final ProcessConfigTO to) {
        val processConfig = this.processConfigService.saveProcessConfig(this.processConfigApiMapper.map(to));
        return ResponseEntity.ok(this.processConfigApiMapper.map2TO(processConfig));
    }

    /**
     * Get a process config by key
     *
     * @param configKey key of the process config
     * @return process config
     */
    @GetMapping("/{key}")
    public ResponseEntity<ProcessConfigTO> getConfig(@PathVariable("key") @NotBlank final String configKey) {
        final ProcessConfig processConfig = this.processConfigService.getProcessConfig(configKey)
                .orElseThrow(() -> new ObjectNotFoundException("Config not available"));
        return ResponseEntity.ok(this.processConfigApiMapper.map2TO(processConfig));
    }

}

package de.muenchen.oss.digiwf.deployment.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Deprecated
@Getter
@AllArgsConstructor
public enum DeploymentStatus {
    SUCCESSFUL("SUCCESSFUL"),
    FAILURE("FAILURE");

    private final String value;
}

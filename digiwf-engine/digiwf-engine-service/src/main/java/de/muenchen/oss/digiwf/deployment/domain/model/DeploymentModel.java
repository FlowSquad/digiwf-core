package de.muenchen.oss.digiwf.deployment.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Deprecated
@Getter
@Builder
@ToString
@AllArgsConstructor
public class DeploymentModel {
    private String deploymentId;
    private String versionId;
    private String target;
    private String file;
    private String artifactType;
}

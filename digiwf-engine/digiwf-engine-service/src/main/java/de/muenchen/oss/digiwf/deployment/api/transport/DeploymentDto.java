package de.muenchen.oss.digiwf.deployment.api.transport;

import lombok.*;

@Deprecated
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DeploymentDto {
    private String deploymentId;
    private String versionId;
    private String target;
    private String file;
    private String artifactType;
}

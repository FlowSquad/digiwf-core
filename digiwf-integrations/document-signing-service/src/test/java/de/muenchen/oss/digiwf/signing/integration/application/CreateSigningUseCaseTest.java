package de.muenchen.oss.digiwf.signing.integration.application;

import de.muenchen.oss.digiwf.signing.integration.application.port.in.CreateSigningInPort;
import de.muenchen.oss.digiwf.signing.integration.application.port.out.SigningServiceOutPort;
import de.muenchen.oss.digiwf.signing.integration.domain.model.SigningModel;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CreateSigningUseCaseTest {

    private final SigningServiceOutPort signingServiceOutPort = mock(SigningServiceOutPort.class);
    private final CreateSigningInPort createSigningUseCase = new CreateSigningUseCase(signingServiceOutPort);

    @Test
    void test_returns_non_empty_string() {
        // Arrange
        when(signingServiceOutPort.createSigningUrl()).thenReturn(SigningModel.builder()
                .signingHost("http://localhost:10000")
                .signingUrl("/doxiview")
                .build());

        // Act
        final SigningModel result = createSigningUseCase.createSigning("documentPath");

        // Assert
        assertThat(result)
                .isNotNull()
                .hasFieldOrPropertyWithValue("signingHost", "http://localhost:10000")
                .hasFieldOrPropertyWithValue("signingUrl", "/doxiview");
    }

}

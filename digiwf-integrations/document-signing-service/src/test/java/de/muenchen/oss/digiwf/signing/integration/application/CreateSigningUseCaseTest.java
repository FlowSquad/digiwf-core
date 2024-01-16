package de.muenchen.oss.digiwf.signing.integration.application;

import de.muenchen.oss.digiwf.signing.integration.application.port.in.CreateSigningInPort;
import de.muenchen.oss.digiwf.signing.integration.application.port.out.SigningServiceOutPort;
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
        when(signingServiceOutPort.createSigningUrl()).thenReturn("https://doxiview.com/showcase/?locale=de#de&feature=sign");

        // Act
        String result = createSigningUseCase.createSigning("documentPath");

        // Assert
        assertThat(result)
                .isEqualTo("https://doxiview.com/showcase/?locale=de#de&feature=sign");
    }

}

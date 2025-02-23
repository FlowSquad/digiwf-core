/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf.ticket.integration.configuration;

import de.muenchen.oss.digiwf.process.api.config.api.ProcessConfigApi;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFileRepository;
import de.muenchen.oss.digiwf.s3.integration.client.repository.DocumentStorageFolderRepository;
import de.muenchen.oss.digiwf.ticket.integration.adapter.out.s3.S3Adapter;
import de.muenchen.oss.digiwf.ticket.integration.adapter.out.zammad.ZammadAdapter;
import de.muenchen.oss.digiwf.ticket.integration.adapter.zammad.api.TicketsApi;
import de.muenchen.oss.digiwf.ticket.integration.application.WriteArticleUseCase;
import de.muenchen.oss.digiwf.ticket.integration.application.port.in.WriteArticleInPort;
import de.muenchen.oss.digiwf.ticket.integration.application.port.out.LoadFilePort;
import de.muenchen.oss.digiwf.ticket.integration.application.port.out.TicketOutPort;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({TicketingProperties.class, TicketingProperties.class})
public class TicketIntegrationAutoConfiguration {
    private final TicketingProperties ticketingProperties;

    @Bean
    public TicketOutPort ticketOutPort(final TicketsApi ticketsApi) {
        return new ZammadAdapter(ticketsApi);
    }

    @Bean
    public LoadFilePort loadFilePort(final DocumentStorageFileRepository documentStorageFileRepository, final DocumentStorageFolderRepository documentStorageFolderRepository, final ProcessConfigApi processConfigApi) {
        return new S3Adapter(documentStorageFileRepository, documentStorageFolderRepository, processConfigApi, ticketingProperties.getSupportedFileExtensions());
    }

    @Bean
    public WriteArticleInPort writeArticleUseCase(final TicketOutPort ticketOutPort, final LoadFilePort loadFilePort) {
        return new WriteArticleUseCase(ticketOutPort, loadFilePort);
    }

}

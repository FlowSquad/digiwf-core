package de.muenchen.oss.digiwf.message.configuration;

import de.muenchen.oss.digiwf.message.core.api.MessageApi;
import de.muenchen.oss.digiwf.message.core.impl.MessageApiImpl;
import de.muenchen.oss.digiwf.message.factory.YamlPropertySourceFactory;
import de.muenchen.oss.digiwf.message.process.api.ErrorApi;
import de.muenchen.oss.digiwf.message.process.api.ProcessApi;
import de.muenchen.oss.digiwf.message.process.impl.ErrorApiImpl;
import de.muenchen.oss.digiwf.message.process.impl.ProcessApiImpl;
import de.muenchen.oss.digiwf.message.properties.DigiwfMessageProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.messaging.Message;
import reactor.core.publisher.Sinks;

/**
 * Autoconfiguration for the digiwf-message library.
 */
@RequiredArgsConstructor
@ComponentScan(basePackages = "de.muenchen.oss.digiwf.message.infra")
@EnableConfigurationProperties(value = DigiwfMessageProperties.class)
@PropertySource(value = "classpath:digiwf-message-application.yml", factory = YamlPropertySourceFactory.class)
public class DigiwfMessageAutoConfiguration {

    private final DigiwfMessageProperties digiwfMessageProperties;
    private final Sinks.Many<Message<Object>> messageSink;

    /**
     * Creates the bean for the default Implementation of {@link MessageApi}.
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public MessageApi messageApi() {
        return new MessageApiImpl(this.messageSink);
    }

    /**
     * Creates the bean for the default Implementation of {@link ProcessApi}.
     *
     * @param messageApi the message api
     * @return the process api
     */
    @Bean
    @ConditionalOnMissingBean
    public ProcessApi processApi(final MessageApi messageApi) {
        return new ProcessApiImpl(
                messageApi,
                this.digiwfMessageProperties.getCorrelateMessageDestination(),
                this.digiwfMessageProperties.getStartProcessDestination()
        );
    }

    /**
     * Creates the bean for the default Implementation of {@link ErrorApi}.
     *
     * @param messageApi the message api
     * @return the error api
     */
    @Bean
    @ConditionalOnMissingBean
    public ErrorApi errorApi(final MessageApi messageApi) {
        return new ErrorApiImpl(
                messageApi,
                this.digiwfMessageProperties.getIncidentDestination(),
                this.digiwfMessageProperties.getBpmnErrorDestination()
        );
    }

}

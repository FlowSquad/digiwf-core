package io.muenchendigital.digiwf.message.adapter.springcloudstream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Supplier;

@Configuration
public class EventEmitterConfiguration {

    /**
     * Sink for sending messages
     *
     * @return Sink
     */
    @Bean
    public Sinks.Many<Message<Object>> sendMessageSink() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    /**
     * Supplier for sending messages
     *
     * @param sink corresponding sink
     * @return Supplier
     */
    @Bean
    public Supplier<Flux<Message<Object>>> sendMessage(final Sinks.Many<Message<Object>> sink) {
        return sink::asFlux;
    }

}

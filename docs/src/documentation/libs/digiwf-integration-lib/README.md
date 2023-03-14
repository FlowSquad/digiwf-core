# DigiWF Integration Lib

Die **DigiWF Integration Lib** stellt das Herzstück unserer Integrationen dar,
indem sie die Kommunikation mit der Engine abstrahiert, Fehlerbehandlung implementiert und eine einheitliche API für die Integrationen bereitstellt.
Dadurch können die Integrationen schnell und einfach entwickelt und gewartet werden,
da der Integrations-Developer sich nicht um die technischen Details kümmern muss, sondern sich auf die Fachlichkeit der Integration fokussieren kann.

## Verwendung

In einer Java Bean muss eine Methode mit der Annotation `@DigiwfIntegration(type = "type-mapping")` deklariert werden.
Basierend auf dem `type` Parameter der Annotation werden eingehende Nachrichten an die passende Integration weitergeleitet.

Theoretisch können mehrere Integrationen in derselben Anwendung implementiert werden, solange unterschiedliche `type` Parameter verwendet werden.

### Beispielintegration

Wir haben eine Beispielintegration erstellt, die die Entwicklung einer neuen Integration verdeutlichen soll.
Hierfür haben wir in Github folgendes Beispielprojekt erstellt: [digiwf-example-integration](https://github.com/it-at-m/digiwf-core/tree/dev/digiwf-integrations/digiwf-example-integration)

Einen Ausschnitt mit der eigentlichen Implementierung der Integration ist in nachfolgendem Code-Beispiel zu sehen.

```Java
import io.muenchendigital.digiwf.integration.adapter.shared.StreamingHeaders;
import io.muenchendigital.digiwf.integration.core.api.DigiwfIntegration;
import io.muenchendigital.digiwf.integration.core.api.TechnicalError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class ExampleIntegration {

    @DigiwfIntegration(type = "example-integration")
    public Map<String, String> exampleIntegration(final Map<String, Object> event) {
        final String processInstanceId = event.get(StreamingHeaders.DIGIWF_PROCESS_INSTANCE_ID).toString();
        final String messageName = event.get(StreamingHeaders.DIGIWF_MESSAGE_NAME).toString();

        log.info("Processing event for process instance id {} with message name {}", processInstanceId, messageName);

        if (event.get("brand").equals("BMW")) {
            // use technical errors to notify the user about a technical error
            // e.g. validation failed, incoming data could not be processed, ...
            // all other exceptions will be treated as system errors and will create incidents
            throw new TechnicalError(processInstanceId, "500", "BMW is not allowed");
        }

        return Map.of(
                "status", "success",
                "message", "Vehicle is a " + event.get("brand") + " " + event.get("model")
        );
    }

}
```

### Fehlerbehandlung

Die **DigiWF Integration Lib** stellt eine einheitliche Fehlerbehandlung für alle Integrationen bereit,
indem sie unser Fehlerbehandlungskonzept implementiert (siehe [Fehlerbehandlung Integrationen](/integrations/error-handling/)). 

#### Fachliche Fehler

Fachliche Fehler können über die `TechnicalError`-Exception geworfen werden.
Diese wird automatisch im Spring Cloud Stream Consumer der **DigiWF Integration Lib** behandelt und in die Engine zurückgesendet.

Folgendes Beispiel zeigt die Verwendung der `TechnicalError`-Exception:

```java
import io.muenchendigital.digiwf.integration.core.api.TechnicalError;

throw new TechnicalError("The instanceId of the process", "400", "Your error message");
```

Einem `TechnicalError` muss immer die `instanceId` des Prozesses übergeben werden, damit der Fehler als
fachlicher Fehler an den Prozess zurückgesendet und im Prozess behandelt werden kann.

#### Incidents

Alle weiteren Exceptions, die in der Integration auftreten und nicht behandelt werden, werden als Incident zurück an die Engine gesendet.

Möchte man einen Incident manuell auslösen, kann Beispielsweise eine `RuntimeException` geworfen werden.

```java
throw new RuntimeException("Your error message");
```

## Konfiguration

Um den Spring Cloud Stream Adapter zu verwenden, muss die **DigiWF Integration Lib** konfiguriert werden.

> Nachfolgend verwenden wir den Kafka Binder. Es selbstverständlich auch andere Binder verwendet werden.

```yaml
server:
  port: 8080
spring:
  cloud:
    stream:
      bindings:
        receiveMessages-in-0:
          group: "digiwf-integration-example"
          destination: "digiwf-example-integration"
        sendMessage-out-0:
          destination: "digiwf-example-integration"
      kafka:
        binder:
          brokers: "${KAFKA_BOOTSTRAP_SERVER:localhost}:${KAFKA_BOOTSTRAP_SERVER_PORT:29092}"
          producerProperties:
            value:
              serializer: org.springframework.kafka.support.serializer.JsonSerializer
            key:
              serializer: org.springframework.kafka.support.serializer.JsonSerializer
          consumerProperties:
            auto:
              offset:
                reset: latest
            value:
              deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
            key:
              deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
          configuration:
            security:
              protocol: "${KAFKA_SECURITY_PROTOCOL:PLAINTEXT}"
    function:
      definition: receiveMessages;sendMessage;
  kafka:
    consumer:
      properties:
        spring:
          json:
            trusted:
              packages: "*"
io:
  muenchendigital:
    digiwf:
      message:
        incidentDestination: "digiwf-example-integration-incident"
        technicalErrorDestination: "digiwf-example-integration-technical-error"
        correlateMessageDestination: "digiwf-example-integration-correlate-message"
```

Die Konfiguationen unter `spring.cloud` und `spring.kafka` müssen wie oben gezeigt gesetzt werden, um die Integration zusammen mit dem Spring Cloud Stream Adapter zu verwenden.
Wenn ein anderer Adapter verwendet wird, so müssen die für den verwendeten Adapter notwendigen Konfigurationen gesetzt werden.

> Die **DigiWF Integration Lib** verwendet unsere DigiWF Message Lib.
> Dementsprechend müssen unter `io.muenchendigital.digiwf.message` die entsprechenden Konfigurationen für die Message Lib hinterlegt werden.
> Weitere Informationen zur Message Lib gibt es [hier](/documentation/libs/digiwf-message/).

## Erweiterbarkeit

Die **DigiWF Integration Lib** ist so aufgebaut, dass sie mit verschiedenen Adaptern verwendet werden kann.
Hierfür stellt die Bibliothek die Schnittstelle `IntegrationExecuteApi` bereit, die von der autowired werden kann.
Die `IntegrationExecuteApi` stellt die Methode `execute` bereit, der der `type` sowie die Daten (`data`) übergeben werden müssen.

Intern sucht die `IntegrationExecuteApi` nach einer passenden Integration, die den übergebenen `type` unterstützt und führt diese aus.
Hierfür werden zum Anwendungsstart alle Beans gesucht, die die Annotation `@DigiwfIntegration` besitzen und registriert.
Zur Laufzeit wird dann die passende Integration anhand des `type` ausgewählt und ausgeführt.

Nachdem wir hauptsächlich Spring Cloud Stream verwenden, haben wir für Spring Cloud Stream einen eigenen Adapter implementiert, der die `IntegrationExecuteApi` verwendet.
Dieser Adapter ist ein Spring Cloud Stream Consumer, der eingehende Nachrichten an die `IntegrationExecuteApi` weiterleitet und somit die Integration ausführt.
Dieser Adapter implementiert zusätzlich unser Fehlerbehandlungskonzept.

### Eigene Adapter

Möchte man neben den von uns bereitgestellten Adaptern eigene Adapter für die Integrationen verwenden,
so muss lediglich eine Schnittstelle (z.B. ein Rest Controller, ein Async API Consumer, ...) erstellt werden,
der die eingehenden Daten an die `IntegrationExecuteApi` weiterleitet.

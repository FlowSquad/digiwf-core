# DigiWF Message

Die **DigiWF Message** Bibliothek ist eine Abstraktionsschicht, um die Kommunikation zwischen den verschiedenen Komponenten
zu vereinfachen und technische Komplexität zu reduzieren.

Die Idee hinter der **DigiWF Message** Bibliothek ist, die immer wiederkehrenden Spring Cloud Stream Konfigurationen
an einer zentralen Stelle zu lösen und eine Api bereitzustellen, um diese zu nutzen.
Dadurch muss ein Integrations-Developer nicht mehr in die Tiefe der Spring Cloud Stream Konfigurationen einsteigen und
kann sich auf die Implementierung der Integration fokussieren.

## Verwendung

Die **DigiWF Message** Bibliothek stellt die MessageApi bereit, die verwendet wird, um Nachrichten zu versenden.

Zusätzlich werden APIs für wiederkehrende Nachrichten bereitgestellt, die wiederum auf der MessageApi aufbauen.
Hierfür haben wir die ProcessApi geschaffen, die verwendet werden kann, um in DigiWF Prozesse zu starten, Messages an Prozesse zu korrelieren und Fehlerbehandlung durchzuführen.

### MessageApi

Die MessageApi stellt die Methode `sendMessage` bereit, die verwendet werden kann, um eine Nachricht an eine bestimmte *Destination* zu senden.
Eine Message besteht aus einem `payload` und `headers`.
Der Payload enthält die Daten, die übermittelt werden sollen.
Die Headers sind ein Key, Value Paar, das zusätzliche Informationen zu der Nachricht enthält.

> Bei DigiWF verwenden wir Spring Cloud Stream, um Nachrichten an Kafka (Message Broker) zu senden.
> Der Payload ist hierbei das Event, das an Kafka gesendet wird.
> Die Headers enthalten wichtige Informationen, wie beispielsweise die Prozessinstanz Id, den Type des Events, etc.

```java
@RequiredArgsConstructor
public class MessageService {
    private final MessageApi sendMessageApi;

    public void sendMessage(final Message message) {
        // send a message to the destination
        boolean success = this.sendMessageApi.sendMessage(message, "my-destination");
        System.out.println("Message sent: " + success);

        // another example with headers
        final Map<String, Object> headers = Map.of("key", "value");
        success = this.sendMessageApi.sendMessage(message, headers, "my-destination");
        System.out.println("Message sent: " + success);
    }
}
```

### ProcessApi

Die `ProcessApi`-Schnittstelle stellt Methoden zum Starten, Korrelieren und Behandeln von Incidents und fachlichen Fehlern in Prozessen bereit.
Im Hintergrund nutzt die ProcessAPI die MessageApi, um die Nachrichten an die entsprechenden Destinations zu senden.
Die Destinations für die unterschiedlichen Aktionen können über die `application.yml` konfiguriert werden.

```java
@RequiredArgsConstructor
public class ProcessService {
    private final ProcessApi processApi;

    public void sendMessages() {
        // Starten Sie einen neuen Prozess mit dem Schlüssel "meinProzess" und einigen Variablen
        processApi.startProcess("meinProzess", new HashMap<String, Object>());

        // Korrelieren Sie eine Nachricht mit der Prozessinstanz-ID und einigen Variablen
        processApi.correlateMessage("123", "meineNachricht", new HashMap<String, Object>());

        // Behandeln Sie einen Vorfall mit der Prozessinstanz-ID, dem Namen der Ursprungsnachricht und einer Fehlermeldung
        processApi.handleIncident("123", "meineNachricht", "Ein Fehler ist aufgetreten.");

        // Behandeln Sie einen technischen Fehler mit der Prozessinstanz-ID, einem Fehlercode und einer Fehlermeldung
        processApi.handleTechnicalError("123", "400", "Ein technischer Fehler ist aufgetreten.");
    }
}
```


## Konfiguration

Die **DigiWF Message** Bibliothek verwendet intern den `spring.cloud.stream.sendto.destination` Header, der ausgehende
Nachrichten automatisch an das Topic, das als Destination angegeben wurde, sendet.

Jedoch muss, damit Spring Cloud Stream Nachrichten versenden kann ein ausgehender Channel konfiguriert werden.
Hierfür empfiehlt es sich, die `spring.cloud.stream.bindings.sendMessage-out-0.destination` Property zu setzen und
unter `spring.cloud.function.definition` die Funktion `sendMessage` zu definieren.

```yaml
spring:
  cloud:
    stream:
      bindings:
        sendMessage-out-0:
          destination: "digiwf-message-scs-example"
    function:
      definition: sendMessage;
[...]
io:
  muenchendigital:
    digiwf:
      message:
        incidentDestination: "digiwf-example-integration-incident"
        technicalErrorDestination: "digiwf-example-integration-technical-error"
        correlateMessageDestination: "digiwf-example-integration-correlate-message"
        startProcessDestination: "digiwf-message-scs-example-start-process"
```

|                                                               |                                                                                   |
|---------------------------------------------------------------|-----------------------------------------------------------------------------------|
| io.muenchendigital.digiwf.message.incidentDestination         | Destination to redirect incidents to (e.g. Kafka Topic)                           |
| io.muenchendigital.digiwf.message.technicalErrorDestination   | Destination to redirect technical errors a.k.a. bpmn errors to (e.g. Kafka Topic) |
| io.muenchendigital.digiwf.message.correlateMessageDestination | Destination to send correlate messages to (e.g. Kafka Topic)                      |
| io.muenchendigital.digiwf.message.startProcessDestination     | Destination to send start process messages to (e.g. Kafka Topic)                  |



## Erweiterbarkeit

Die **DigiWF Message** Bibliothek ist so aufgebaut, dass sie mit verschiedenen Adaptern verwendet werden kann.
Hierfür stellt die Bibliothek die Schnittstelle `SendMessagePort` bereit, die von den Adaptern implementiert wird.
Dieser Schnittstelle wird ein Message Objekt sowie die Destination übergeben.
Im Adapter kann anschließend die technische Implementierung des Message versands erfolgen.

### Eigene Adapter

Möchte man neben den von uns bereitgestellten Adaptern eigene Adapter verwenden, so muss lediglich ein neuer Adapter erstellt werden,
der die Schnittstelle `SendMessagePort` implementiert und als Bean bereitgestellt wird.

- Eine Beispielimplementierung eines Adapters für Spring Cloud Stream findet sich [hier](https://github.com/it-at-m/digiwf-core/tree/dev/digiwf-libs/digiwf-message/digiwf-message-adapter-springcloudstream).
- Eine Beispielimplementierung eines eignen Adapters findet sich [hier](https://github.com/it-at-m/digiwf-core/tree/dev/digiwf-libs/digiwf-message/digiwf-message-example-simple).

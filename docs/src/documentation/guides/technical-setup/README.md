# Technisches Setup

Im Folgenden wird das technische Setup beschrieben.

## lokale Infrastruktur

Für die Entwicklung wird eine lokale Infrastruktur im _stack_ Ordner bereitgestellt.

### Voraussetzungen

Folgende Programme / Tools müssen installiert sein:

* IDE welche Java, TypeScript und VueJs unterstützt (IntelliJ Idea, VSCode)
* Maven
* NodeJs
* OpenJDK 11
* Docker

In der Hostdatei des Computers muss keycloak als Hostname hinzugefügt wurden sein.

Dateipfad Linux/Mac: /etc/hosts
Dateipfad Windows: C:\Windows\System32\Drivers\etc\hosts

Zeile `127.0.0.1 localhost` muss zu `127.0.0.1 localhost keycloak` geändert werden.

### Komponenten der Infrastruktur

Die lokale Infrastruktur ist mittels docker-compose aufgesetzt.

In der folgenden Abbildung ist der grundlegende Aufbau zu sehen:

![grundlegender Aufbau des lokalen Stacks](~@source/images/platform/guides/technical-setup/docker-setup.png)

Dabei werden folgende Bausteine verwendet:

* [PostgreSQL](https://www.postgresql.org/)
* [Keycloak](https://www.keycloak.org/)
* [Keycloak Migration](https://github.com/mayope/keycloakmigration)
* [Apache Kafka](https://kafka.apache.org/)
* [Nginx](https://www.nginx.com/)

### Mögliche Szenarien zum Starten der Infrastruktur

Aktuell gibt es zwei mögliche Szenarien.

#### Szenario 1: lokale Infrastruktur starten um Tasklist Backend und Frontend zu entwickeln

Das erste Szenario ist für die Entwicklung der Tasklist (aktuelles Wording: Engine). Dabei wird das Docker-Compose Projekt so gestartet, dass die notwendigen Servies mit der Tasklist-backend Jar (aktuelles Wording: digiwf-engine) und dem Vite Server für das Tasklist Frontend kommunizieren können.

Dazu in der `stack/docker-compose.yaml` die Konfiguration des Api Gateways (Servicename: digiwf-gateway) anpassen.

Environments: 

```
SPRING_PROFILES_ACTIVE: local
```

Danach das Docker Compose Projekt starten. 
Dazu im Ordner _stack_ ausführen:

```docker compose up -d```

Danach sollte die Ausgabe von `docker ps` ungefähr wie folgt aussehen:

![Ausgabe von docker ps mit allen gestarteten Services](~@source/images/platform/guides/technical-setup/docker-ps-output.png)

Wenn das Api Gateway nicht hochgefahren ist, noch einmal `docker compose up -d` ausführen.

Danach startet man das Tasklist Backend (EngineServiceApplication).
Dazu startet man dieses mit folgenden Profilen: local, streaming, no-ldap
Zusätzlich bindet man die .env Datei aus dem Stack Ordner ein (Dafür kann man das Idea Plugin [EnvFile](https://plugins.jetbrains.com/plugin/7861-envfile) nutzen)

Ist das Backend erfolgreich gestartet, startet man noch das Frontend. (`npm run serve:tasklist` im _digiwf-apps_ Ordner)

Danach im Browser [http://localhost:8082](http://localhost:8082) aufrufen. Damit wird man auf das Api Gateway geleitet.

Es sollte eine Anmeldeseite erscheinen, welche von Keycloak bereitgestellt wird.

Dort meldet man sich mit dem Nutzername _johndoe_ und dem Passwort _test_ an.
Bei erfolgreichem Login bekommt man eine 500 zurück.  

Danach wechselt man auf [http://localhost:8081](http://localhost:8081). Man sollte jetzt das Frontend sehen. Alle Netzwerkrequests sollten erfolgreich beantwortet werden können.

### Szenario 2: lokale Infrastruktur starten, um Tasklist-Frontend in Docker Containern zu betreiben

In der `stack/docker-compose.yaml` muss die Konfiguration des Api Gateways (Servicename: digiwf-gateway) angepasst werden:

Environments:
```
SPRING_PROFILES_ACTIVE: local, docker
```

Danach im Ordner _stack_ ausführen:

```docker compose --profile tasklist-frotend up -d```

Zusätzlich startet man das Tasklist Backend (EngineServiceApplication).
Dazu startet man dieses mit folgenden Profilen: local, streaming, no-ldap
Zusätzlich bindet man die .env Datei aus dem Stack Ordner ein (Dafür kann man das Idea Plugin [EnvFile](https://plugins.jetbrains.com/plugin/7861-envfile) nutzen)


Anschließend sollte man beim Aufruf von [http://localhost:8082](http://localhost:8082) auf die Keycloak Loginmaske weitergeleitet werden.
Dort meldet man sich mit dem Nutzername _johndoe_ und dem Passwort _test_ an.

Bei erfolgreicher Anmeldung ist das Digiwf Tasklist Frontend sehen. Alle Netzwerkrequests sollten ordnungsgemäß durchgeführt werden.

### Komponenten

Alle Komponenten sind mit einem Dockernetzwerk miteinander verbunden. Zusätzlich werden bei einigen Services Port forwarding angwendet, um von der Hostmaschine direkt auf die Container zuzugreifen. 

#### Keycloak - Identity provider

Verwaltet Nutzer und Gruppen.
Der dazu verwendete Realm lautet: P82.
Keycloak stellt OpenId / OAuth2 Funktionalität zur Verfügung. 

Angelegter Nutzer: 

Nutzername: johndoe

Passwort: test

Keycloak nutzt noch eine PostgreSQL DB und Keycloak Migration zur Erstellung des Realms.

#### Kafka - Message Bus

Wird aktuell nur zur Anbindung der DigiWF Engine genutzt.

### Api Gateway

Verwaltet für das Frontend Sessions und hält die JWT Tokens im Speicher.

Verbindet sich mit Keycloak für den Login / die Erneuerung der Access Tokens. 

Tauscht bei jedem, vom Frontend kommenden, Netzwerkrequest die Session gegen den Accesstoken aus und leitet den Requests weiter an das jeweilige Backend (aktuell nur DigiWFEngineService) weiter.

### PostgreSQL

Datenbank für DigiWFEngine und DigiWFTasklist.

### Mailhog

Mail Server für DigiWFEngineService.

### Minio

S3-kompatibler ObjectStorage für DigiWFEngineService.

## DigiWFEngine

Die DigiWF Engine ist eine Camunda Engine, welche um einige Funktionalitäten erweitert wurde.
Die DigiWF Engine kann mit dem Befehl `mvn install` gebaut und anschließend gestartet werden.

### Camunda Cockpit

Die DigiWF Engine kann direkt mit einem Camunda Cockpit gestartet werden.
Hierfür muss das Profil `camunda-ce` (bzw. `camunda-ee` für die Enterprise Variant) verwendet werden.

```bash
# community edition
mvn install -Pcamunda-ce
# enterprise edition
mvn install -Pcamunda-ee
```

Ggf. muss das Projekt in der IDE neu importiert werden, damit das Profil richtig erkannt wird.

Anschließend kann die Engine gestartet werden und im Browser kann das Cockpit unter [http://localhost:39146/camunda/app/](http://localhost:39146/camunda/app/) aufgerufen werden.

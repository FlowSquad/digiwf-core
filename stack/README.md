# Stack

For local development you can use the following tools:

- **SSO**: Keycloak
- **Event Bus**: Kafka (and Zookeeper)
- **S3 Storage**: [Minio](https://min.io/docs/minio/linux/index.html)
- **Email Server**: [Mailhog](https://github.com/mailhog/MailHog)
- **SSO**: [Keycloak](https://www.keycloak.org/)
- **DB**: Postgresql database 

## Docker

Use docker compose to start the infrastructure components:

1. Add profiles to host file:
    ```bash
    # add profiles to host
    # On Mac/Linux it is located in `/etc/hosts` on Win `C:\Windows\System32\Drivers\etc\hosts`
    127.0.0.1 localhost keycloak
    ```

2. Start docker environment:
    ```bash
    # start infrastructure (for local development)
    docker compose up -d

    # with tasklist
    docker compose --profile tasklist-frontend up -d
    ```
3. In Browser:

   Open `http://localhost:8082/` and login with `johndoe` and `test`

> Also checkout our documentation [https://digiwf.oss.muenchen.de/documentation/guides/technical-setup/#lokale-infrastruktur](https://digiwf.oss.muenchen.de/documentation/guides/technical-setup/#lokale-infrastruktur).

## Running Process Engine Backend

To start the backend (`digiwf-engine-service`), run `io.muenchendigital.digiwf.EngineServiceApplication` with the
profiles `local, streaming, no-ldap`. The application requires environment variables to be set. For this purpose,
the `stack/local-docker.env` should be used. If you run IDEA IntelliJ,
the [.EnvFile plugin](https://plugins.jetbrains.com/plugin/7861-envfile) might be helpful to
add the env file to your run configuration.

### Running the stack for the first time

**Setup Minio**

1. Go to [http://localhost:9001/](http://localhost:9001/)
2. Sign in with *minio* and *Test1234*
3. Create a bucket
4. Create a service account
5. Add the bucket name and service account secrets as env variables to the digiwf-s3-integration
    * `IO_MUENCHENDIGITAL_DIGIWF_S3_BUCKETNAME`
    * `IO_MUENCHENDIGITAL_DIGIWF_S3_ACCESSKEY`
    * `IO_MUENCHENDIGITAL_DIGIWF_S3_SECRETKEY`
    * `IO_MUENCHENDIGITAL_DIGIWF_S3_URL=http://localhost:9000`

## Additional Properties/Envs

There is a set of properties defined in `local-docker.env`.
Set the following properties either in an `.env` file or add them in a `custom application-*.properties`.

```
IO_MUENCHENDIGITAL_DIGIWF_S3_BUCKETNAME=
IO_MUENCHENDIGITAL_DIGIWF_S3_ACCESSKEY=
IO_MUENCHENDIGITAL_DIGIWF_S3_URL=http://localhost:9000
IO_MUENCHENDIGITAL_DIGIWF_S3_SECRETKEY=

IO_MUENCHENDIGITAL_DIGIWF_COSYS_SSOTOKENREQUESTURL=
IO_MUENCHENDIGITAL_DIGIWF_COSYS_URL=
IO_MUENCHENDIGITAL_DIGIWF_COSYS_SSOTOKENCLIENTID=
IO_MUENCHENDIGITAL_DIGIWF_COSYS_SSOTOKENCLIENTSECRET=

DIRECTORY_LDAP_CONTEXTSOURCE=
DIRECTORY_LDAP_PERSONSEARCHBASE=
DIRECTORY_LDAP_PERSONOBJECTCLASSES=
DIRECTORY_LDAP_OUSEARCHBASE=
DIRECTORY_LDAP_OUOBJECTCLASSES=
```

## LHM PC

- [Instructions for WSL](https://git.muenchen.de/ext.dl.moesle/digiwf-local-setup)

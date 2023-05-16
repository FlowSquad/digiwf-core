Required to run cypress is the following digiwf-apps/tests/.env file:
```
AUTH2_USERNAME=johndoe
AUTH2_PASSWORD=...
```

1. Run in /stack: docker compose up -d
2. Stop digiwf-gateway container
3. Run configuration EngineServiceApplication (enable EnvFile, Profile: local, streaming, no-mail, no-ldap)
4. Run configuration AppGatewayApplication (enable EnvFile, Profile: local)
5. Run start script: serve:tasklist
6. Run start script e2e:local (cypress testing) or GUI version e2e:local:gui
This is a quick and dirty first README

Required to run cypress is the following digiwf-apps/tests/.env
AUTH2_USERNAME=johndoe
AUTH2_PASSWORD=test

1. run in /stack: docker compose up -d
2. stop digiwf-gateway
3. Run configuration EngineSerivceApplication (enable EnvFile, Profile: local, streaming, no-mail, no-ldap)
4. Run configuration AppGatewayApplication (enable EnvFile, Profile: local)
5. run start script: serve:tasklist
6. run start script: e2e:local:gui (Cypress Testrunner GUI version)
7. ...
#mvn clean install -DskipTests
docker build -t digiwf-tasklist-service:latest digiwf-task/digiwf-tasklist-service
docker build -t digiwf-gateway-service:latest digiwf-gateway
docker build -t digiwf-engine:latest digiwf-engine/digiwf-engine-service
docker build -t digiwf-connector:latest digiwf-connector/digiwf-camunda-connector-service
docker build -t digiwf-tasklist:latest digiwf-apps/packages/apps/digiwf-tasklist
docker build -t digiwf-cosys:latest digiwf-integrations/digiwf-cosys-integration/digiwf-cosys-integration-service
docker build -t digiwf-signing:latest digiwf-integrations/document-signing-service
docker build -t digiwf-s3:latest digiwf-integrations/digiwf-s3-integration/digiwf-s3-integration-service

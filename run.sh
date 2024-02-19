#!/bin/bash

echo "Building digiwf-apps"
cd digiwf-apps && npm run init && npm run build
cd ..

echo "Building services"
mvn clean verify -DskipTests -T8

echo "Building docker images"
./buildDocker.sh

echo "To run the application, execute the following command:"
echoe "docker compose -f stack/docker-compose-showcase.yml up -d"

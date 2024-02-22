#!/bin/bash

echo "Building digiwf-apps"
cd digiwf-apps && npm run init && npm run build
cd ..

echo "Building services"
./mvnw clean verify -DskipTests -T8

echo "Building docker images"
./buildDocker.sh

echo "Starting services"
docker compose -f stack/docker-compose-showcase.yml up -d

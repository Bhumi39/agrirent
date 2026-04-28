#!/usr/bin/env bash
cd "$(dirname "$0")"
echo "Starting Agriculture Rental project on http://localhost:8082"
chmod +x ./mvnw
./mvnw spring-boot:run

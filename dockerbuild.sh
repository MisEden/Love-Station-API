#!/bin/bash
set -e

if [ "$1" == "local" ]; then
  docker-compose -f docker-compose-local.yaml down
  docker-compose -f docker-compose-local.yaml up -d
  exit 1
elif [ "$1" == "dev" ]; then
  docker-compose -f docker-compose-dev.yaml down
  docker-compose -f docker-compose-dev.yaml build --force-rm love-station-api
  docker-compose -f docker-compose-dev.yaml up -d
elif [ "$1" == "prod" ]; then
  docker-compose -f docker-compose-prod.yaml down
  docker-compose -f docker-compose-prod.yaml build --force-rm love-station-api
  docker-compose -f docker-compose-prod.yaml up -d
else
  docker-compose -f docker-compose-dev.yaml down
  docker-compose -f docker-compose-dev.yaml build --force-rm love-station-api
  docker-compose -f docker-compose-dev.yaml up -d
fi
echo "wait for running..."
sleep 40

curl -H "Content-Type: application/json" -X GET http://localhost:8080/v1/api/health

echo "\n"

exit 0
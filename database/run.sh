#!/bin/bash

docker build -t h2-database .
docker rm -f h2-db || echo "Not yet running"
docker run -p8089:8089 --name h2-db --network net1  h2-database
docker network attach net1 h2-db 
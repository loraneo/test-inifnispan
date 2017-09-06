#!/bin/bash

docker rm -f test$1 || echo "Not yet running"

docker service create --detach -p808$1:8080 --network net$1 --name test$1 test-infinispan1 
#!/bin/bash

mvn clean package || exit 1
docker build -t test-infinispan1 . || exit 2



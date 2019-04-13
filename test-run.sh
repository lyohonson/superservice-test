#!/usr/bin/env bash
echo "---------------------Cleaning /build----------------------------"
./gradlew clean
echo "--------------------Build and run tests-------------------------"
./gradlew test -DserviceHost=localhost -DservicePort=5000 -DdbPath=$1
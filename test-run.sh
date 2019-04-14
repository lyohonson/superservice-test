#!/usr/bin/env bash
#if[$1==""];
#then
#printf
echo "---------------------Cleaning /build----------------------------"
./gradlew clean
echo "--------------------Build and run tests-------------------------"
./gradlew test -DserviceHost=localhost -DservicePort=5000 -DdbPath="$1"
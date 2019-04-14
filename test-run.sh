#!/usr/bin/env bash
if [[ $# -eq 0 ]] ; then
    echo 'No arguments. Enter full path to main.db'
    exit 1
fi
echo "---------------------Cleaning /build----------------------------"
./gradlew clean
echo "--------------------Build and run tests-------------------------"
./gradlew test -DserviceHost=localhost -DservicePort=5000 -DdbPath="$1"
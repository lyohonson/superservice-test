To run tests firstly you need to do the following:
- Install Java version 1.8.0_*


To run tests:
- Check your superservice.py started on http://localhost:5000
- Run command:
  ./test-run.sh <Path_to_your_main.db>

  or run:
  ./gradlew -DserviceHost=localhost -DservicePort=5000 -DdbPath=<Path_to_your_main.db>

The values for the parameters above depend on how you run superservice.

Allure reports will be generated automatically and opened in default browser after build.
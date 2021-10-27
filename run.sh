#!/bin/bash
set -e

#maven clean old package
echo "\nStep1. clean package\n"
mvn clean

#maven build package
echo "\nStep2. build package\n"
mvn package

#move .jar to ../ directory
echo "\nStep3. change jar path\n"
mv ./target/love-station-1.0.jar ./love-station-1.0.jar

#run api
echo "\nStep4. run jar\n"
java -jar love-station-1.0.jar &

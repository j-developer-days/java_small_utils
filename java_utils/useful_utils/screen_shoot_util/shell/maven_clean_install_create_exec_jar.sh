#!/bin/bash
clear

readonly parent_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
readonly V_EXEC_JAR=exec_jar

#mvn clean install --file ../pom.xml -U -DexecJar=t -Pdelombok &&
mvn clean install --file ../pom.xml -U -DexecJar=t &&
echo "------------------------------------------------------------------clean install success"

if [ -d "../$V_EXEC_JAR" ]; then
  echo "Directory already exists - ${DIR}..."
else
  mkdir -pv ../$V_EXEC_JAR
  echo "create directory - $V_EXEC_JAR"
fi


cp -v ../target/screen_shoot_util_exec_jar.jar ../$V_EXEC_JAR &&
cp -v ../target/classes/logger.properties ../$V_EXEC_JAR

rm -rfv ../$V_EXEC_JAR/libs/ &&
cp -rv ../target/libs/ ../$V_EXEC_JAR/libs/ &&
cp -v run_jar_file.sh ../$V_EXEC_JAR/run_jar_file.sh

#sleep 2 && clear && rm -rfv ../$V_EXEC_JAR

cd $parent_path
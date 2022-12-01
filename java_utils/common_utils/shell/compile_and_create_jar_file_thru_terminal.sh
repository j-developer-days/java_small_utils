#!/bin/bash
clear

#mvn -X --for debug
mvn package --file ../pom.xml -U
rm -rfv ../temp_folder

mkdir -pv ../temp_folder/classes
mkdir -pv ../temp_folder/jar

javac -d ../temp_folder/classes/ -sourcepath ../src/main/java ../src/main/java/com/jdev/file/*.java ../src/main/java/com/jdev/logger/*.java ../src/main/java/com/jdev/util/*.java
jar --create --verbose --file ../temp_folder/jar/common_utils.jar -C ../temp_folder/classes/ .

#if we not need result of this script, we can uncomment next line
#rm -rfv ../temp_folder
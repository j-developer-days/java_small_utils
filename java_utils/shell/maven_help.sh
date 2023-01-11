#!/bin/bash
clear

#mvn --file ../pom.xml -U help:evaluate -Dexpression=project.version -q -DforceStdout -Doutput=./file.txt
#echo

#RESULT=$(mvn --file ../pom.xml -U help:evaluate -Dexpression=project.version -q -DforceStdout)
#echo $RESULT

RESULT=$(mvn --file ../pom.xml -U help:evaluate -Dexpression=project.modules.strings.string -q -DforceStdout)
echo $RESULT
#!/bin/bash
#compile - compile the source code of the project
clear && mvn clean compile --file ../../pom.xml -U
#sh ./maven_clean_by_param.sh compile
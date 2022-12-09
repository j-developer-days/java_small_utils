#!/bin/bash
#validate - validate the project is correct and all necessary information is available
clear && mvn clean validate --file ../../pom.xml -U
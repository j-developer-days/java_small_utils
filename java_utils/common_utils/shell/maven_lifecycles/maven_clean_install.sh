#!/bin/bash
#install - install the package into the local repository, for use as a dependency in other projects locally
clear && mvn clean install --file ../../pom.xml -U
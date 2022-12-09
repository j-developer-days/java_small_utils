#!/bin/bash
#verify - run any checks on results of integration tests to ensure quality criteria are met
clear && mvn clean verify --file ../../pom.xml -U
#!/bin/bash
clear && mvn clean package --file ../pom.xml -U && mvn liquibase:update --file ../pom.xml
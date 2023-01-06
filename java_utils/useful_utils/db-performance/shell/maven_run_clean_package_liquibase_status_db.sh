#!/bin/bash
clear && mvn clean package --file ../pom.xml -U && mvn liquibase:status --file ../pom.xml
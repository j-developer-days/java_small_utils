#!/bin/bash
clear && mvn --file ../pom.xml -U clean package
mvn --file ../pom.xml -U -Proot-db liquibase:dropAll && mvn --file ../pom.xml -U -Proot-db liquibase:update
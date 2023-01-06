#!/bin/bash
clear && mvn --file ../pom.xml -U clean package && mvn --file ../pom.xml liquibase:rollback -Dliquibase.rollbackCount=1
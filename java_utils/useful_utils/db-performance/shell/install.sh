#!/bin/bash
clear
mvn --file ../pom.xml install
#mvn --file ../pom.xml -U install:help
#mvn --file ../pom.xml -U install:help -Ddetail=true
#mvn --file ../pom.xml -U install:help -Ddetail=true -Dgoal=install

#mvn --file ../pom.xml -U org.apache.maven.plugins:maven-install-plugin:help -Ddetail=true
#mvn --file ../pom.xml -U org.apache.maven.plugins:maven-install-plugin:3.0.0-M1:help -Ddetail=true
#mvn --file ../pom.xml -U org.apache.maven.plugins:maven-install-plugin:3.1.0:help -Ddetail=true
#mvn --file ../pom.xml -U org.apache.maven.plugins:maven-install-plugin:2.5:help -Ddetail=true
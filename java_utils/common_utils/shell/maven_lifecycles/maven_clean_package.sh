#!/bin/bash
#package - take the compiled code and package it in its distributable format, such as a JAR.
clear && mvn clean package --file ../../pom.xml -U
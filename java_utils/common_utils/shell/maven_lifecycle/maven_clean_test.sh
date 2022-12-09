#!/bin/bash
clear && mvn clean test --file ../../pom.xml -U -Dskip.UT.tests=false
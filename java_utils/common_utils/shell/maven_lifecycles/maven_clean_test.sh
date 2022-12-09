#!/bin/bash
#test - test the compiled source code using a suitable unit testing framework. These tests should not require the code be packaged or deployed
clear && mvn clean test --file ../../pom.xml -U -Dskip.UT.tests=false
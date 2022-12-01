#!/bin/bash
clear && mvn clean install --file ../pom.xml -U -Dskip.UT.tests=false
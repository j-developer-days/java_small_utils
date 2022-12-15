#!/bin/bash
java -agentlib:jdwp=transport=dt_socket,server=y,address=5007 -jar ../target/screen_shoot_util_exec_jar.jar
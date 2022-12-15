#!/bin/bash
java -agentlib:jdwp=transport=dt_socket,server=y,address=5007 -cp ../target/classes/.:../target/libs/common_utils-1.2.1-14.12.2022.jar com.jdev.screen.ScreenShooterStarter
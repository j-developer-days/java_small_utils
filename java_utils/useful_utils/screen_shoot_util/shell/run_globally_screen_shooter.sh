#!/bin/bash
clear

readonly parent_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
echo $parent_path

sudo rm -rfv /usr/bin/screen_shooter
sudo mkdir -p /usr/bin/screen_shooter

sudo cp -rv libs/ /usr/bin/screen_shooter
sudo cp -v logger.properties /usr/bin/screen_shooter/logger.properties
sudo cp -v screen_shoot_util_exec_jar.jar /usr/bin/screen_shooter/screen_shoot_util_exec_jar.jar
sudo cp -v screen_shooter.sh /usr/bin/screen_shooter.sh
sudo chmod -v +x /usr/bin/screen_shooter.sh
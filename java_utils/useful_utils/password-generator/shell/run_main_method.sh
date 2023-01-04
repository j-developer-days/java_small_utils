#!/bin/bash
java -cp ../target/classes/:../target/libs/common_utils-1.2.3-04.01.2023.jar com.jdev.PasswordGeneratorRunner $1 $2
#java -cp ../target/libs/common_utils-1.2.3-04.01.2023.jar:../target/classes/ com.jdev.PasswordGeneratorRunner $1 $2
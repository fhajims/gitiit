#!/bin/zsh

firstFile=$1
# or specify the file you want to check:
#firstFile="...../MainActivity.kt" 


# https://github.com/arturbosch/detekt 
echo "How to get started with detekt"
git clone https://github.com/arturbosch/detekt
cd detekt
java -jar detekt-cli/build/libs/detekt-cli-1.4.0-all.jar --input ${firstFile}

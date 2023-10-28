#!/bin/zsh

firstFile=$1
# or specify the file you want to check:
#firstFile="...../MainActivity.kt" 

# https://ktlint.github.io/#getting-started
echo "How to get started with ktlint"
curl -sSLO https://github.com/pinterest/ktlint/releases/download/0.36.0/ktlint 
chmod a+x ktlint
./ktlint  ${firstFile}
#!/bin/zsh

echo "We remove all the build folders"
find . -type dir -name "build" -exec rm -rf {} +

echo "Done"
#!/bin/zsh

echo "we show the sizes of all the build folders"
find . -type dir -name "build" -exec du -ms {} +

echo "Done"
#!/bin/zsh
echo -n "MB in src: "
du -ms src
echo "We clean up the build folders (Note: they are also ignored by git)"

rm -rf ./src/build
rm -rf ./src/app/build
echo -n "MB in src: "
du -ms src
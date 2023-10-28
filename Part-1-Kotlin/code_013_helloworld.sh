#!/bin/bash
# 
# required: installed kotlin compiler
# check out: https://github.com/JetBrains/kotlin/releases/tag/v1.4.10 
#
# e.g. on macOS (some x00 MB downloaded)
#  ? brew cask install adoptopenjdk ?
#  brew install kotlin-native
#     if you get quarantine warning "libllvmstubs.dylib” cannot be opened because the developer cannot be verified’"
#  => xattr -d  com.apple.quarantine /usr/local/Caskroom/kotlin-native/1.4.0/kotlin-native-macos-1.4/konan/nativelib/*.dylib

echo "Compiling kotlin into binary... "
mkdir -p tmp
time kotlinc-native code_013_helloworld.kt -o tmp/hello
echo "Now check out the compiled binary in the tmp directory"
# 'hello world' should be around 0.8 MB
ls -alh tmp/hello.kexe
# e.g. 64 bit executable 
file tmp/hello.kexe 
echo "We are running native binary..."
echo "*"
# first time execution takes about 0.2 s
time ./tmp/hello.kexe 
# second time should be even faster ~ 0.01s 
time ./tmp/hello.kexe 
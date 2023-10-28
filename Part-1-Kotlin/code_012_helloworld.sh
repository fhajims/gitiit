#!/bin/bash
echo "Compiling kotlin into classes and pack into jar file ... "
mkdir -p tmp
kotlinc code_011_helloworld.kt -include-runtime -d tmp/hello.jar 
echo "Now check out the compiled java class in the jar-file in the tmp directory"

echo "We are running the byte code (*.class) with the Java virtual machine:"
java -jar tmp/hello.jar 
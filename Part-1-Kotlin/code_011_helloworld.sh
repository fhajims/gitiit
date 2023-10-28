#!/bin/bash
echo "Compiling kotlin into classes and put them in subdir 'tmp' ... "
kotlinc code_011_helloworld.kt -d tmp
echo "Now check out the compiled java class in the tmp directory"
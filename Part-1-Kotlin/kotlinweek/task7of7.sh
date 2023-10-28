#!/bin/bash

# Download and add library to run Kotlin with Coroutines
# e.g. download from https://mavenlibs.com/jar/file/org.jetbrains.kotlinx/kotlinx-coroutines-core

# Note for Android Studio:
#
# coroutines are provided by library: "kotlinx.coroutines"... i.e. in gradle use:
# =>
# dependencies {
#    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4'
# }
OUTDIR="tmp"
PATHTOLIB="./include/kotlinx-coroutines-core-1.6.4.jar"
PATHTOOUT="$OUTDIR/task7of7.jar"
SRCWITHOUTSUFFIX="task7of7"
CLASSTOSTARTUP="Task7of7Kt" # Note Uppercase and suffix "Kt"

echo "Create temporary output directory..."
mkdir -p $OUTDIR


echo "Compile to bytecode and run within a JVM (if compilation is successful)..."
echo " "
kotlinc \
 -include-runtime \
 -d $PATHTOOUT \
 -cp  ${PATHTOLIB} \
 $SRCWITHOUTSUFFIX.kt \
&& \
java \
 -cp $PATHTOLIB:$PATHTOOUT \
    $CLASSTOSTARTUP


echo " "
echo "Done."
echo " You might remove the temp folder '${OUTDIR}' including the '${PATHTOOUT}' file: 'rm -rf tmp'."
---

---
#### [Home](../README.md)

---


# Part 2 – Tools

Tools for Android Development, Automation and for Android Forensics (Inspection)


**Typical Tasks**

* Create apps: compile, package, sign 
* Reverse engineering: analyse, decompile


## IDE - Android Studio

**Android Studio** is suggested. Get familiar with (USB-) **debugging**, inspecting the **LogCat** output and updating the Software Development Kits **SDKs** as well as the **emulators**, the Android Virtual Devices **AVDs**. Under the hood, Android Studio works with **gradle** as build tool to automate the compile, assemble, deploy tasks and more.



### From any source code file

* Kotlin / Java / Bytecode Inspection
	* ```Tools / Kotlin / Show Kotlin Bytecode```
	* Button **Decompile** 


### Inspect existing ```*.apk``` files

* Android Studio: Open apk using menu entry *Profile or debug apk*.


### Deployment build

* With Android Studio: ```View / Tool Window / Build``` 
	* create **apk for deployment**: ```./gradlew assembleRelease```
	* Inspect ```./app/build/outputs/apk/release/app-release-unsigned.apk```
	* Optionally, use ```zipalign``` and ```apksigner``` to sign it with YOUR developer key.

	
## Command Line Tools (gradle, adb, am, pm)

**Idea**: we want to find out: which kind of development such as which programming language was used (java, kotlin, react/cordova), which libraries (3rd party, native libs: e.g. facebook integration) are in use, which sensors and permissions (e.g. camera, audio in manifest) are necessary and wheather the app is obfuscated (e.g. use of proguard)? 

**Advantage**: later you might change code, revoke permissions, and resign an app.


### Setup / check / inspect tools and paths.

Dependent of your system the commands might differ. For example, set and add the pathes to **android sdk**:


* for Windows:

```
PATH= %PATH%;C:\Users\User\AppData\Local\Android\Sdk\platform-tools
PATH= %PATH%;C:\Users\User\AppData\Local\Android\Sdk\emulator
PATH= %PATH%;C:\Users\User\AppData\Local\Android\Sdk\tools\bin
```

* for Linux and for Mac (and zsh):

```
export ANDROID_SDK_ROOT="~/Library/Android/sdk"
echo  $ANDROID_SDK_ROOT

echo 'PATH=$PATH:'${ANDROID_SDK_ROOT}'/tools/bin/' >> ~/.zshrc
echo 'PATH=$PATH:'${ANDROID_SDK_ROOT}'/platform-tools/' >> ~/.zshrc
echo 'PATH=$PATH:'${ANDROID_SDK_ROOT}'/emulator' >> ~/.zshrc
source ~/.zshrc
```

### Java for Gradle (and other tools)

* Gradle (and other tools such as *avdmanager*) need **Java 8** (Version 1.8). Install for example the JDK 8 from [OpenJDK](https://openjdk.java.net/install/) and set your ```JAVA_HOME``` environment variable, such as following example


* For Linux:

	```
	export JAVA_HOME='/usr/local/android-studio-ide-201.6858069-linux/android-studio/jre/jre'
	export PATH=$JAVA_HOME/bin:$PATH
	```

* For Mac (and zsh):

	* *Note for Mac-Users*: One can list all the installed jdks with the command  ```/usr/libexec/java_home -V```.

	```
	export JAVA_HOME="~/Library/Java/JavaVirtualMachines/corretto-1.8.0_302/Contents/Home"
	```
	

* Try out **gradlew**: in the base directory of Android apps (created with Android Studio) check out available Gradle tasks:

	```./gradlew tasks```.	




## Prepare your Android phone for development

For development it is much better to use a real device.

* Step-by-step
	* (1) Add **Developer Menu Entry** ```{} Developer options``` 
		* tap 7 times on ```Settings / About phone / Build number```
	* (2) Switch on **USB Debugging**
		* Enable ```Settings / System / Advanced / Developer options / USB debugging``` (*Debug mode when USB is connected*)
	* (3) Connect via USB-Cable
		* try (Find details below)

		```
		adb devices
		```
		
		you get something, like following two devices listed, if a real device is connected and an emulator is running too:
		
		```
		List of devices attached
		99051FFBZ00D39	device
		emulator-5554	device

		``` 


## Main Tool for remote control the device / emulator

Android Debugging Bridge **adb**:

```
adb version 
```

For several tools Java 1.8 is required. Find out which path tho Java Android Studio is using and set environment variable ```JAVA_HOME```.

## Source Code Checks

* detekt

	```
	java -jar detekt-cli/build/libs/detekt-cli-1.4.0-all.jar --input <afile.kt>
	```
	
* ktlint

	```
	./ktlint <afile.kt>
	```

## Code Optimisation 

Code should be shrinked/minified and optimised. It can be obfuscated. The task is performed by the **R8 compiler** and is enabled by default for release builds. 

### Minify code and shrink resoures

In file ```build.gradle``` for release build the minifying is enabled by default:

```
...
buildTypes {
        release {
            minifyEnabled true
            shrinkResources true
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
...
```

### Custom Obfuscation

Basic obfuscation is switched on with the ```minifyEnabled true``` flag (see above). Customize obfuscation rules by setting *Progard rules* in file ```proguard-rules.pro```:

Note: *The R8 compiler uses the proguard rules for configuring the obfuscation process.*





## Compiling and package to apk

List all tasks available and start debug/release builds with Gradle:

```
./gradlew tasks --all

./gradlew :app:assembleDebug


/gradlew :app:assemble
ls -al ./app/build/outputs/apk/release/
```


## Automate 

Use tool to automate. The Android Bridge **adb** allows to remote control emulators and real devices. The package manager **pm** is used for installing apps and the activity manager **am** to startup (activities within) apps. 

### Create and run emulator 

Full featured with Google Playstore: 

```
echo no | avdmanager \
		create avd \
		--force \
		--name myCustomInspectionAVD \
		--abi google_apis_playstore/x86_64 \
		--package 'system-images;android-31;google_apis_playstore;x86_64'
``` 

*Note*: Google Play does not allow ```adb root```.

Older API level and without Google Playstore: 

```
echo no | avdmanager \
		create avd \
		--force \
		--name anotherCustomInspectionAVD \
		--package 'system-images;android-25;google_apis;x86_64'
```


Adjust to the api level of your choice, i.e. use -29 instead of -23 ....  compare to previous created avds: ```cat ~/.android/avd/*.ini && cat ~/.android/avd/*.avd/config.ini```  

Start up an *android virtual device* AVD:

```
ls -al ~/.android/avd
emulator -avd "myCustomInspectionAVD"
```


### Connection to Device / Emulator

* Find out, if a device/emulator is connected using the Android Debug Bridge
	```adb devices -l```
	
* and open a shell:
	* Default (works only If just one device/emulator is running): ```adb shell```
	* Emulator TCP/IP: ```adb -e shell```
	* Device over USB: ```adb -d shell```
	* Specific device (here 94WAY0TG87 ): ```adb -adb -s 94WAY0TG87 shell```
	* Specific device given transport id (here 2): ```adb -t 2 shell```

### Install / Uninstall Apps

Install on device

```
adb install -t app-debug.apk
```

For uninstall, we have to find out the package name
```
adb shell "pm list packages at.fhj"
adb uninstall at.fhj.iit.theshow
```

### Run Apps

```
adb shell am start -n "at.fhj.iit.sunsetslideshow/at.fhj.iit.sunsetslideshow.MainActivity" -a android.intent.action.MAIN -c android.intent.category.LAUNCHER
```

### Sending System Events, i.e. **Implicit Intents** to an App

First, connect via telnet to the device/emulator and authorize:

```
cat ~/.emulator_console_auth_token
# e.g.: FOoWnz55R8ge6Jf8

telnet localhost 5554
auth FOoWnz55R8ge6Jf8
```

then:

```
gsm call +438605987
sms send +438605987 "Hello SMS from telnet"

power ac on
power ac off

help 
help network

network speed gsm
network speed 5g
```

### Sending Events, i.e. **Explicit Intents** to an App

E.g. sending (**broadcast**) key value (```TAG``` /  ```snow```) to an app (which has registered a broadcast receiver to listen for ```at.fhj.slideshow.FILTER```.

```
adb -s emulator-5554  shell am broadcast -a "at.fhj.slideshow.FILTER" -e "TAG" "snow"
``` 


## Mirror app to your desktop

Install and run mirroring tool on your desktop:

E.g. Download/install <https://github.com/Genymobile/scrcpy>.

```
scrcpy
```

On the device

```
adb -s 94WAY0TG87 shell am start ...
```


## Inspect the Linux system (as root)

* Older systems (and emulator) support root access:

	```adb root``` for android <= 4.2, AP level 17 Jelly Bean)
	
	
	```
	adb shell
	
	whoami
	id
	ls -al /data
	cat /proc/meminfo
	cat /proc/version
	ps -ef | wc -l
	lsof
	```


* Output the system events from the system log:

	```
	adb shell logcat
	adb shell logcat | grep at.fhj
	```
	
* use adb in combination with pm an am:

	```
	pm list libraries
	```

* list and dump info on services

	```
	adb shell dumpsys
	adb shell dumpsys -l # to list all services
	adb shell dumpsys user
	adb shell dumpsys display
	adb shell dumpsys battery
	adb shell dumpsys ....
	adb shell dumpsys package com.android.settings
	```

* Use your Linux know how to report:
	* number of ps, find user and process id, report cpu usage: 
		* e.g. ```ps -ef | wc -l```
		* e.g. ```ps -ef | grep at.fhj```
		* e.g. ```top```
	* report id, groups, ... open files of your app process
		* e.g. ```groups u0_a139``` 
		* e.g. ```lsof```
	* optional report about loged in user and the system
		* e.g. ```whoami```, ```id```, ```cat /proc/meminfo```, ```cat /proc/version```, ...
		* report about app: fs permissions e.g. ```ls -al```


* Inspect Security Enhanced Linux (SELinux):
	* Show SELinux labels (to inspect *user/role/type/range* - fields) for processes, files and directories:
		* e.g. ```ps -eZ```  
			
			```
			...
			u:r:webview_zygote:s0          webview_zyg+    872    289 1772388  50824 0                   0 S webview_zygote
			u:r:untrusted_app:s0:c116,c25+ u0_a116        7988    289 1274984 103476 0                   0 S com.android.chrome
			...
			``` 
		* e.g. ```ls -dZ /storage/emulated/0/*```
		* e.g. ```ls -Z /system/app/EasterEgg```
		* e.g. ```ls -Z /system/app/EasterEgg/*.apk```

			```
			u:object_r:system_file:s0 /system/app/EasterEgg/EasterEgg.apk
			```

	* Show SELinux labels set for specific file system pathes or for app-domains:
		* e.g. ```cat /system/etc/selinux/plat_file_contexts | grep /mnt/sdcard```
		* e.g. ```cat /system/etc/selinux/plat_seapp_contexts```


## Inspection of app


Typical tasks, are to find out:

* About the UI
	* Languages used: German, English, Chinese, ...
	* Resources
		* strings
		* layouts

* About the code
	* Programming language: Kotlin? 
	* was NDK (native development kit: C, C++) used
	* obfuscated / optimised <= classes named a b c 
	* libraries / multidex
		* libs for analytics 
		* logging libraries
* About the App Features
	* Manifest: 
		* permissions(!) 
		* activities (which might be started)
		* minSdkVersion <= can be run as root on virtual device? 
		* Keys (API keys)
		* android:scheme <= might we use (crafted) "URL"s to start or to send (fake) data
		* android:allowBackup ... ?
		* android:usesCleartextTraffic, ..network config, usw. usf <= security, client certificates, ... 
		* starts or uses services, broadcast receivers, content provider,... ?
* Network, Filesystem 	
	* uses SD-card

* Privacy, Security
	* hard coded credentials
	* signed by 




### Download an app (apk) and/or data 

Downloading (**pull**) an *apk* after finding the full app name and location by listing and filtering existing apps (by their package name, i.e. the unique app-id).

```
adb -s 94WAY0TG87 shell pm list packages -f | grep fhj
adb -s 94WAY0TG87 pull /data/app/~~vR8Xxo4zn5qHtYeL-7Bm7g==/at.fhj.iit.sunsetslideshow-vRZW-hUzxpTO2VRLLUt21Q==/base.apk /tmp/inspectme.apk
```

```
unzip /tmp/inspectslideshow.apk -d /tmp/
```

Note: see below tool: ```apkanalyzer```.


### Inspect the backup 

* Get app data via backup, then extract and inspect (as shown above): 
		
	```
	adb backup -apk -oob ....
	``` 
	
	```
	dd ... | open ssl ... > backkup.tar
	```
		
* A too simple approach is just to **unzip** the apk, because the Manifest is still in an unreadable binary format, the classes are compiled in unreadable *.dex files

	```
	# Too simple, just extracting the zipped (apk) file
	unzip base.apk -d ./extracted/
	
	# Sorry, Manifest still unreadable (compiled, binary):
	tree ./extracted |grep  -3 "Manifest.xml"
	cat ...Manifest.xml
	```

### Security-related tools (built-in)

In addition to normal linux tools (*zip*, *file*, *vi*, ...) further tools are recommended:

* **keytool**: find out if self-signed, if debug certificate was used
	
	```
	keytool -printcert -jarfile Snapchat_v10.70.0.0_apkpure.com.apk
	```

* **jarsigner**: find out who signed the apk

	```
	jarsigner -verify -verbose -certs Snapchat_v10.70.0.0_apkpure.com.apk | grep CN= | uniq
	```
	
* **apksigner**: find out who signed the apk

	```
	apksigner verify -print-certs Pocket\ Comics\ Premium\ Webtoon_v1.3.3_apkpure.com.apk 
	```

* **apkanalyser**
	
	```
	apkanalyzer files list app-debug.apk
	apkanalyzer files ... app-debug.apk
	apkanalyzer apk features app-debug.apk
	apkanalyzer apk ... app-debug.apk
	apkanalyzer manifest permissions app-debug.apk
	apkanalyzer manifest version-name app-debug.apk
	apkanalyzer manifest ... app-debug.apk
	apkanalyzer dex list app-debug.apk
	apkanalyzer dex ... app-debug.apk
	```

* **aapt2** tool (located in ```build-tool``` subdir of the android-sdk) makes it easy to view permissions, strings and more:

	```
	${ANDROID_SDK_ROOT}/build-tools/31.0.0/aapt2 -h 
	
	# For example, inspect strings
	${ANDROID_SDK_ROOT}/build-tools/31.0.0/aapt2 dump strings base.apk
	${ANDROID_SDK_ROOT}/build-tools/29.0.2/aapt dump xmltree app-debug.apk AndroidManifest.xml``
	``` 

## Selected security-related tools (3rd party)


### Static Analysis

* Drag and drop ```*.apk``` files to Android Studio for inspection.

* **apktool**: reverse engineer Android apps with <https://tools.kali.org/reverse-engineering/apktool>, e.g. decompile *.dex.

	```
	apktool -o extracted-apktool d base.apk 
	 
	# now inspect:
	tree extracted-apktool/smali*
	cat extracted-apktool/smali_classes2/at/fhj/omd/android/slideshow08b/R.smali 
	cat extracted-apktool/smali_classes4/at/fhj/omd/android/slideshow08b/model/GPS.smali
	cat extracted-apktool/AndroidManifest.xml 
	```


* **jadx**: dex to java decompiler <https://github.com/skylot/jadx>


	```
	jadx -v -r -d extracted-jadx -ds extracted-jadx-sources base.apk
			
	# now inspect the sources
	tree extracted-jadx-sources/at		
	```

	*Note:* In the decompiled bytecode one might find ```@Metadata``` annotations within some classes. The Kotlin compiler adds annotation (metadata information) to the bytecode to be used by Kotlin libraries at runtime. This information *adds features*, which might not be available with the Java programming language.
	
	```
	@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002...\u0010\b\u001a\u00020\t¨\u0006\n"}, d2 = {"Lat/fhj/ims/ShowActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "playSong", "v", "Landroid/view/View;", "app_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
	/* compiled from: ShowActivity.kt */
	public final class ShowActivity extends AppCompatActivity {
	```



* dex2jar: <https://github.com/pxb1988/dex2jar>
* JD GUI: decompile java with <http://java-decompiler.github.io>.
* apkparser: similar to apkanalyzer: <https://github.com/hsiafan/apk-parser>

* smali/baksmali: assembler/disassembler, i.e. for decompiling **old dex** files for Dalvik <https://github.com/JesusFreke/smali>

* VS Code Plugin **APKLab** <https://github.com/surendrajat/apklab> to open ```*.apk``` and inspect resources as well as code.

### Dynamic Analysis

* **Frida** <https://frida.re> allows to patch apps by hooking into API calls, to *rewrite methods* (using JavaScript scripts). 

### Inspect Network Traffic

* **Burp** Suite <https://portswigger.net/burp> to intercept network traffic and view request and responses.
 
* **mitmproxy** Man-In-The-Middle proxy <https://mitmproxy.org> to inspect HTTPs encrypted traffic on the fly (if the mitmproxy CA certifices can be installed on the client device). Mitmproxy can be instrumented (automated) using a Python API. 




--- 
#### [Next part: Android Apps](../Part-3-Android-Apps/study-material--android-apps.md)

---
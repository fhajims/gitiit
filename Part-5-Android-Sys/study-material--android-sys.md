---

---
#### [Home](../README.md)

---


# Part 5 – Android System Architecture

Selected details on security aspects of Android Apps within the operating system.

## System Architecture ##

**Prerequisite:** For the layered Android platform architecture see [Part 3: Android Apps](../Part-3-Android-apps/study-material--android-apps.md).


### Booting, the Operating System, and the Kernel

* Trusty TEE (trusted execution environment)
* Verified Boot for \emph{chain of trust} and rollback protection: e.g. using a digitally signed boot image
* System Partition
* Full-disk encryption
* File-based encryption
* No swapping partition (i.e. you might run out of memory)
* SEforAndroid: Customised Security Enhanced (SE) - Linux providing mandatory access control (MAC) with *Lables* (security contexts); Additional to DAC[^dac] in standard Linux.
* Process isolation
* Secure Inter Process Communication IPC (see below)


### Starting Apps

* Apps are run in virtual machines (ART VMs) forked (spawned) from the  **Zygote** process. I.e. every spawned process has it's own ART VM.
* Code is shared (by the ```zygote``` process) for performance reasons


### Selected Security Features for (Custom) Apps

* App signing, certificates to verify apks
* Every (Android App-) process is forked from a special process called ```Zygote``` (for startup performance reasons).
* App sandbox (see above)
* User-based permissions model
	* app equals Linux user (id)
	* apps within the same (linux) group are allowed to access common (file system) resources
* APIs for Encryption, Keystore


### About ART

Android runtime (ART) supersedes the Dalvik virtual machine. ART provides:

* Ahead of time compiling (***AOT***).

* ART precompiles Dalvik bytecode into native code, into *Linux* ELF shared object ```*.oat``` files.

	* compilation happens internal on device with tool ```dex2oat```

 	* compilation happens on system upgrade

 	* compilation happens on installation of app

* Android Optimized Application ```*.oat``` files are available since Android 5.x Lollipop. Check out  (```/data/dalvik-cache/```, and the tool ```/system/bin/dex2oat```)
* Garbage collection (in Oreo) with a *Concurrent Copying Garbage Collector*


#### **Update** for ART with Android 7: 

* **Hybrid compilation**, i.e. combination of ahead-of-time, just-in-time, and profile-guided copmilation. 

* Details: On the first runs (with interpreted code and possibly JIT compilation) a profile of an app is created. Only then, frequently used code is compiled by a compilaton daemon when the device is idle (and charging). On subsequent starts of the app the precompiled code-parts get used and, if necessary, further code is scheduled by the JIT compiler (info added to the profile) for compilation by the compilation daemon.

#### History (about Dalvik): 

* Dalvik used special (non-standard) java bytecode. The bytecode is stored as (Dalvik Executables DEX) ```*.dex``` file. Dalvik used **JIT** just-in-time compiling.


* *Details:* ```*.java``` is compiled to ```*.class``` files (= bytecode). BUT: opcodes are different on Dalvik, so it is necessary to convert the ```*.class``` files to ```*.dex``` files. On Android devices, we find the  optimised dex (```*.odex```) files in a cache directory (created after first launch).



### About IPC

Inter-process comunication (IPC) is limited basically to Intents (and Services) using the *Binder*. Explicit intents start a specific app. Implicit intents call other apps which are registered. **Binder** allows to bind apps to running services.

*Note:* Unlike desktop operating system, there is **no support** for signals, sockets or pipes (on higher level APIs). 

*Internal Note 1:* IPC is also possible using so called a *bound service*: (a) by extending the *iBinder interface*, (b) implementing a *Messenger* (class), and (c) AIDL. For (c) AIDL, the IPC can be done implemented via client server communication using the Android Interface Definition Language to un-/marshall objects. 

*Internal Note 2:* Furthermore, API level 27 and the Native Development Kit (NDK) suppport anonymous shared mem (useing a filehandle to ```/dev/ashmem``` which are passed to other processes/apps via ```android.os.ParcelFileDescriptor```).



[^dac]: Discretionary Access Control (DAC): i.e. Linux rwx permissions. Compare to Role-Based Access Control (RBAC), where permissions are assigned to a specific role, users are assigned roles by administrators.



--- 
#### [Next part: Attacks and Mitigations](../Part-6-Attacks+Mitigations/study-material--attacks+mitigations.md)

---
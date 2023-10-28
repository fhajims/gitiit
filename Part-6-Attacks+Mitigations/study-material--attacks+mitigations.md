---

---
#### [Home](../README.md)

---


# Part 6 – Attacks and Mitigations

## Know the risks

There are several (open source) projects which lists typical problems (risks) and best practice (mitigation strategies).

### Required terms / background

* Attack Vectors
* Security Weakness


### Know the possible risks and learn about the assets to protect. 

Most common problems and risks are discussed at:

* Open Web Application Security Project (OWASP) 
	* OWASP Mobile Security
	* Selected Risks from OWASP Mobile Risks Top 10 (2016):
		* M1: Improper Platform Usage 
			* => E.g. Stick to Android Guidelines, API, Kotlin, Libs,...
		* M2: Insecure Data Storage
			* => E.g. Use secured shared data, encrypted filesystem and files, switch off verbose logging, ...  
		* M3: Insecure Communication
			* => E.g. Use TLS, trusted CA providers, ...
		* M4: Insecure Authentication
			* => E.g. Use two factors, ...
		* M5: Insufficient Cryptography
			*	=> E.g. Use up-to-date Crypto-API, Algos/Ciphers and key lengths, consider SHA1, MD5 insecure, ...
		* M6: Insecure Authorization
			*	=> E.g. Use minimal privileges for web services (no admin user/roles),...
		* M7: Client Code Quality
			*	=> E.g. Use tools to check code for quality and security, ...
		* M8: Code Tampering
			*	=> E.g. Try to detect modified code (or rooted environment), ...
		* M9: Reverse Engineering
			*	=> E.g. Obfuscate your code, ... 
		* M10: Extraneous Functionality
			*	=> E.g. Perform code reviews to find hidden features,...


* App security improvement program (ASI) 
	* Flags security issues to developes on Google Play.

* Check Security Level with Mobile App Security Requirements and Verification (**MASVS**), Mobile Security Testing Guide (MSTB), Mobile App Security Checklist


	

### Stick to standards and best practices.

Learn about defense strategies, improve apps, the secure environment.


* Mobile Application Security Verification Standard (**MASVS**)
* **OWASP** Mobile Security Testing Guide




## Selected Attacks and Mitigations

* Attackers use **Reverse Shells**
	* E.g. with the Netcat tool




### System and App Permissions

**Attacks**

* Startup an activity (e.g. start activity via `am` tool) which is not the main activity (possibly circumventing authorisation required to view the activity).

**Mitigation**

* Manifest
	* Do not allow to start intents externally: ```android:exported="false"```.
	* *Signature-based permissions* to communicate between two of your apps. Checks for same signing key.
	

**Attacks**

* An app can be backuped (and restored) using adb. This results in possibly accessing and inspecting private data within the backup files. 
* An app can be inspected at runtime, i.e. debugged, by a (remote) debugger.

**Mitigation**

* Manifest
	* Never allow `<android:allowBackup="false" />`.
	* Never set `<android:debuggable="true" />`. 

### Code, Programming Language & APIs

**Attacks**

Apps are never secure. The algorithms, the code is never proven correctly. Often, evil-crafted user input will break apps.

* Development, GIT

**Mitigation**

* Use [Kotlin](../Part-1-Kotlin/study-material--kotlin.md), it is (slightly) more secure.

* Protect credentials. Never store API keys in the code base (git).

* Run code metrics. Use external tools, such as 
	* *ktlint*, or
	* *detect*.

* Testing
	* Setup test cases.
	* Check code coverage.

* Coding with Crypto APIs

	* Basic (high level)
		* see EncryptedSharedPrefs
		* see BiometricAuthentication 
	
	* Advanced
		* Algorithms
		* PseudoRandomNumberGenerator (PRNG)
		* Hashes
		* Message Digest
		* Message Authentication Code (MAC)
		* Symmetrical (AES, stream vs block, padding, initialisation vectors)
		* Asymmetrical (RSA, public/private keypairs)
		* Signatures
		* Certificates
		* Keystore (PKCS12, Alias)



### Source Code Protection

**Attacks**

* Code Tampering
* Reverse Engineering

**Mitigation**

* Make Reverse Engineering harder with Code Obfuscation.



### Secure Storage

**Attacks**

* Extract sensitive data from shared preferences.

**Mitigation**

* Use encryption provided by the system
	* EncryptedSharedPrefs
	* EncryptedFile



### Use Cryptography

Use the **Crypto API** for hashing, signing and encrypting data. Never even try to write crypto algorithms on your own!

**Attacks**

* Authenticate within the app using no/weak crypto, e.g. by "guessing" passwords from md5 hashes.

**Mitigation**

* Keystores to generate keys and use Crypto APIs.
	* Keystore, keychain
	* Generate key pairs


**Attack**

* Inspect backups in the file system. The plain text data might  hold credentials.

**Mitigation**

* **Encrypt data** when storing into the file system.

### Authentication

**Attacks**

* No or broken authentication (leaked passwords).

**Mitigation**

* Biometric Authentication (Fingerprint)

### Network

Required terms / background

* Certificate, Root Certificate
* Certificate Authorities (CAs)
* Certificate Chains
* Limitations of debug certificates

**Attacks**

* Unsecured networks allow to inspect data in transit.

**Mitigation**

* Transport Layer Security TLS. Always use **https**.
* In Manifest: ```android:usesCleartextTraffic "false"```

* Network Security Configuration file: to specify a *custom CA* or for *certificate pinning*. Settings are done in ```network-security-config```.

	* **Client certificates**

	* Optional, not suggested by Google
		* Custom CAs
		* *Certificate pinning*
		* *Public Key Pinning*






--- 
#### [Back to Overview](../Overview/study-material--overview.md)

---
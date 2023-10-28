---

---
#### [Home](../README.md)

---


# Part 1 – Kotlin

The Kotlin Programming Language.

## Overview ##

The overall idea is to become a better programmer: to speak the same language, to know the main **concepts** of app programming for Android. This should help when **communicating** with other developers and it could help to reduce the amount of code to write (**efficiency**) and write more **secure code**.

### **Code Quality**:

* Know your target group: **write code for humans!**
	* Other developers want to read, modify, and reuse your code. 
* Stick to the [Coding-Conventions]
* Sensible naming (semantics!)
	* (+) speaking names for methods, variables
	* (-) avoid generic names such as ```demo```, ```test```, ```dummy```
* Structure (indentation, line length)
	* suggestion for max. length of a method: about half to one page
* Documentation (if necessary)
	* (+) *What* (is it about) 
	* (-) NOT: *How* each (line of) code works
* Reference external sources 
	* Please honour the work of others. 


## How Kotlin is run
* Kotlin/JVM i.e. Java bytecode
* Kotlin/JS i.e. Browsers, Node.js
* Kotlin/Native i.e. Linux, Windows, macOS, iOS, Android, WebAssembly

## From Java to Kotlin 

The main differences listed in [JAVA-vs-Kotlin]:

* **Idiomatic Kotlin** means to let go old Java habits  
	* (-) No *getters* and *setters*
	* (+) Use *properties*
	* (-) No multi-line code for *singletons*
	* (+) Use keyword *object*

## Top Features Security

* Immutable and mutable variables
* Null safety, lateinit
* Contracts

## Top Features Commodity

* **Immutable variables** <= whenever possible prefer ```val``` over ```var``` for security.
* **Type inference** <= compiler auto-detects type of a new variable 
* **Default arguments** for functions <= call the same method with minimal information (what is different from the default)
* **Named arguments** <= so you need little documentation
* **High-order functions** <= pass functions like  *normal* parameters
	* lambdas with ```it``` (vs. ```this```)
* **Lazy properties** <= set on first use
* **Observable properties** (vetoable) <= event when current value is going to be changed
* **Data classes** <= no need to create boiler plate code for methods such as constructor, ```hasChode```, ```toString```, or ```copy```.
* **Singletons** <= with keyword ```object```
* **Operator overloading** <= for concise, readable code. E.g., use operator ```+``` instead of method ```add```. 
* Scope functions: ```let```, ```run```, ```with```, ```apply```, ```also``` 

## Concurrency

*Check out the prerequisites[^ProcessesAndThreads], the know-how on processes and threads to be able to understand coroutines properly.*


[^ProcessesAndThreads]: **Prerequisites**: Knowledge about real (hardware) parallelism in contrast to quasi parallelism. **Multitasking** is managed by the operating system (keywords: processes, scheduler, dispatcher, context switch, preemtive, inter-process communication overhead). For concurrency within a process, we use **Multithreading** (keywords: threads, sharing the heap memory, race conditions, synchronisation, threadsafe, deadlocks, not termination from outside). Multithreading is faster and cheaper than multitasking, but not that secure. Multithreaded programming with nested threads might lead to **callback hell**.

The main idea is to **avoid** the so called **callback hell** and pretend/simulate a sequential (serial) program-flow. The code is shorter and better readable. 


* **Coroutines** are not threads (threads are typically managed by the os), but just code which can be **suspended** and **resumed** later. Coroutines are *not preemptive*, they must (be nice i.e. collaborative and) pause/stop themselves.
	* (-) coroutines provide no real (preemtive) multithreading by default. See remarks below.
	* (+) coroutines are **very light-weight** and fast (no context switching necessary)
	* (+) can simplify asynchronous programming (no need of callbacks). This is called **structured concurrency**.

### How to use Coroutines

* Android apps need import

	```kotlin
	import kotlinx.coroutines.*
	```
	
	
* For using coroutines, we have to mark **suspendable functions** with `suspend`. Suspendable functions are later launched in a given scope within a coroutine *context* (see Scopes and Dispatchers below).
	
	```kotlin
	suspend fun downloadImages(fromURL:Url){
		... // download takes a while
	}
	...
	val f = suspend { ...}
	...
	withTimeout(1000) { ...}
	...				
	``` 


* Coroutines need a **context** and a **scope**. 
	
	* **Scopes** (to **launch async code**, i.e. to **launch suspendable functions**):
		 * Use **Jobs** to manage (lifecycle of coroutines), i.e. to *launch*, to *cancel*, to *join*... 
		 		* Note: For example, coroutines in Android **lifecycleScope** are canceled when Activity/Fragment is destroyed. 
	* Scopes: **GlobalScope** (live time of app) and optionally spedify **Dispatcher** : 
		 	* `Dispatcher.Default` thread pool (typically the # of CPU cores), CPU bound workload
		 	* `Dispatcher.IO` pool 64 threads, I/O workloud: disk / network
		 	* `Dispatcher.Main` Android, update UI 
	
	* (Multiple) operations (coroutines) run within a scope. Optionally, we can block until all coroutines of a scope are done using ```join```.
		* E.g., 
		
		```kotlin
		launch { ... }
 		GlobalScope.launch { ... }
 		...
 		GlobalScope.launch(Dispatchers.IO) { ... }
 		CoroutineScope(Dispatchers.IO).launch { ... } // same
 		...
 		val jb = GlobalScope.launch { ... }
 		jb.join()
 		jb.cancel()
 		... 
 		GlobalScope.async { ... }
 		GlobalScope.async (start = CoroutineStart.LAZY) { ... }
 		...
 		// For Android Activities and Fragments
 		lifecycleScope.launch { ... } 
		...
 		// e.g.: 
		job = GlobalScope.launch { 
			downloadImages("https://server1.fhj.at/..")
			processImages()
			createThumbs()
			... }
		job.join()
		```
		
	* Optionally, coroutines can be started in a special **context** with one of following **CoroutineDispatchers**:
		* ```Default``` <= CPU intensive work
		* ```IO``` <= NW, FS, DB
		* ```Main``` <= use the main thread, e.g. because the UI must be updated!
		* ```Unconfined``` <= do not use
	* I.e. coroutine might be executed not in the main thread, but within thread pools.
	* Coroutines can easily be cancelled. E.g. be getting *out-of-scope* or manually with ```job.cancel```.

* **Deferred objects**, i.e. Wait for results using `async` and `await` or `awaitAll`:

	```kotlin
	coroutineScope {
		val f = async { ... }
		val g = async { ... }
		...
		g.await()
		f.await() 
		...
		val deferreds = listOf(
			async { f() },  
			async { g() }  
		)
		deferreds.awaitAll()
		...
	}	 			
	```



* Caveats
	* In coroutine use ```delay()```, not ```Thread.sleep()``` 
	* When using ```async``` we will explicitly ```await``` the result.
	* When using ```launch``` the *await* happens implicitly.

	* 	As coroutines can **only** be started within a **scope**, such as *runBlocking*, the statement `runBlocking{...}` is often used for scripts, but should be avoided in apps.
		* Details: the use of **runBlocking** in demos does NOT kill the script, i.e. to **wait until coroutines finish**. Careful: `runBlocking` **blocks the main thread**. Use inside `main` method. `runBlocking` returns the result of a coroutine.

			```kotlin
			runBlocking { ... }
			...
			runBlocking { 
				...
				coroutineScope {
					launch { ... }
					launch { ... } 
				}
				... 
			}
			```


## Other Language Features

* Inline functions
* Primary constructors
* Sealed classes, open classes
* Property delegation 
* Generics with 
	* invariance: <T>,
	* covariance: <out T>,
	* contravariance: <in T>
* **Smart casts** <= compiler knows that within if block a otherwise nullable variable can not be null: ```if (nullableVar is Type) {...```   
* Range expression
* String interpolation, multi-line strings
* Conditional as expressions
* Powerful collections: filter, map, fold/reduce
* Extension to classes: extension functions


## Not-so-nice/intuitive Features

* Companion objects
* Object expressions

## Kotlin and Java

* Interoperability with Java
	* E.g. with annotations: @JvmStatic, @JvmField


## Getting Started

* Checkout many, many [**Kotlin Code Snippets**](https://git-iit.fh-joanneum.at/omd/code-snippets/-/tree/master/kotlin) from <https://git-iit.fh-joanneum.at/omd/code-snippets>, i.e. a structured code snippet collection for multiple languages.
* <https://kotlinlang.org>
* [LanguageDocumentation]
* [Koans](https://play.kotlinlang.org/koans/Introduction/Hello,%20world!/Task.kt) in the online playground


[LanguageDocumentation]: <https://kotlinlang.org/docs/kotlin-docs.pdf>
[Coding-Conventions]: <https://kotlinlang.org/docs/reference/coding-conventions.html>
[JAVA-vs-Kotlin]: <https://kotlinlang.org/docs/reference/comparison-to-java.html>




--- 
#### [Next part: Tools](../Part-2-Tools/study-material--tools.md)

---


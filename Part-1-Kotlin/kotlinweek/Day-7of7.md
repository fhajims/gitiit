[Home](./README-KotlinWeek.md)

# Kotlin Week 7/7:

<https://pl.kotl.in/mRj-btXL2>. Open in private browser window for a fresh session. Copy the code below in a dedicated online playground or into an Android Studio Scratch file. Thx.

## Task **Coroutines**:


* *Note for Android Studio*: coroutines are provided by library: ```kotlinx.coroutines.*```, so add to your gradle config:
	
	```
	...
	dependencies {
	   implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.2'
	}
	...
	``` 

### Rewrite following code using idiomatic Kotlin:

*Optimise .... until reworked code runs concurrent (structured concurrency).*

Improve code by implementing concurreny with GlobalScope.launch, runBlocking, launch, coroutineScope, suspend fun, async, await, delay. (Why) should we replace ```Tread.sleep(..)``` with ```delay(..)```?


```kotlin
import kotlinx.coroutines.*

// Task: make syncrounous calls async (with coroutines)!
fun syncFetchJSON(what:String){
    println("Simulate loading doc $what...")
    Thread.sleep(1000L)
    println("Simulate loading doc $what. Done")
}
fun syncFetchTwoJSONDocsFromServer(){
    println("...The first sync call will be started...")
    syncFetchJSON("HolidayPOIs") 
    println("...The second sync call will be started...")
    syncFetchJSON("CommutingPOIs")
    println("INFO: Download Done")
}


```

Usage

```kotlin   
fun main() {
    println("Kotlin Week 7/7 – Coroutines: Please Improve (keyworkds: GlobalScope.launch, runBlocking, launch, coroutineScope, suspend fun, async, await, delay) ")
    syncFetchTwoJSONDocsFromServer()
}
```



[Back Home](./README-KotlinWeek.md)
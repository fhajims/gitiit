Kotlin Week 7/7: 

## Task **Coroutines**:


### A Possible Solution

```kotlin
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ")

println("Kotlin Week 7/7 – Coroutines: Please Improve (keyworkds: GlobalScope.launch, runBlocking, launch, coroutineScope, suspend fun, async, await) ")

suspend fun fetchJSON(what:String){
    println("   Simulate loading doc $what...")
    delay(1000L)
    println("   Simulate loading doc $what. Done")
}

suspend fun fetchTwoJSONDocsFromServer() =
    coroutineScope {

        /*
        println("...The first async call will be started...")
        val concurrentFetchNo1 = async { fetchJSON("HolidayPOIs") }
        println("...The second async call will be started...")
        val concurrentFetchNo2 = async { fetchJSON("CommutingPOIs") }
        concurrentFetchNo2.await()
        concurrentFetchNo1.await()
        */
        /* even better */
        val tasks = listOf(
            async { fetchJSON("HolidayPOIs") },
            async { fetchJSON("CommutingPOIs") }
        )
        println("...The two async call are started and will be awaited....")
        tasks.awaitAll()
        /* */

        println("...The two async calls have finished. I.e. all downloads done")
    }

println("INFO: This is a demo, so we might use runBlocking")
runBlocking {
    println("Starting up some async calls in a second...")
    launch {
        fetchTwoJSONDocsFromServer()
        println(" Finally, the two async calls have finished...")
    }
    println("All the async calls are started (and run in the background)")
}
println("INFO: after run of blocking routine.")
```

this will output:

> INFO: This is a demo, so we might use runBlocking
> 
> Starting up some async calls in a second...
> 
> All the async calls are started (and run in the background)
> 
> ...The two async call are started and will be awaited....
> 
>    Simulate loading doc HolidayPOIs...
> 
>    Simulate loading doc CommutingPOIs...
> 
>    Simulate loading doc HolidayPOIs. Done
> 
>    Simulate loading doc CommutingPOIs. Done
> 
> ...The two async calls have finished. I.e. all downloads done
> 
>  Finally, the two async calls have finished...
> 
> INFO: after run of blocking routine.




```kotlin

//
// Some explanations:
//

// About Coroutines/Context/Channels/Dispatcher/SharedState/Actor: GlobalScope, launch, runBlocking, async, withContext, send, receive, Dispatchers.Main, yield, AtomiInteger...Mutex()

// Info: coroutines (keywords launch, async .... channels, flow, select,... )
//       are "better" than async/await with Futures and promises (Kotlinlang claims)!!

// Execute code with coroutines ... NEVER BLOCK block the main thread:
//
// normal functions: invoke, return
// coroutines: invoke, return, suspend (pause execution, save context = local variables), resume (continue)
//
// Dispatchers.Default <= cpu intensive work (outside  main thread)
// Dispatchers.IO <= disk, network operations (outside main thread)
// Dispatchers.Main <= Interact with UI (Android) (ON main thread

// coroutines are provided by library: "kotlinx.coroutines"... i.e. in gradle use:
// =>
// dependencies {
//    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.2'
// }


// TODO 
/*
println("Kotlin Week 7/7 – Coroutines: Solution 7a Synchronise with Channels")
println("Kotlin Week 7/7 – Coroutines: Solution 7b Async")
println("Kotlin Week 7/7 – Coroutines: Solution 7b Yield")
*/

```


// To compile this Kotlin source file 
// on the commandline to Java bytecode 
// and run the bytecode within a JVM (Java Virtual Machine)
// we are including the coroutines library
// => see task7of7.sh


//
// // Note for Android Studio:
//
// coroutines are provided by library: "kotlinx.coroutines"... i.e. in gradle use:
// =>
// dependencies {
//    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4'
// }
//
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

fun main() {
  println("Kotlin Week 7/7 – Coroutines: Please Improve (keyworkds: GlobalScope.launch, runBlocking, launch, coroutineScope, suspend fun, async, await, delay) ")
  syncFetchTwoJSONDocsFromServer()
}


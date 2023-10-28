#!/usr/bin/env kotlin

// run this Kotlin script on the commandline: 
//    kotlin task1of7.kts
// or
//    chmod +x *.kts
//    ./task1of7.kts


class SecureTheIntData(t:Int) {
    var internalDataStore:Int = t
}
class SecureTheStringData(s:String) {
    var internalDataStore:String = s
}

fun main() {
    println("Kotlin Week 6/7 – Generics: Please Improve")
    val secureIntegerDataStore:SecureTheIntData = SecureTheIntData(1)
    val secureStringDataStore:SecureTheStringData = SecureTheStringData("mypassword")

    println(" => $secureIntegerDataStore and $secureStringDataStore")
}

main()
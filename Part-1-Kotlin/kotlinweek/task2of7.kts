#!/usr/bin/env kotlin

// run this Kotlin script on the commandline: 
//    kotlin task1of7.kts
// or
//    chmod +x *.kts
//    ./task1of7.kts

data class Location(val area:String, val country:String)
data class Photo(val title: String, val location:Location)
fun main() {
    println("Kotlin Week 2/7 – Functional: Please Improve")
    
    // collection of environment sins:
    val slideshow = listOf<Photo>(
        Photo("Oil Sands",Location("orinoco","Venezuela") ),
        Photo("Polluted Water",Location("ganges","India")),
        Photo("Palm Oil Plantations", Location("??", "Malaysia")),
        Photo("Burning Rainforest",Location("Amazon","??")),
        Photo("Radioactive Waste Dumping",Location("??","Somalia"))
    )
    // TODO: output filtered (by first letter P) sorted (descending) list of (unique) countries
    
    // bad solution: WHY?
    var c = 'Z'
    while (c >= 'A') {
        // print("$c ");
        --c
        for (photo in slideshow) {
            if (photo.location.country.slice(0..0) == c.toString() // crashes, if...?!
                &&
                photo.title[0] == 'P') { // dangerous, if title is ...?!
                println( photo.location.country )
            }
        }
    }
    println("Kotlin Week 2/7 – Functional: Solution 2a Filter title for letter P (keywords: its)")
    println("Kotlin Week 2/7 – Functional: Solution 2b Sort by contry (descending, ignore case)")
    println("Kotlin Week 2/7 – Functional: Solution 2c modify values (keywords: map)")
    println("Kotlin Week 2/7 – Functional: Solution 2d Unique countries (keywords: set, sortedSet)")

}
main()
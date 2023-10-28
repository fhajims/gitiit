[Home](./README-KotlinWeek.md)

# Kotlin Week 2/7: 

<https://pl.kotl.in/So-OjLOdK>. Open in private browser window for a fresh session. Copy the code below in a dedicated online playground or into an Android Studio Scratch file. Thx.

## Task **output filtered, sorted, unique list of 'P'-photos contries**:

### Rewrite following code using idiomatic Kotlin:

*Optimise .... until reworked code is only about 7 to 12 lines long.*

Classes and demo data to work with

```
data class Location(val area:String, val country:String)
data class Photo(val title: String, val location:Location)

val slideshow = listOf<Photo>(
    Photo("Oil Sands",Location("orinoco","Venezuela") ),
    Photo("Polluted Water",Location("ganges","India")),
    Photo("Palm Oil Plantations", Location("??", "Malaysia")),
    Photo("Burning Rainforest",Location("Amazon","??")),
    Photo("Radioactive Waste Dumping",Location("??","Somalia"))
)
```

Improve the usage (pretty output of slides of the slideshow):

```kotlin
fun main() {
	println("Kotlin Week 2/7 – Functional Code: Please Understand (filter, sort, it, map, set, reversed) and then Improve (< 7 LOC)")
	
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
	print("Kotlin Week 2/7 – Functional: Solution 2a Filter title for letter P (keywords: its)")
	print("Kotlin Week 2/7 – Functional: Solution 2b Sort by contry (descending, ignore case)")
	print("Kotlin Week 2/7 – Functional: Solution 2c modify values (keywords: map)")
	print("Kotlin Week 2/7 – Functional: Solution 2d Unique countries (keywords: set, sortedSet)")
}
```


[Day 3: Lazy Properties](./Day-2of7.md)
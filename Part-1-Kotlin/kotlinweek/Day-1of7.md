[Home](./README-KotlinWeek.md)

# Kotlin Week  1/7: 

<https://pl.kotl.in/SxIqKne0Y>. Open in private browser window for a fresh session. Copy the code below in a dedicated online playground or into an Android Studio Scratch file. Thx.

## Task **design, create and output List of POIs**:

### Rewrite following code using idiomatic Kotlin:

*Optimise .... until reworked code is only about 10 to 15 lines long.*

Improve classes

```kotlin
import kotlin.Float.Companion.NEGATIVE_INFINITY

class GPS constructor(note:String = "unknown location") {
    private var longitude:Float = (-1.0).toFloat()
    private var latitude:Float = NEGATIVE_INFINITY
    private var note:String
    init {
        this.longitude = 0F
        this.latitude = 0f
        this.note = note
    }
    fun setLng(lng:Float):GPS { longitude = lng; return this}
    fun setLat(lat:Float):GPS { latitude = lat; return this}
    fun getNote():String{ return this.note }
    override fun toString():String{ return "" + this.latitude.toString() + "°N / " + this.latitude.toString() + "° E" }
}

class SingletonTrackPrinter {
    companion object { // no "static" keyword in Kotlin
        private var inst:SingletonTrackPrinter? = null
        fun getInstance():SingletonTrackPrinter?{
            if (inst==null) inst = SingletonTrackPrinter()
            return inst
        }
    }
    fun print(pois:List<GPS>){
       for (poi in pois) println( poi.getNote() + ": " + poi.toString() )
    }
}

```

Improve the usage, the creating and printing of (a list of) instances:

```kotlin

fun main() {
    println("Kotlin Week 1/7 – Shorter Code: Please Understand (primary constructor, init, companion object) and then Improve (< 15 LOC)")
    val home:GPS = GPS("@Home");
    home.setLat(47.4433F)
    home.setLng(15.2792F)
    var myTrack: List<GPS> = mutableListOf(home, GPS("City center").setLng(48f).setLat(15.3F), home)
    
    val stp = SingletonTrackPrinter.getInstance()
    if (stp != null) { stp.print(myTrack) }
    
    print("KotlinWeek Day 1/7 – Shorter Code: Solution 1a Singleton (threadsafe) for the TrackPrinter (keywords: object)\n")
    print("KotlinWeek Day 1/7 – Shorter Code: Solution 1b Data Class (keywords: default values, named arguments)\n")
    print("KotlinWeek Day 1/7 – Shorter Code: Solution 1c Shorter with string interpolation (keywords: dollar-sign)\n")
}
```

[Day 2: Functional](./Day-2of7.md)
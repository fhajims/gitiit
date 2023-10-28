Kotlin Week 1/7: 

## Task: Design, Create and Output List of POIs


### A Possible Solution

```kotlin
data class Poi_v2(val lng: Float, val lat: Float,val note:String = "unknown")

object TrackPrinter_v2{
    fun printPathOf(pois:List<Poi_v2>) = print(pois.map { "${it.note}: ${it.lat}° N / ${it.lng}° E" })
}

TrackPrinter_v2.printPathOf( listOf<Poi_v2>( 
    Poi_v2(15.2792F,47.4433F, "Home"), 
    Poi_v2(15.3F,47.5F) ) )
```
#!/usr/bin/env kotlin

// run this Kotlin script on the commandline: 
//    kotlin task1of7.kts
// or
//    chmod +x *.kts
//    ./task1of7.kts 

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


fun main() {
    println("Kotlin Week 1/7 – Shorter Code: Please Understand (primary constructor, init, companion object) and then Improve (< 15 LOC)")
	val home:GPS = GPS("@Home");
	home.setLat(47.4433F)
	home.setLng(15.2792F)
	var myTrack: List<GPS> = mutableListOf(home, GPS("City center").setLng(48f).setLat(15.3F), home)
    val stp = SingletonTrackPrinter.getInstance()
	if (stp != null) { stp.print(myTrack) }
    print("Kotlin Week 1/7 – Shorter Code: Solution 1a Singleton (threadsafe) for the TrackPrinter (keywords: object)\n")
	print("Kotlin Week 1/7 – Shorter Code: Solution 1b Data Class (keywords: default values, named arguments)\n")
	print("Kotlin Week 1/7 – Shorter Code: Solution 1c Shorter with string interpolation (keywords: dollar-sign)\n")

}
main()
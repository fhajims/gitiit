package at.fhj.omd.android.slideshow08b.service

import android.content.SharedPreferences
import android.util.Log
import at.fhj.omd.android.slideshow08b.model.GPS
import at.fhj.omd.android.slideshow08b.model.Slide
import kotlin.properties.Delegates


object SlideShowService {
  var refToShardPreferences:SharedPreferences? = null

  private var slides = mutableListOf<Slide>()


  private var currSlide:Slide? by Delegates.observable(null) { property, oldValue, newValue ->
    Log.d("SLIDE-SERVICE","observing $property: changes from $oldValue to $newValue")
    // save to prefs (if you like)
  }

  fun lastShownOrFirstSlide():Slide?{
    currSlide = getSlideById(
              refToShardPreferences?.getInt("LAST_SLIDE_ID",0))

    return currSlide ?: getNextSlide()
  }

  // Note: currently we use addSlide only within this class
  //       later you might want to make this public
  private fun addSlide(aNewSlide:Slide){
    slides.add(aNewSlide)
  }

//  fun getLastSlide():Slide?{
//    currSlide = slides.last()
//    return this.currSlide
//  }
  private fun getSlideById(id:Int?):Slide?{
    if (id==null) return null

    val slidesWithID = slides.filter { s -> s.id == id }
    if (slidesWithID.isEmpty()) return null
    return slidesWithID.first()
  }
  fun getNextSlide():Slide?{
    if ( slides.isEmpty() ) return null

    currSlide = when (currSlide) {
      is Slide -> {
        when (currSlide) {
          slides.last() -> slides.first()
          else -> {
            val idx = slides.indexOf(currSlide) + 1
            slides.elementAt(idx)
          }
        }
      }
      else -> slides.first()
    }
    return currSlide
  }

  fun addSomeDemoSlides(){
    // (2) User Location
    // find proper GPS values at: https://www.map-gps-coordinates.com
    addSlide( Slide(77,"Beautiful sunset", "sunset", GPS(40.0,23.5)))
    addSlide( Slide(-2,"Enjoy the beach", "water",GPS(14.90,23.51)))
    addSlide( Slide(-22,"More Coffee!!", "coffee",GPS(22.9,43.15)))
    addSlide( Slide(33,"Sand and more", "sand",GPS(31.95,115.86)))
  }

}


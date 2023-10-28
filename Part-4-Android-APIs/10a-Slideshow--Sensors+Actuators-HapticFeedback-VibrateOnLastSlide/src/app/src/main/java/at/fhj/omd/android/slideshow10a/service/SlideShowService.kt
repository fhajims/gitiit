package at.fhj.omd.android.slideshow10a.service

import android.content.SharedPreferences
import android.util.Log
import at.fhj.omd.android.slideshow10a.model.Slide
import kotlin.properties.Delegates


object SlideShowService {
  var refToShardPreferences:SharedPreferences? = null

  private var slides = mutableListOf<Slide>()

  // Discussion:
  //   is storing at every change too often?
  //   => Better store on life-cycle events only?
  //      TODO decide: SlideShow-fragment or -activity

  private var currSlide:Slide? by Delegates.observable(null) { property, oldValue, newValue ->
    Log.d("SLIDE-SERVICE","observing $property: changes from $oldValue to $newValue")

    // if (refToShardPreferences != null){
    // check out let apply run ... at
    // https://kotlinlang.org/docs/scope-functions.html
    refToShardPreferences?.let { sp ->
      with (sp.edit()){
        when (val cs = currSlide) {
          is Slide -> this?.putInt("LAST_SLIDE_ID", cs.id)
          else -> this?.remove("LAST_SLIDE_ID")
        }
        this?.apply()
      }
    }
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
    addSlide( Slide(77,"Beautiful sunset", "sunset"))
    addSlide( Slide(-2,"Enjoy the beach", "water"))
    addSlide( Slide(-22,"More Coffee!!", "coffee"))
    addSlide( Slide(33,"Sand and more", "sand"))
  }

}


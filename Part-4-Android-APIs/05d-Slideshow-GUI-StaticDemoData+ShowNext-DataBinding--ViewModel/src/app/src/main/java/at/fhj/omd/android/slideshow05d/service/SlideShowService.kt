package at.fhj.omd.android.slideshow05d.service

import at.fhj.omd.android.slideshow05d.model.Slide


object SlideShowService {
  private var slides = mutableListOf<Slide>()
  private var currSlide:Slide? = null

  // Note: currently we use addSlide only within this class
  //       later you might want to make this public
  private fun addSlide(aNewSlide:Slide){
    slides.add(aNewSlide)
  }

  fun getNextSlide():Slide?{
    if (slides.size == 0) return null

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
    addSlide( Slide(-2,"Sand and more", "sand"))
  }

}


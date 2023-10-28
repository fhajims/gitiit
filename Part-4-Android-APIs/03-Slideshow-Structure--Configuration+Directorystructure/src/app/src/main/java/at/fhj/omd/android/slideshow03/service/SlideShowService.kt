package at.fhj.omd.android.slideshow03.service

// 13.
import at.fhj.omd.android.slideshow03.model.Slide

// 12.
object SlideShowService {
  // 13.
  var slides = mutableListOf<Slide>()

  // 14.
  // TODO: addSlide is currently just used internally,
  //       late you might like to make it public
  private fun addSlide(aNewSlide:Slide){
    slides.add(aNewSlide)
  }

  // 15. will be replaced later
  fun addSomeDemoSlides(){
    addSlide( Slide(-2,"More Coffee!!", "coffee"))
    addSlide( Slide(77,"Beautiful sunset", "sunset"))
    addSlide( Slide(-2,"Enjoy the beach", "beach"))
    addSlide( Slide(-2,"Sand and more", "sand"))
  }
}
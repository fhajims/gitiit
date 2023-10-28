package at.fhj.omd.android.slideshow05a.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import at.fhj.omd.android.slideshow05a.R

import android.util.Log
import at.fhj.omd.android.slideshow05a.model.Slide
import at.fhj.omd.android.slideshow05a.service.SlideShowService

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class SlideshowFragment : Fragment() {

  private var slideImgView:ImageView? = null

  private val holidaySlides = SlideShowService

  override fun onCreateView(
          inflater: LayoutInflater, container: ViewGroup?,
          savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_slideshow, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)


    view.findViewById<Button>(R.id.button_show_about).setOnClickListener {
      // Log.i("GUI-Slideshow-Fragment","The button 'button_show_about has id: ${R.id.button_show_about} on view ${view}")
      findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
    }



    slideImgView = view.findViewById(R.id.imageView)
    slideImgView?.setOnClickListener {
      showNextSlide()
    }

    holidaySlides.addSomeDemoSlides()


    val firstSlide = holidaySlides.getNextSlide()

    updateUIWithSlide(firstSlide)

  }

  private fun updateUIWithSlide(slide:Slide?){
    val resID = when (slide) {
      is Slide -> {
        when (slide.imageName) {
          "water" -> R.drawable.water
          in arrayOf("sun", "sunset", "evening") -> R.drawable.sunset
          "sand" -> R.drawable.sand
          else -> R.drawable.no_image_for_slide
        }
      }
      else -> R.drawable.no_slides
    }
    slideImgView?.setImageResource(resID)
  }

  private fun showNextSlide(){
    Log.i("MAIN", "Show the next slide")
    val nextSlide:Slide? = holidaySlides.getNextSlide()
    if (nextSlide is Slide){
      updateUIWithSlide(nextSlide)
    }
  }
}
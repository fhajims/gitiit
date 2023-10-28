package at.fhj.omd.android.slideshow10a.ui

import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import at.fhj.omd.android.slideshow10a.R
import at.fhj.omd.android.slideshow10a.model.Slide
import at.fhj.omd.android.slideshow10a.service.SlideShowService


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

    slideImgView = view.findViewById(R.id.imageView)
    slideImgView?.setOnClickListener {
      showNextSlide()
    }

    holidaySlides.addSomeDemoSlides()

    // start where we left off:
    // make holidaySlides service remember shared prefs
    holidaySlides.refToShardPreferences = context?.getSharedPreferences(
      "SLIDES_PREFS",
      Context.MODE_PRIVATE
    )
    val lastShownOrFirstSlide = holidaySlides.lastShownOrFirstSlide()

    updateUIWithSlide(lastShownOrFirstSlide)

  }

  private fun updateUIWithSlide(slide: Slide?){
    val resID = when (slide) {
      is Slide -> {
        when (slide.imageName) {
          "water" -> R.drawable.water
          in arrayOf("sun", "sunset", "evening") -> R.drawable.sunset
          "sand" -> R.drawable.sand
          else -> {
            vibrateOnWarning()
            R.drawable.no_image_for_slide
          }
        }
      }
      else -> R.drawable.no_slides
    }
    slideImgView?.setImageResource(resID)
  }

  // set permissions in manifest, otherwise:
  // java.lang.SecurityException: Requires VIBRATE permission
  private fun vibrateOnWarning() {
    val vibrationEngine = context?.getSystemService(VIBRATOR_SERVICE) as? Vibrator
    val effect = VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE)
    vibrationEngine?.vibrate(effect)
  }

  private fun showNextSlide(){
    Log.i("MAIN", "Show the next slide")
    val nextSlide:Slide? = holidaySlides.getNextSlide()
    if (nextSlide is Slide){
      updateUIWithSlide(nextSlide)
    }
  }

}
package at.fhj.omd.android.slideshow03.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import at.fhj.omd.android.slideshow03.R

// 4.
import android.util.Log
import at.fhj.omd.android.slideshow03.model.Slide
import at.fhj.omd.android.slideshow03.service.SlideShowService

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class SlideshowFragment : Fragment() {

  // 1.
  private var slideImgView:ImageView? = null

  // 6.... see =>  10., 11., 12.... 15. (Slide & Slideshow)
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

    view.findViewById<Button>(R.id.button_first).setOnClickListener {
      findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
    }
    // 2.
    slideImgView = view.findViewById(R.id.imageView)
    slideImgView?.setOnClickListener {
      showNextSlide()
    }

    // 16.
    holidaySlides.addSomeDemoSlides()

    // 17.
    val firstSlide = holidaySlides.slides.first()
    Log.i("MAIN", "Callback 'On view created': We have to decide which slide to be shown. Currently (not optimal?) always: $firstSlide")
    updateUIWithSlide(firstSlide)
  }

  private fun updateUIWithSlide(slide:Slide){
    Log.i("MAIN", "UpdateUIWithSlide. Select proper resource for given slide (here: ${slide}) - not implemented yet")
    // val imgResId = //.. TODO
    // slideImgView?.setImageResource(imgResId)
    slideImgView?.setImageResource(R.drawable.water)
  }

  // 18.

  // 3.
  private fun showNextSlide(){
      // 5.
      Log.i("MAIN", "Show the next slide - not implemented yet")
  }
}
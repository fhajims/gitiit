package at.fhj.omd.android.slideshow05b.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import at.fhj.omd.android.slideshow05b.R

import android.util.Log
import at.fhj.omd.android.slideshow05b.model.Slide
import at.fhj.omd.android.slideshow05b.service.SlideShowService

/* on the commandline you might send now:
   adb -s emulator-5554  shell am broadcast -a "at.fhj.slideshow.JUMP_LAST"
   adb -s emulator-5554  shell am broadcast -a "at.fhj.slideshow.FILTER" -e "TAG" "snow"
*/

const val SLIDES_JUMP_FIRST = "at.fhj.slideshow.JUMP_FIRST"
const val SLIDES_JUMP_LAST = "at.fhj.slideshow.JUMP_LAST"
const val SLIDES_FILTER = "at.fhj.slideshow.FILTER"


class SlideshowFragment : Fragment() {

  private val slideShowReceiver = object : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
      Log.w("SLIDESHOW_NOTIFICATION","we got something")
      when (intent.action) {
        SLIDES_JUMP_FIRST -> {
          Log.e("SLIDESHOW_NOTIFICATION","jump to first slide now... NOT IMPLEMENTED YET")
          // TODO updateUIWithSlide(SlideShowService.getFirstSlide())
        }
        SLIDES_JUMP_LAST -> {
          Log.w("SLIDESHOW_NOTIFICATION", "jump to last slide now...")
          val lastSlide = SlideShowService.getLastSlide()
          updateUIWithSlide(lastSlide)
        }
        SLIDES_FILTER -> {
          val filterTag = intent.getStringExtra("TAG")
          Log.w("SLIDESHOW_NOTIFICATION", "filter slides with tag '$filterTag' now...")
        }
      }
    }
  }

  private var slideImgView:ImageView? = null

  private val holidaySlides = SlideShowService

  override fun onCreateView(
          inflater: LayoutInflater, container: ViewGroup?,
          savedInstanceState: Bundle?
  ): View? {

    context?.registerReceiver(slideShowReceiver, IntentFilter(SLIDES_JUMP_FIRST))
    context?.registerReceiver(slideShowReceiver, IntentFilter(SLIDES_JUMP_LAST))
    context?.registerReceiver(slideShowReceiver, IntentFilter(SLIDES_FILTER))

    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_slideshow, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    view.findViewById<Button>(R.id.button_show_about).setOnClickListener {
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

  override fun onDestroyView() {
    context?.unregisterReceiver(slideShowReceiver)
    super.onDestroyView()
  }
}
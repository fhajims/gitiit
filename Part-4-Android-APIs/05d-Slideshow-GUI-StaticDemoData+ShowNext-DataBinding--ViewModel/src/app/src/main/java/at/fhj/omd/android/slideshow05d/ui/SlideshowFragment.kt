package at.fhj.omd.android.slideshow05d.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import at.fhj.omd.android.slideshow05d.R
import at.fhj.omd.android.slideshow05d.databinding.FragmentSlideshowBinding
import at.fhj.omd.android.slideshow05d.hideKeyboard
import at.fhj.omd.android.slideshow05d.model.Slide
import at.fhj.omd.android.slideshow05d.service.SlideShowService
import kotlinx.coroutines.*

/*
* Add for View binding in the module build.gradle:
    buildFeatures {
        viewBinding true
    }
*
* */
class SlideshowFragment : Fragment() {

  // Notes on View Binding:
  // If view binding is enabled for a module,
  //    a binding class is generated for each XML layout file that the module contains...
  //    here: layout/fragment_slideshow.xml
  //    here: => FragmentSlideshowBinding
  // you need to import:
  //    here: import at.fhj.omd.android.slideshow05d.databinding.FragmentSlideshowBinding
  //    check: ..../app/build/generated/data_binding_base_class_source_out/debug/out/at/fhj/omd/android/slideshow05d/databinding/FragmentSlideshowBinding.java
  // The generated binding class is called FragmentSlideshowBinding,...
  // see: https://developer.android.com/topic/libraries/view-binding#kotlin

  private var _binding: FragmentSlideshowBinding? = null
  // This property is only valid between
  // onCreateView and onDestroyView.
  private val binding get() = _binding!!


  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    // Inflate the layout for this fragment
    _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
    val view = binding.root
    Log.i("BINDING","We set the view as binding root: $view")
    return view
  }

  override fun onDestroyView() {
    _binding = null
    super.onDestroyView()
  }



  // conventional way of accessing ui elements
  // see findViewById(..) below
  private var slideImgView:ImageView? = null

  // Using the service (which is a singleton, an 'object')
  private val holidaySlides = SlideShowService

  private var _timeCoroutine: Job? = null

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    // we can access UI elements only
    // because we know the "ids"
    //   (find "timeout" in fragment_slideshow.xml)
    // No need for "findViewByID(..)" anymore
    binding.timeout.text = getString(R.string.timeout_button_text)
    binding.timeout.setOnClickListener {
      Log.i("BINDING","Clicked on Timeout. Reset counter to max")
      _timeCoroutine?.cancel()
    }

    binding.pause.doAfterTextChanged {
      _timeCoroutine?.cancel()
      reStartTimer()

      // see file "Utilities.kt" for extensions
      hideKeyboard()
    }


    // conventional way of accessing ui elements
    // see declaration of private var 'slideImgView' above
    slideImgView = view.findViewById(R.id.imageView)
    slideImgView?.setOnClickListener {
      showNextSlide()
    }

    // using the "global" service (here: fill with data)
    holidaySlides.addSomeDemoSlides()

    // Update UI after modifying/setting local variable
    val firstSlide = holidaySlides.getNextSlide()
    updateUIWithSlide(firstSlide)

    // reStartTimer()
  }

  private fun updateUIWithSlide(slide: Slide?){
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
      reStartTimer()
    }
  }
  private fun reStartTimer(){
    _timeCoroutine?.cancel()
    _timeCoroutine = lifecycleScope.launch {
      var totalSeconds = 3
      binding.pause.text?.let{
        if (it.isEmpty()) return@launch

        totalSeconds = Integer.parseInt( it.toString() )
      }
        // if (binding.pause?.text != null) Integer.parseInt(binding.pause.text ) else 10
      val tickSeconds = 1
      for (second in totalSeconds downTo tickSeconds) {
        binding.timeout.text = "◎".repeat(second)
        delay(1000)
      }
      binding.timeout.text = getString(R.string.timeout_button_text_done)
      showNextSlide()
    }
  }
}
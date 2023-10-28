package at.fhj.omd.android.slideshow04.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import at.fhj.omd.android.slideshow04.R

import android.util.Log
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import at.fhj.omd.android.slideshow04.model.Slide
import at.fhj.omd.android.slideshow04.service.SlideShowService
import kotlinx.coroutines.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class SlideshowFragment : Fragment() {
  private var titleLabel:TextView? = null
  private var slideImgView:ImageView? = null

  private val holidaySlides = SlideShowService

  override fun onCreateView(
          inflater: LayoutInflater, container: ViewGroup?,
          savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_slideshow, container, false)
  }

  private fun updateUIWithSlide(slide: Slide?){
    if (slide !is Slide) { return  }

    Log.i("MAIN", "UpdateUIWithSlide. Select proper resource for given slide (here: $slide) - not implemented yet")
    // val imgResId = //.. TODO
    // slideImgView?.setImageResource(imgResId)
    slideImgView?.setImageResource(R.drawable.water)
  }
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    titleLabel = view.findViewById(R.id.textview_slideshow_title)

    view.findViewById<Button>(R.id.button_show_about).setOnClickListener {
      findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
    }
    slideImgView = view.findViewById(R.id.imageView)
    slideImgView?.setOnClickListener {
      showNextSlide()
    }

    slideImgView?.setImageResource(R.drawable.sand)

    // create demo slides, then try to
    //    sort, filter, add, update, ...
    holidaySlides.addSomeDemoSlides()

    // TODO: decide which slide to show on Life-cycle method 'onViewCreated'
    val firstSlide = holidaySlides.getNextSlide()
    updateUIWithSlide(firstSlide)
  }
  private var aPrime:Long = 99L

  private fun isPrime(number: Long): Boolean {
    if(number<2) return false
    for (i in 2.toLong()..number/2) {
      if (number % i == 0.toLong()) {
        return false
      }
    }
    return true
  }

  // @UiThread
  @SuppressLint("SetTextI18n")
  private fun updateUIFromUIThreadOnly(newValue:Long) {
    titleLabel?.text = "Slideshow $newValue"
  }


  // @WorkerThread
  private fun longLastingTaskFindingPrime():Long {
    val primes: MutableList<Long> = ArrayList()
    for (p in 123456L..398765L) {
      if (isPrime(p)) {
        primes.add(p)
        aPrime = p
        // Skipped 148 frames!  The application may be doing too much work on its main thread.
        // so, we use "launch" (or "async" if you want to wait/use results)


        //val deferred = GlobalScope.launch {
        GlobalScope.launch {
          updateUIFromUIThreadOnly(p)
        }
        // deferred.await().whateverYouWaitFor
      }
    }
    return primes.last()
  }
  private fun showNextSlide(){
    Log.i("MAIN", "Show the next slide. Not implemented yet")

    // Demo long lasting tasks (execute in background)
    // GlobalScope.launch(){...} application global
    lifecycleScope.launchWhenResumed {
      updateUIFromUIThreadOnly(0L)
      // suspend this coroutine for one second
      delay(3_000)
      Log.i("COROUTINE", "(2) Log-output after pausing for three sec.")
      val tasksWithTimeout = launch{
        // TODO: newSingleThreadContext .... deprecated
        //      read about the problems: https://github.com/Kotlin/kotlinx.coroutines/issues/261
        //launch(newSingleThreadContext("ExecuteInACustomThread")) {
        // same with: launch( newFixedThreadPoolContext(5,"AThreadPool")) {
        launch {
          aPrime = longLastingTaskFindingPrime()
          Log.i("COROUTINE","Found $aPrime")
        }
        launch {
          delay(1_500)
          Log.i("COROUTINE","How far are we after 1.5 secs: $aPrime")
          delay(1_000)
          Log.i("COROUTINE","How far are we after 2.5 secs: $aPrime")
        }
      }
      delay(2_000)
      tasksWithTimeout.cancel() // will cancel 2nd coroutine (but not the one with the custom thread)
      Log.i("COROUTINE", "(3) Log-output after pausing for another two sec.")
    }
    Log.i("COROUTINE", "(1) suspendable function is started.")
    Log.i("COROUTINE", "(1b) now try to pause by changing to About fragment.. and resume later...")
  }

  override fun onResume() {
    super.onResume()
    Log.d("MAIN", "Fragment live-cycle: resumed")
    // TODO: update the prime found on the UI now
    //       NOT using variable aPrime, because... (recreated)
    // GlobalScope.async {
      // updateUIFromUIThreadOnly(aPrime)
    // }
  }

  override fun onPause() {
    Log.i("MAIN", "Fragment live-cycle: pausing")
    super.onPause()
  }
}
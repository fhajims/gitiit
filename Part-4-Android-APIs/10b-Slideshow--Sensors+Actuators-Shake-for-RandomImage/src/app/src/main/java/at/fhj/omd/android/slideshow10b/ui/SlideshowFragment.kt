package at.fhj.omd.android.slideshow10b.ui

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.content.Context.VIBRATOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import at.fhj.omd.android.slideshow10b.R
import at.fhj.omd.android.slideshow10b.model.Slide
import at.fhj.omd.android.slideshow10b.service.SlideShowService
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt


object ShakeDetector{
  private const val THRESHOLD:Float = 7f
  private const val DELAY:Long = 800_000_000 // in nano, i.e. 0.8 sec.
  private var lastAcc:Float = SensorManager.GRAVITY_EARTH
  private var currAcc:Float = SensorManager.GRAVITY_EARTH
  private var lastTime:Long = 0

  fun isPhoneShaken(evt:SensorEvent):Boolean{
    // Log.d("SENSOR","TIME-DIFF ${evt.timestamp- _lastTime} ${(evt.timestamp- _lastTime) < at.fhj.omd.android.slideshow10b.ui.ShakeDetector.DELAY}")
    if ((evt.timestamp - lastTime) < DELAY ) return false


    val (x,y,z) = evt.values
    lastAcc = currAcc
    currAcc = sqrt(x.pow(2)+y.pow(2)+z.pow(2))
    val delta = abs(currAcc - lastAcc)
    //Log.d("SENSOR:", "D ${delta} (current ${_currAcc} - last ${_lastAcc}) ${evt.values[0]}")
    val shaken = delta > THRESHOLD
    if (shaken) {
      lastTime = evt.timestamp
    }
    return shaken
  }
}
class SlideshowFragment : Fragment() {


  private var slideImgView:ImageView? = null

  private val holidaySlides = SlideShowService

  private var _sensorManager:SensorManager? = null

  // Hint: Also check out the TriggerEventListener if that suites your needs better
  private val _sensorEventListener = object: SensorEventListener {
    override fun onSensorChanged(event: SensorEvent?) {
      event ?: return

      if ( ShakeDetector.isPhoneShaken(event) ){
        Log.i("SENSOR","Significant sensor input. User has shaken the phone. Proceed to next slide...")
        showNextSlide()
      }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
      Log.i("SENSOR","Ignoring accuracy changed event for sensor: $sensor. New accuracy: $accuracy")
    }

  }
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    _sensorManager = context?.getSystemService(SENSOR_SERVICE) as SensorManager


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


  override fun onResume() {
    super.onResume()
    val sensor:Sensor? = _sensorManager?.getDefaultSensor(
            Sensor.TYPE_ACCELEROMETER)

    _sensorManager?.registerListener(
            _sensorEventListener,
            sensor,
            SensorManager.SENSOR_DELAY_UI)
  }

  override fun onPause() {
    _sensorManager?.unregisterListener(_sensorEventListener)
    super.onPause()
  }
}
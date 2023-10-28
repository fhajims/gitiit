package at.fhj.omd.android.slideshow08b.ui

// do not forget:
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Context.*
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.motion.widget.Debug.getLocation
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import at.fhj.omd.android.slideshow08b.R
import at.fhj.omd.android.slideshow08b.R.id.textViewDistance
import at.fhj.omd.android.slideshow08b.model.GPS
import at.fhj.omd.android.slideshow08b.model.Slide
import at.fhj.omd.android.slideshow08b.service.SlideShowService
import org.osmdroid.config.Configuration.*
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView

private const val PERMISSION_REQUEST_ACCESS_FINE_LOCATION = 100

class SlideshowFragment : Fragment() {

  private var slideImgView:ImageView? = null

  private val holidaySlides = SlideShowService

  // (1) Map
  // private var  map:MapView? = null
  private lateinit var map : MapView

  // (2) User Location
  private var locationManager: LocationManager? = null
  private lateinit var tvDistance: TextView

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_slideshow, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    // (1) MAP
    map = view.findViewById(R.id.osmmap)
    map.setTileSource(TileSourceFactory.MAPNIK)

    // (2) User Location
    locationManager = context?.getSystemService(LOCATION_SERVICE) as LocationManager
    tvDistance = view.findViewById(textViewDistance)

    // (1) MAP
    // If you get error:
    // E/OsmDroid: Please configure a relevant user agent; current value is: osmdroid
    // OpenStreetMapTileProviderConstants.setUserAgentValue(BuildConfig.APPLICATION_ID)
    // Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID)
    // ...
    // =>
    getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context))


    slideImgView = view.findViewById(R.id.imageView)
    slideImgView?.setOnClickListener {
      showNextSlide()
    }

    holidaySlides.addSomeDemoSlides()

    // start where we left off:
    // make holidaySlides service remember shared prefs
    holidaySlides.refToShardPreferences = context?.getSharedPreferences(
      "SLIDES_PREFS",
      MODE_PRIVATE
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
            R.drawable.no_image_for_slide
          }
        }
      }
      else -> R.drawable.no_slides
    }
    slideImgView?.setImageResource(resID)
  }

  // no need to translate numeric content of the text
  @SuppressLint("SetTextI18n")
  private fun showNextSlide(){
    Log.i("MAIN", "Show the next slide")
    val nextSlide:Slide? = holidaySlides.getNextSlide()
    if (nextSlide is Slide){
      updateUIWithSlide(nextSlide)

      if (nextSlide.location is GPS){
        // (2)
        val dist = calculateDistanceInKM(nextSlide)
        Log.i("GPS", "Current distance: $dist")
        tvDistance.text = """${dist ?: '-'} km"""


        // (1)
        val mapController = map.controller
        mapController.setZoom(5.5)
        val startPoint = GeoPoint(nextSlide.location.latitude, nextSlide.location.longitude)
        mapController.setCenter(startPoint)
      }
    }
  }


  // Manifest:
  //  <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />


  // To work with user location:
  // GPS / Location based services:


  private var locationListener = object : LocationListener {
    override fun onLocationChanged(location: Location) {
      val latitude = location.latitude
      val longitude= location.longitude

      Log.i("GPS", "Current loc changed to Latitude: $latitude ; Longitude: $longitude")

    }
    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
      Log.i("GPS", "Current provider status changed $provider")
    }
  }




  private fun calculateDistanceInKM(slide: Slide):Float?{
    val ctx = context
    if (ctx !is Context) { return null }

    val act = this.activity
    if (act !is Activity) { return null }


    val destLoc = slide.location
    if (destLoc !is GPS) { return null }


    // (2) User Location
    if (ContextCompat.checkSelfPermission(
        ctx,
        Manifest.permission.ACCESS_FINE_LOCATION
      )
      != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(
        act,
        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
        PERMISSION_REQUEST_ACCESS_FINE_LOCATION
      )
      return null
    }



    // (2) poll current GPS location
    val currentLocation = locationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
    if (currentLocation !is Location){return null}

    Log.i("GPS", "Current location is: $currentLocation")
    val results = FloatArray(1)
    // World Geodetic System (WGS)
    Location.distanceBetween(
      currentLocation.latitude,
      currentLocation.longitude,
      destLoc.latitude,
      destLoc.longitude,results)
    return results.first()/1000 // in km
  }



  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
  ) {
    Log.i("GPS","User granted permissions with code: $requestCode")
    if (requestCode == PERMISSION_REQUEST_ACCESS_FINE_LOCATION) {
      when (grantResults[0]) {
        PackageManager.PERMISSION_GRANTED -> {
          val gl = getLocation()
          Log.i("GPS","Permissions granted => $gl")
        }
        PackageManager.PERMISSION_DENIED -> {
          //Tell user to grant permission
          Toast.makeText(
            context,
            "Error getting location. Check permissions and try again!",
            Toast.LENGTH_SHORT
          ).show()
        }
      }
    }
  }

  override fun onResume() {
    super.onResume()
    map.onResume()


    // (2) setup callback for GPS changes
    try {
      locationManager?.requestLocationUpdates(
        LocationManager.GPS_PROVIDER,
        0L,
        0f,
        locationListener
      )
    } catch (ex: SecurityException) {
      Toast.makeText(
        context,
        "Error getting location. Check permissions and try again!",
        Toast.LENGTH_SHORT
      ).show()
    }


  }

  override fun onPause() {
    map.onPause()
    locationManager?.removeUpdates(locationListener)
    super.onPause()
  }
}



package at.fhj.omd.android.slideshow05b.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import at.fhj.omd.android.slideshow05b.R


/*
  For live-cycle events of this fragment
  View in Logcat the log output of the app
  filter "verbose" and match text "SLIDE-FRAGMENT"
  While watching the log output:
    Try to move between "About" and "Slideshow" fragments back and forth
    Try to open the settings activity (from menu) and move forth and back.
    Try to got to the home screen and finally, kill the app.
 */

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AboutFragment : Fragment() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Log.v("SLIDE-FRAGMENT-LIFE-CYCLE","State-1a: 1a=onCreate,1b=OnCreateView,1c=onViewCreated,2=onStart,3=onResume,4=onPause,5a=onStop,5b=onSaveInstanceState,6a=onDestroyView,6b=onDestroy")
  }
  override fun onCreateView(
          inflater: LayoutInflater, container: ViewGroup?,
          savedInstanceState: Bundle?
  ): View? {
    Log.v("SLIDE-FRAGMENT-LIFE-CYCLE","State-1b: 1a=onCreate,1b=OnCreateView,1c=onViewCreated,2=onStart,3=onResume,4=onPause,5a=onStop,5b=onSaveInstanceState,6a=onDestroyView,6b=onDestroy")
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_about, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    Log.v("SLIDE-FRAGMENT-LIFE-CYCLE","State-1c: 1a=onCreate,1b=OnCreateView,1c=onViewCreated,2=onStart,3=onResume,4=onPause,5a=onStop,5b=onSaveInstanceState,6a=onDestroyView,6b=onDestroy")
    view.findViewById<Button>(R.id.button_back_to_slideshow).setOnClickListener {
      findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
    }
  }

  override fun onStart() {
    super.onStart()
    Log.v("SLIDE-FRAGMENT-LIFE-CYCLE","State-2 : 1a=onCreate,1b=OnCreateView,1c=onViewCreated,2=onStart,3=onResume,4=onPause,5a=onStop,5b=onSaveInstanceState,6a=onDestroyView,6b=onDestroy")
  }

  override fun onResume() {
    super.onResume()
    Log.v("SLIDE-FRAGMENT-LIFE-CYCLE","State-3 : 1a=onCreate,1b=OnCreateView,1c=onViewCreated,2=onStart,3=onResume,4=onPause,5a=onStop,5b=onSaveInstanceState,6a=onDestroyView,6b=onDestroy")
  }

  override fun onPause() {
    Log.v("SLIDE-FRAGMENT-LIFE-CYCLE","State-4 : 1a=onCreate,1b=OnCreateView,1c=onViewCreated,2=onStart,3=onResume,4=onPause,5a=onStop,5b=onSaveInstanceState,6a=onDestroyView,6b=onDestroy")
    super.onPause()
  }

  override fun onStop() {
    Log.v("SLIDE-FRAGMENT-LIFE-CYCLE","State-5a: 1a=onCreate,1b=OnCreateView,1c=onViewCreated,2=onStart,3=onResume,4=onPause,5a=onStop,5b=onSaveInstanceState,6a=onDestroyView,6b=onDestroy")
    super.onStop()
  }

  override fun onSaveInstanceState(outState: Bundle) {
    Log.v("SLIDE-FRAGMENT-LIFE-CYCLE","State-5b: 1a=onCreate,1b=OnCreateView,1c=onViewCreated,2=onStart,3=onResume,4=onPause,5a=onStop,5b=onSaveInstanceState,6a=onDestroyView,6b=onDestroy")
    super.onSaveInstanceState(outState)
  }

  override fun onDestroyView() {
    Log.v("SLIDE-FRAGMENT-LIFE-CYCLE","State-6a: 1a=onCreate,1b=OnCreateView,1c=onViewCreated,2=onStart,3=onResume,4=onPause,5a=onStop,5b=onSaveInstanceState,6a=onDestroyView,6b=onDestroy")
    super.onDestroyView()
  }

  override fun onDestroy() {
    Log.v("SLIDE-FRAGMENT-LIFE-CYCLE","State-6b: 1a=onCreate,1b=OnCreateView,1c=onViewCreated,2=onStart,3=onResume,4=onPause,5a=onStop,5b=onSaveInstanceState,6a=onDestroyView,6b=onDestroy")
    super.onDestroy()
  }
}
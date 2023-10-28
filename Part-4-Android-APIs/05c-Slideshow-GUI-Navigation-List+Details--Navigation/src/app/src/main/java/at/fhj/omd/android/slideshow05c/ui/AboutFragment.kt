package at.fhj.omd.android.slideshow05c.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import at.fhj.omd.android.slideshow05c.R

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AboutFragment : Fragment() {

  override fun onCreateView(
          inflater: LayoutInflater, container: ViewGroup?,
          savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_about, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    view.findViewById<Button>(R.id.button_back_to_slideshow).setOnClickListener {
      findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
    }
  }
}
package at.fhj.omd.android.slideshow05d

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

//class Utilities {

    /*
    * Trick to hide the (soft) keyboard
    * with NEW method hideKeyboard()
    * see:
    * https://dev.to/rohitjakhar/hide-keyboard-in-android-using-kotlin-in-20-second-18gp
    * */

  fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
  }

//  fun Activity.hideKeyboard() {
//    hideKeyboard(currentFocus ?: View(this))
//  }

  fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
  }
//}
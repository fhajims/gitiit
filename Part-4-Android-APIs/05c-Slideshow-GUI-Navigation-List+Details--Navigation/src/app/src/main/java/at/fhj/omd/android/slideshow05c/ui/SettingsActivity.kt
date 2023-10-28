package at.fhj.omd.android.slideshow05c.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import at.fhj.omd.android.slideshow05c.R


private const val TITLE_TAG = "settingsActivityTitle"

class SettingsActivity : AppCompatActivity(),
        PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.settings_activity)
    if (savedInstanceState == null) {
      supportFragmentManager
              .beginTransaction()
              .replace(R.id.settings, HeaderFragment())
              .commit()
    } else {
      title = savedInstanceState.getCharSequence(TITLE_TAG)
    }
    supportFragmentManager.addOnBackStackChangedListener {
      if (supportFragmentManager.backStackEntryCount == 0) {
        setTitle(R.string.title_activity_settings)
      }
    }
    supportActionBar?.setDisplayHomeAsUpEnabled(true)


    // we got some information from another activity
    val intent = intent
    val city = intent.getStringExtra("city")
    val zipcode = intent.getIntExtra("zipcode",0)
    Log.i("SETTINGS","Now set $zipcode for $city. ... Not implemented yet ")



  }

  override fun onBackPressed() {
    Log.i("SETTINGS","Back from settings to main...  ")

    val retIntent =  Intent()
    retIntent.putExtra( "retData", "The result data string")
    setResult(Activity.RESULT_OK, retIntent)
    super.onBackPressed()
  }




  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    // Save current activity title so we can set it again after a configuration change
    outState.putCharSequence(TITLE_TAG, title)
  }

  override fun onSupportNavigateUp(): Boolean {
    if (supportFragmentManager.popBackStackImmediate()) {
      return true
    }
    // close the activity
    finish()

    return super.onSupportNavigateUp()
  }

  override fun onPreferenceStartFragment(
    caller: PreferenceFragmentCompat,
    pref: Preference
  ): Boolean {
    // Instantiate the new Fragment
    val args = pref.extras
    val fragment = supportFragmentManager.fragmentFactory.instantiate(
      classLoader,
      pref.fragment
    ).apply {
      arguments = args
      setTargetFragment(caller, 0)
    }
    // Replace the existing Fragment with the new Fragment
    supportFragmentManager.beginTransaction()
            .replace(R.id.settings, fragment)
            .addToBackStack(null)
            .commit()
    title = pref.title
    return true
  }

  class HeaderFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
      setPreferencesFromResource(R.xml.header_preferences, rootKey)
    }
  }

  class MessagesFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
      setPreferencesFromResource(R.xml.messages_preferences, rootKey)
    }
  }

  class SyncFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
      setPreferencesFromResource(R.xml.sync_preferences, rootKey)
    }
  }
}
package at.fhj.omd.android.slideshow05b.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import at.fhj.omd.android.slideshow05b.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar



/*
  For live-cycle events
  View in Logcat the log output of the app
  filter "verbose" and match text "SLIDE-ACTIVITY"
  While watching the log output:
    Try to open the settings activity (from menu) and move forth and back.
    Try to got to the home screen and finally, kill the app.
 */

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Log.v("SLIDE-ACTIVITY-LIFE-CYCLE","State-1 : 1=onCreate,2a=onStart,2b=onRestart,3=onResume,4=onPause,5=onStop,6=onDestroy")
    setContentView(R.layout.activity_main)
    setSupportActionBar(findViewById(R.id.toolbar))

    findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
      Snackbar.make(view, "Dear user, please click on the image to display the next slide ...", Snackbar.LENGTH_LONG)
              .setAction("Action", null).show()
    }
  }

  override fun onStart() {
    super.onStart()
    Log.v("SLIDE-ACTIVITY-LIFE-CYCLE","State-2a: 1=onCreate,2a=onStart,2b=onRestart,3=onResume,4=onPause,5=onStop,6=onDestroy")
  }


  override fun onRestart() {
    super.onRestart()
    Log.v("SLIDE-ACTIVITY-LIFE-CYCLE","State-2b: 1=onCreate,2a=onStart,2b=onRestart,3=onResume,4=onPause,5=onStop,6=onDestroy")
  }

  override fun onResume() {
    super.onResume()
    Log.v("SLIDE-ACTIVITY-LIFE-CYCLE","State-3 : 1=onCreate,2a=onStart,2b=onRestart,3=onResume,4=onPause,5=onStop,6=onDestroy")
  }

  override fun onPause() {
    Log.v("SLIDE-ACTIVITY-LIFE-CYCLE","State-4 : 1=onCreate,2a=onStart,2b=onRestart,3=onResume,4=onPause,5=onStop,6=onDestroy")
    super.onPause()
  }
  override fun onStop() {
    Log.v("SLIDE-ACTIVITY-LIFE-CYCLE","State-5 : 1=onCreate,2a=onStart,2b=onRestart,3=onResume,4=onPause,5=onStop,6=onDestroy")
    super.onStop()
  }

  override fun onDestroy() {
    Log.v("SLIDE-ACTIVITY-LIFE-CYCLE","State-6 : 1=onCreate,2a=onStart,2b=onRestart,3=onResume,4=onPause,5=onStop,6=onDestroy")
    super.onDestroy()
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    // Inflate the menu; this adds items to the action bar if it is present.
    menuInflater.inflate(R.menu.menu_main, menu)
    return true
  }


  /*
   // see
   // https://developer.android.com/training/basics/intents/result
   // needs in build.gradle (for Module):
   //       implementation 'androidx.activity:activity-ktx:1.2.2'
   //       implementation 'androidx.fragment:fragment-ktx:1.3.2'
   */
  private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
    if (result.resultCode == Activity.RESULT_OK) {
      val intent = result.data
      if (intent is Intent){
        if (intent.hasExtra("retData")){
          val getValue = intent.getStringExtra("retData")
          Log.i("MENU","we got return value '$getValue'")
        }else{
          Log.i("MENU","we got no known return values")
        }
      }else{
        Log.i("MENU","we got no return values")
      }
    }
  }


  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    return when (item.itemId) {
      R.id.action_settings -> {
        Log.d("MENU", "Settings clicked. Not implemented yet.")

        val intent = Intent(this, SettingsActivity::class.java)
        intent.putExtra("city", "Kapfenberg")
        intent.putExtra("zipcode", 8605)

        /*
        startActivity(intent)
        */

        // modern way:
         startForResult.launch(intent)
        /**/




        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }
}
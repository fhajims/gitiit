package at.fhj.omd.android.slideshow05c.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import at.fhj.omd.android.slideshow05c.R
import at.fhj.omd.android.slideshow05c.ui.manage.TagListActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(findViewById(R.id.toolbar))

    findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
      Snackbar.make(view, "Dear user, please click on the image to display the next slide ...", Snackbar.LENGTH_LONG)
              .setAction("Action", null).show()
    }
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
  /* or
  val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { retDataStr:String? ->
    // Handle the returned retDataStr
    Log.i("MENU","we got return value $retDataStr")
  }
  */

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    return when (item.itemId) {
      R.id.action_settings -> {
        Log.d("MENU", "Settings clicked.")

        val intent = Intent(this, SettingsActivity::class.java)
        intent.putExtra("city", "Kapfenberg")
        intent.putExtra("zipcode", 8605)

         startForResult.launch(intent)
         true
      }
      R.id.action_manage -> {
        Log.d("MENU", "Manage clicked.")
        val intent = Intent(this, TagListActivity::class.java).also {
          it.putExtra("selectedSlideID", 23)
          startActivity(it)
        }
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }
}
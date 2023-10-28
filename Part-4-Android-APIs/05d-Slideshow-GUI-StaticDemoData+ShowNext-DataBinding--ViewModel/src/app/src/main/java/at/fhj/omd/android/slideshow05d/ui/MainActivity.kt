package at.fhj.omd.android.slideshow05d.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import at.fhj.omd.android.slideshow05d.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(findViewById(R.id.toolbar))

    findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
      Snackbar.make(view,
              "Set slideshow timer up to 9 seconds. \n" +
              "...and check out view binding in code...",
              Snackbar.LENGTH_LONG)
              .setAction("Action", null).show()
    }
  }


}
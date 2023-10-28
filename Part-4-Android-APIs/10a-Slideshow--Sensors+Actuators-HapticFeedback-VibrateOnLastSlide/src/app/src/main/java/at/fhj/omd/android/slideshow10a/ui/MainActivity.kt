package at.fhj.omd.android.slideshow10a.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import at.fhj.omd.android.slideshow10a.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(findViewById(R.id.toolbar))

    findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
      Snackbar.make(view, "Dear user, note vibration on some of the slides ...", Snackbar.LENGTH_LONG)
              .setAction("Action", null).show()
    }


  }


}
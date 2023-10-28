package at.fhj.omd.android.slideshow08b.model

// e.g.: Vienna: GPS(48.20,16.37)
data class GPS(val latitude:Double = 0.0,val longitude:Double = 0.0)

data class Slide(
        val id:Int,
        var title:String = "untitled",
        var imageName:String = "dummy",
        val location:GPS? = null
)

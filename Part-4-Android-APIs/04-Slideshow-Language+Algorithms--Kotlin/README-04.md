[up](../study-material--android-apis.md)

# Part 4 - Step 4 Kotlin

## TL'DR: How to run the demo app in Android Studio

In Android Studio with the menu entry ```File/Open...``` select the subfolder ```src``` to be opened in ```New Window```. Or, just drag and drop the ```src``` folder onto your Android Studio Icon.


## Kotlin

Basics about the programming language Kotlin have been discussed in [Part 1 Kotlin](../../Part-1-Kotlin/study-material--kotlin.md). Using Kotlin the way it is meant to be used is called *idiomatic*. Examples of Kotlin usage for Android development with Android APIs is discussed here. 

## Idiomatic Kotlin

Use the language features provided by the programming language **Kotlin** the way it is designed. Avoid using outdated patterns of other languages. Avoid copy over existing code and *make it run*.

## Data Design

Start with data design *before* starting to code.


* Draft demo data. Provide a few (at least three) samples. 

	> Slide1: An image of the sunset at the beach (sunset.jpg)

	> Slide2: The dog jumps into the waves (waves.jpg)

	> Slide3: A white ship is passing by (boat.jpg)
	
* Draw the Flow of Data

	* You:

	> Create an empty slide show "On the beach"

	> Select first image from camera roll and add to slideshow

	> Add text to image and repeat adding more slides

	> Finish (which causes uploading image + text data to cloud)

	> Publish by "sharing" (send link to friends selected from contacts)

	* Your friend(s):
		
	> Check email and click on link which opens slideshow

	> Data is downloaded and presented
	
	* You and your friends:

	> Swipe forth and back to select another slide
	
	> Messages are sent to each other (broadcast) about currently selected slide number.

	> When message from friend arrives (in the background) another slide is presented (with a smooth transition).

Check: Would this set of data work with the paper prototype? 

* Design data structure

	> Slideshow: id, title, list-of-slides, 
	
	> SingleSlide: id, Slideshow-id, title, image-url (optionally: a comment and stars for ranking)


	Preview to Kotlin code:
	
	```
	data class Slide(
		val id:Int,
		var title:String = "Untitled",
		val imageName:String = "dummy.jpg" ){
   	/// ...
   }
	
	```

## Selected Examples: 

* Background tasks with Kotlin **Coroutines**.

	* Add dependencies to your module gradle script

	```
	...
	dependencies {
		...
		implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3"
	    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.2"
	}
	```

	* Import required functionality, e.g.
	
	```
	...
	import kotlinx.coroutines.delay
	```

	* Create a context, then run code (```suspend fun xxx(){...}```) in codeblocks which can be suspended and resumed.

	```
	```

* Implementing services using Kotlin **Singletons**.


[Next part (5a GUI: Show static images)](../05a-Slideshow-GUI-LayoutWithStaticImagesView--ActivityIntentsFragments/README-05a.md)


---

*This is the README-04.md of <https://git-iit.fh-joanneum.at/Feine/omd-droid-devel/Part-4-Android-APIs>.*
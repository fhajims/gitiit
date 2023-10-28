[up](../study-material--android-apis.md)

# Step 05c Navigation

## TL'DR: How to run the demo app in Android Studio

In Android Studio with the menu entry ```File/Open...``` select the subfolder ```src``` to be opened in ```New Window```. Or, just drag and drop the ```src``` folder onto your Android Studio Icon.

## Navigate to other Activities

* First, create another activity. E.g. an Activity called ```TagListActivity```:


	```kotlin
	class TagListActivity : AppCompatActivity() { ...
	```

* Then, setup an **intent** and call **startActivity** with parameter of the created intent:

	```kotlin
	val intent = Intent(this, TagListActivity::class.java).also {
	          startActivity(it)
	}
	```

## Pass data to other Activities

* Optionally, one might pass parameters, by using **putExtra** to the intent before starting the activity.

	```kotlin
	val intent = Intent(this, TagListActivity::class.java).also {
	  it.putExtra("selectedSlideID", 23)
	  startActivity(it)
	}
	```
## Call Activity and Get Some Result-Value(s)

* First register listener on result, then start the intent


	```kotlin
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
	```
	
	```kotlin
	val intent = Intent(....)
	startForResult.launch(intent)
	```
## Closing an Activity

* Use **finish()** to close the activity and return to the previous one:

	```kotlin
   finish()
	```

## Navigate Between Fragments 

* A fragment manager can replace parts (called **fragments**) of the activity.

	* Prepare — optionally with arguments (here ```ARG_ITEM_ID```) — and initialise a fragment:

		
		```kotlin
		val fragment = TagDetailFragment()		```
	
	* Replace current fragment with another one. This is possible with the FragmentManager using transactions (allows to add multiple fragments side by side at once for example):

		```kotlin
		parentActivity.supportFragmentManager
			.beginTransaction()
			.replace(R.id.tag_detail_container, fragment)
			.commit()
		```

## Pass Data Between Fragments
	
* Data might be passed from one fragment to another.

	* Prepare — optionally with arguments (here ```ARG_ITEM_ID```) — and initialise a fragment:

		
		```kotlin
		val fragment = TagDetailFragment().apply {
			arguments = Bundle().apply {
				putString(TagDetailFragment.ARG_ITEM_ID, item.id)
			}
		}
		```
		
		
	* Replace current fragment with another one as shown above.
		
	* The target fragment can read the argument here ```ARG_ITEM_ID```) passed in the ```onCreate(...)``` callback:

		```kotlin
		override fun onCreate(savedInstanceState: Bundle?) {
			super.onCreate(savedInstanceState)

				arguments?.let {
					if (it.containsKey(ARG_ITEM_ID)) {
						// ...
						// .. = it.getString(ARG_ITEM_ID)
						// ...
						...
		```

[Next part: Part 4 - Step 5d Data Binding](../05d-Slideshow-GUI-StaticDemoData+ShowNext-DataBinding--ViewModel/README-05d.md)


---

*This is the README-05c.md of <https://git-iit.fh-joanneum.at/Feine/omd-droid-devel/Part-4-Android-APIs>.*
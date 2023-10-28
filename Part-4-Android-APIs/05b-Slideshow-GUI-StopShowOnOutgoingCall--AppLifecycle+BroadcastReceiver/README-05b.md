[up](../study-material--android-apis.md)

The Android Lifecycle and Broadcast Receivers.

# Step 05b The Android Lifecycle

## TL'DR: How to run the demo app in Android Studio

In Android Studio with the menu entry ```File/Open...``` select the subfolder ```src``` to be opened in ```New Window```. Or, just drag and drop the ```src``` folder onto your Android Studio Icon.

## Lifecycle

* [Activity Lifecycle](https://developer.android.com/guide/components/activities/activity-lifecycle)
	* Callbacks (methods to override)
		* onCreate
		* onStart / onRestart
		* onResume
		* >**RUNNING**
		* onPause
		* onStop
		* onDestroy
	
	![Activity lifecycle](https://developer.android.com/guide/components/images/activity_lifecycle.png)
* [Fragment Livecycle](https://developer.android.com/guide/fragments/lifecycle)
	* First, a *frame manager* attaches a frame (callback ```onAttach()```)	
	* Then, use callbacks (methods to override): 
		* onCreate, onCreateView, onViewCreated
		* onStart
		* onResume ... <= i.e., visible and **ready for user input**
		* onPause 
		* onStop, onSaveInstanceState()
		* onDestroyView, onDestroy

	![Fragment lifecycle](https://developer.android.com/images/guide/fragments/fragment-view-lifecycle.png)

 
 
# Step 05b Broadcast Receivers
 
* Set up Broadcast Receiver for **explicit intents**:

	* When starting the (e.g. main) activity
		* First invent some shortcuts for the intents, such as ```at.fhj.slideshow.FILTER```:
			
		```
		const val SLIDES_JUMP_FIRST = "at.fhj.slideshow.JUMP_FIRST"
		const val SLIDES_...
		const val SLIDES_FILTER = "at.fhj.slideshow.FILTER"

		```
		* Create the intent - callback:

		```
		private val slideShowReceiver = object : BroadcastReceiver() {
	    override fun onReceive(context: Context, intent: Intent) {
	        Log.w("SLIDESHOW_NOTIFICATION","we got something")
	        when (intent?.action) {
	          SLIDES_JUMP_FIRST -> Log.w("SLIDESHOW_NOTIFICATION","jump to first slide now...")
	          SLIDES_...
	          SLIDES_FILTER -> {
	            val filterTag = intent.getStringExtra("TAG")
	            Log.w("SLIDESHOW_NOTIFICATION", "filter slides with tag '$filterTag' now...")
	          }
	        }
	    }
	  }
			
		```
		
		* **Register** the intent on startup of the activity

		```
		override fun onCreate(savedInstanceState: Bundle?) {
    		super.onCreate(savedInstanceState)
			...
			registerReceiver(slideShowReceiver, IntentFilter(SLIDES_JUMP_FIRST))
			registerReceiver(slideShowReceiver, IntentFilter(SLIDES_...))
			registerReceiver(slideShowReceiver, IntentFilter(SLIDES_FILTER))
			...
		```
		* **Unregister** the intent on activity stop

		```
		override fun onStop() {
			unregisterReceiver(slideShowReceiver)
			super.onStop()
	  }
		```

* Testing intents
			
	* Try to send implicit and explicit intents from the commandline:

		* For **explicit intent**, use adb to send for the intent ```at.fhj.slideshow.JUMP_FIRST```:

		```
		adb -s emulator-5554  shell am broadcast -a "at.fhj.slideshow.JUMP_FIRST"
		```
		
		* For **explicit intent**, use adb to send for example key ```TAG``` with string value ```snow```:
		
		```
		adb -s emulator-5554  shell am broadcast -a "at.fhj.slideshow.FILTER" -e "TAG" "snow"
		```
	
		* For implicit intents, use telnet. First, find port the emulator listens

		```
		adb devices
		```

		> List of devices attached

		> 94WAY0TG87	device

		> emulator-5554	device
		 
		* Connect via ```telnet``` to emulator 

		```
		cat /Users/john/.emulator_console_auth_token
		```
		
		> FOoWn6zpXXXe6Jf8
		
		```
		telnet localhost 5554
		auth FOoWn6zpR8ge6Jf8
		```
		
		* Trigger device actions, such as sending sms, incoming calls, power connection and the like:
		
		```
		power ac on

		# try to send other events: 
		# help
		# sms send +438605987 "Visit the woods"
		# gsm call +438605987
		# power capacity 7
		# ... 
		```

[Next part: Part 4 - Step 05c Navigation](../05c-Slideshow-GUI-Navigation-List+Details--Navigation/README-05c.md)


---

*This is the README-05b.md of <https://git-iit.fh-joanneum.at/Feine/omd-droid-devel/Part-4-Android-APIs>.*
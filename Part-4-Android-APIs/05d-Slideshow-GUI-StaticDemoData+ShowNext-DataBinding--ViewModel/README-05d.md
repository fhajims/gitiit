[up](../study-material--android-apis.md)

# Step 05 Data Binding

## TL'DR: How to run the demo app in Android Studio

In Android Studio with the menu entry ```File/Open...``` select the subfolder ```src``` to be opened in ```New Window```. Or, just drag and drop the ```src``` folder onto your Android Studio Icon.

## General Information

Using a library to support data binding. Binding of a variable (in the background) to it's representation on the user interface (and vice-versa).

* Data binding
	* One-way: Changes on the GUI are reflected automatically in the internal (backend) data stores (but changes in the background do not automatically trigger the update of the presentation, the user interface).
	* Two-way: Changes on the graphical user interface (GUI) trigger changes in the backend and changes in the backend trigger update on the UI.	 


## Enable View Binding for Android Module

* Idea: we want NOT to write code for  ```findViewById.?(...)```
* Step-By-Step
	* In the ```build.gradle``` for the module set ```viewBinding true```:

		```json
		android {
		    ...
		    buildFeatures {
		        viewBinding true
		    }
		}
		```
	* **Code Generation** during build step creates e.g. for each element in layout ```fragment_slideshow.xml``` code in file ```FragmentSlideshowbinding.java```, so we must import e.g.: 

		```kotlin
		import at.fhj.omd.android.slideshow05d.databinding.FragmentSlideshowBinding
		```
		
		* For the curious: Inspect generated java file ```.../app/build/generated/data_binding_base_class_source_out/debug/out/at/fhj/omd/android/slideshow05d/databinding/ActivityMainBinding.java``` and find:

			```java
			...
			id = R.id.timeout;
			TextView timeout = rootView.findViewById(id);
			...
			```
	 	* Optionally, delete the java files and recreate them (e.g. in the *Gradle view* of Android Studio):

	 		```bash
	 		 ./gradlew tasks --all
	 		 #...
	 		 #app:dataBindingGenBaseClassesDebug
	 		 #...
	 		```
	
	* To access the ui elements, we create an internal variable (e.g. ```binding```):

		```kotlin
		private var _binding: FragmentSlideshowBinding? = null
		private val binding get() = _binding!!
		``` 
		
	* We initialise the new variable (e.g. ```binding``` when the view is created and set it to null on view destory:

		```kotlin
		override fun onCreateView(
			inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
			): View? {

			_binding = FragmentSlideshowBinding.inflate(inflater, container, false)
    		val view = binding.root
    		return view
		}
  
		override fun onDestroyView() {
			_binding = null
			super.onDestroyView()
		}
  		``` 
	
	* We can access UI elements, because we know the "ids" (e.g. variable ```timeout``` is defined in ```fragment_slideshow.xml```). No need for call ```findViewByID(..)``` anymore:

		```kotlin
		binding.timeout.text = 12.toString()
	   binding.timeout.setOnClickListener {
	      Log.i("BINDING","Clicked on Timeout. Reset counter to max")
	   }
   ```

   * One-way binding example of EditText element:

		```kotlin
		binding.pause.doAfterTextChanged {
      		Log.i("BINDING", "New value in pause element: ${binding.pause.text}")
			// do stuff with value
		}
		```

[Next part: Part 4 - Step 5g Save State](../05g-Slideshow-GUI-SaveState-CurrentSlide--LifecycleAware/README-05g.md)


---

*This is the README-05d.md of <https://git-iit.fh-joanneum.at/Feine/omd-droid-devel/Part-4-Android-APIs>.*
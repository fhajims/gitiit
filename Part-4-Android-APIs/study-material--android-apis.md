---

---
#### [Home](../README.md)

---


# Part 4 Android APIs

Modern Android Development (MAD) and selected step-by-step instructions for development using Android libraries and APIs.


## Part 4: Android APIs ##

### Designing / Structuring an App

Larger apps might be structured by features or might have a layered architecture. Structure by using modules. Modules can be loaded on demand.



### MAD

Use the Modern Android Development **MAD** design patterns / **architectural components**. MAD are layers, frameworks, libraries which work upon the basic Android functionality, as discussed in [Part 3 – Android Apps](../Part-3-Android-Apps/study-material--android-apps.md)). Components are **lifecycle aware** (discussed below).


Use the libraries of **Android Jetpack**.

* Requirement in `build.gradle.kts`:

	```kotlin
	...
	dependencies {
	    val lifecycle_version = "2.2.0"
	
	    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
	    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
	    ...
	}
	```


* **ViewModel**
	* Preserve (cache) app data, even when view changes (i.e. app rotates to landscape). *Advantage: No reload/fetch of data is necessary when UI configuration changes, e.g. navigate between fragments.*
	* Optionally, persist data between app recreation (start/stop).

		* Prepare a view model:

			```kotlin
			data class DiceUiState( ..)
			class DiceRollViewModel : ViewModel() {
				// Expose screen UI state
				private val _uiState = MutableStateFlow(DiceUiState())
				val uiState: StateFlow<DiceUiState> = _uiState.asStateFlow()
				
				// Handle business logic
				fun rollDice() {
					_uiState.update { .... 
				...
			}
			```
		* Create your view model (when activity is created) and write code how the UI should be updated with data from the model

			```kotlin
			class DiceRollActivity : AppCompatActivity() {
				override fun onCreate(...){
					val viewModel: DiceRollViewModel by viewModels()
					lifecycleScope.launch {
						repeatOnLifecycle(Lifecycle.State.STARTED) {
							viewModel.uiState.collect {
									...// Update UI elements
							}
						}
					}
				}
			}
			```

* **LiveData**
	* To observe data changes. Data objects notify UI elements (views) when underlying data/databases change. *Notes: no manual lifecycle handling required.*

		* Create (empty) instance of `LiveData` (to hold data)
	
			```kotlin
			class NameViewModel : ViewModel() {
	
				val currentName: MutableLiveData<String> by lazy {
					MutableLiveData<String>()
				}
			```
	
		* When activity is created, **create an observer**. Then **start observing**. 
	
			```kotlin
			class NameActivity : AppCompatActivity() {
				private val model: NameViewModel by viewModels()
				
				override fun onCreate(...){
					...
					val nameObserver = Observer<String> { newName ->
						nameTextView.text = newName
					}
					model.currentName.observe(this, nameObserver)
					...
				}
				```

* **DataBinding**
	* Bind **declarative** UI elements to variables (data sources) of your app.
		* No need for `findViewById`. Code is generated. 

			* Like always, define UI elements in XML

				```xml
				<LinearLayout ... >
				    <TextView android:id="@+id/username" />
					 ....
				    <Button android:id="@+id/confirmbutton" ... />
				</LinearLayout>
				```

			* During project compilation, the binding library creates code, i.e. variables (within `binding`) to access UI elements.

			
				```kotlin
				private lateinit var binding: ResultProfileBinding
				override fun onCreate(...) {
					..
				    binding = ResultProfileBinding.inflate(layoutInflater)
				    val view = binding.root
				    setContentView(view)
				}
				```
			
			* Use variables (here `username` and `confirmbutton` ) in code
		
				```kotlin
				binding.username.text = viewModel.name
				binding.confirmbutton.setOnClickListener { viewModel.userClickedConfirm() }
				```
		
	* Compare one-way and two-way data binding with `@={}` notation.

	 	```xml
	 	<CheckBox
		    android:id="@+id/rememberMeCheckBox"
		    android:checked="@={viewmodel.rememberMe}"
		/>
	 	```

* **Repository**
	* Persistence via O/R-Mapping for a local SQL database: Use **Room** and specify a *database*, *entities* and *DAOs*.
	
		```kotlin
		@Entity
		data class User(
		    @PrimaryKey val uid: Int,
		    @ColumnInfo(name = "first_name") val firstName: String?,
		    @ColumnInfo(name = "last_name") val lastName: String?
		)
		```
		
		```kotlin
		@Dao
		interface UserDao {
		    @Query("SELECT * FROM user")
		    fun getAll(): List<User>
		    ...
		```
		 
		```kotlin
		@Database(entities = [User::class], version = 1)
		abstract class AppDatabase : RoomDatabase() {
		    abstract fun userDao(): UserDao
		}
		```
		
		```kotlin
		val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
       ).build()
     val userDao = db.userDao()
		val users: List<User> = userDao.getAll()
		```
		
		
	* Access Remote Data Source (webservice): **Retrofit**

		```kotlin
		private val retrofit = Retrofit.Builder()
			.addConverterFactory(ScalarsConverterFactory.create())
			.baseUrl(BASE_URL)
			.build()
		```
		
		
		```kotlin
		val listResult = MarsApi.retrofitService.getPhotos()
		```


	





* **WorkManager**
	* For async tasks
	* Scheduled in the background

	```kotlin
	val continuation = WorkManager.getInstance(context)
		.beginUniqueWork( ...)
		.then(...)
		.then(...) 
	```


* **Dependency Injection** (DI)
	* **Hilt**

		* Define **one** container for the app

		
		```kotlin
		@HiltAndroidApp
		class ExampleApplication : Application() { ... }
		```

	* Where you want to use DI, annotate your custom (Application/ViewModel/Activity/Fragment/View/Service/BroadcastReceiver) classes, like:

		```kotlin
		@AndroidEntryPoint
		class ExampleActivity : AppCompatActivity() { ... }
		```
	
	* With the annotated class, annotate a variable to inject dependency:

	
		```kotlin
		@Inject lateinit var analytics: AnalyticsAdapter
		```
		
	* Define the bindings

		```kotlin
		class AnalyticsAdapter @Inject constructor(
		  private val service: AnalyticsService
		) { ... }
		```
		
	* The (single) object/instance/service you want to inject must be provided
		
		```kotlin
		interface class AnalyticsServiceImp { ... 
		class AnalyticsServiceImp ... (): class AnalyticsServiceImp { ...} 
		...
		``` 
	* **Dagger** with auto-code-generation to manage complex dependencies.

		

	
* **Navigation**
	* Manage the navigation flow within the app. 
	* Define/create a navigaton graph for **one single activity**.

	```xml
	<?xml version="1.0" encoding="utf-8"?>
	<navigation xmlns:android="..."
	            xmlns:app="..."
	            android:id="@+id/nav_graph">
	
		<fragment
			android:id="@+id/view_pager_fragment" ... >
			<action
				android:id="@+id/action_view_pager_fragment_to_plant_detail_fragment"
				app:destination="@id/plant_detail_fragment" ... />
		</fragment> 
	</navigation>
	```
	
	* Trigger navigation from Code. Note, a navigation host (Fragment/View/Activity) has a `NavController` with method `navigate`.

	```kotlin
	override fun onClick(v: View) {
    val amount: Float = ...
    val action =
        SpecifyAmountFragmentDirections
            .actionSpecifyAmountFragmentToConfirmationFragment(amount)
    v.findNavController().navigate(action)
}
	```

* **Declarative UI** 
	* Write UI in code with **Jetpack Compose**

	```kotlin
	@Composable
	fun PlantListItemView(plant: Plant, onClick: () -> Unit) {
 	...
	Text(
		text = plant.name,
		textAlign = TextAlign.Center,
		modifier = Modifier
			.fillMaxWidth()
			.padding(vertical = dimensionResource(id = R.dimen.margin_normal))
			.wrapContentWidth(Alignment.CenterHorizontally)
	)
	```

* Of course, the is much more:
	* *Paging* to load information gradually. Paging includes the use of *RecyclerView* adapters. 


*Note: It is important to note, that the architecture components (listed above) are components, which are lifecycle-aware. That means, code to react on lifecycle changes is written in other places. I.e. one can move code to react on lifecycle state changes away from the activities or fragments direct into the component.*


## Secure Coding

Use Kotlin (see [Part 1 Kotlin](../Part-1-Kotlin/study-material--kotlin.md)) it is more secure. For example, it provides **Null Safety**. Find more on secure coding in [Part 6 Security / Attacks and Mitigations](../Part-6-Attacks+Mitigations/study-material--attacks+mitigations.md).


## Step-by-step Tutorial

Starting with an app idea the development steps are explained step-by-step.


### Part 4 — Step 1 A Slideshow App

Why we need an app? Why we need a slideshow app?
	
Read [Part 4 - Step 1 Intro](./01-Intro/README-01.md).

### Part 4 — Step 2 Prototyping a Slideshow

How to spare many hours of rework? 

Read [Part 4 - Step 2 Prototyping](./02-Planning--PaperPrototypes/README-02.md).


### Part 4 — Step 3 Slideshow App-Structure and Incemental Development

How to setup the first app structure and then improve your app step by step?

Read [Part 4 - Step 3 App Structure and Incremental Development](./03-Slideshow-Structure--Configuration+Directorystructure/README-03.md). 


### Part 4 — Step 4 Idiomatic Kotlin for Coding Slideshow Functionality 

Use Idiomatic Kotlin for the data structures and the algorithms during Slideshow Develoment. Provide some holiday slides as demo data and search, filter, transform the given data set, e.g. the list of images. 

Read [Part 4 - Step 4 Idiomatic Kotlin](./04-Slideshow-Language+Algorithms--Kotlin/README-04.md).

### Part 4 — Step 5 GUI — User Interface of the Slideshow

Presenting slides and interacting with an slideshow app.

* [Part 4 - 5a Activities, Intents, Fragments](./05a-Slideshow-GUI-LayoutWithStaticImagesView--ActivityIntentsFragments/README-05a.md): Display static slideshow images
* [Part 4 - 5b Android App Life-cycle, Broadcast Receivers](./05b-Slideshow-GUI-StopShowOnOutgoingCall--AppLifecycle+BroadcastReceiver/README-05b.md): Use adb to broadcast slide number. Show slide with given number.
* [Part 4 - 5c Navigation](./05c-Slideshow-GUI-Navigation-List+Details--Navigation/README-05c.md): Show image details for a single slide 
* [Part 4 - 5d Data Binding](./05d-Slideshow-GUI-StaticDemoData+ShowNext-DataBinding--ViewModel/README-05d.md): Fewer code with binding variables to UI. Timer, Layout: Present slides as images with titles
* Part 4 - 5e Interaction: Allow users to rate a slide
* Part 4 - 5f Filter: Add functionality to show sunset imgages only
* [Part 4 - 5g Save State](./05g-Slideshow-GUI-SaveState-CurrentSlide--LifecycleAware/README-05g-save-state.md): Remember the last viewed slide when reopening the app


### Part 4 — Step 6 Fetch Data

Never block the user interface! Use concurrency to fetch data in the background.  


* 6a Do not block the UI, WorkManager: Load data in the background
* 6b Dependency Injection with Hilt and Dagger: Load data from data stores
* 6c Retrofit: Fetch data from remote web services

### Part 4 — Step 7 Caching for Performance (and UX)

* Room, Alarm Manager: cache data and check for update regularly.

### Part 4 — Step 8 Location Based Services

* Geo Location and Distance: Show location where photo was taken. 
* Location: Point of Interests (POIs), Distance 
* Map: Pins, Landmarks
* Detect **In range**

### Part 4 — Step 9 Components

Code features as reusable components. 

* Zoom animated components: rate slide with subtle blinking / pulsing stars

### Part 4 — Step 10 Sensors and Actuators

* Use the camera to take images.

### Part 4 — Step 11 Security

* Secure Hardware and Security APIs: use biometric authentication
* Secure Coding: refactor and improve code 

### Part 4 — Step 12 System Services

* Contact Store: get email of contact to share slides with.
* Content Provider: offer slides to other apps

### Part 4 — Step 13 Cloud

* GCM Cloud and Sync Services: synchronise comments via cloud

### Part 4 — Step 14 Notifications

Local and remote (push) notifications to remind the user of events.

* Local and/or remote Notifications: notify users that someone added a comment to a slide.


### Part 4 — Step 15 Machine Learning (ML)

* Machine Learning ML: auto catagorise an image and show matching category (sunset, beach, water)

### Part 4 — Step 16 Augmented Reality (AR)

* Augmented Reality (AR): project the slides onto a wall.


*....to be continued....*




--- 
#### [Next part: 5 Android System Architecture](../Part-5-Android-Sys/study-material--android-sys.md)

---


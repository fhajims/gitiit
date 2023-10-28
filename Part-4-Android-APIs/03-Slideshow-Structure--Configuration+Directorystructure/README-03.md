[up](../study-material--android-apis.md)

# Part 4 - Step 3 Setup App


## TL'DR: How to run the demo app in Android Studio

In Android Studio with the menu entry ```File/Open...``` select the subfolder ```src``` to be opened in ```New Window```. Or, just drag and drop the ```src``` folder onto your Android Studio Icon.

## First App, Setup App Structure

* Setup new project, e.g. Basic Activity

* Refactor default naming
	
	* E.g. ```FirstFragment``` -> ```SlideshowFragment```
	* E.g. ```SecondFragment``` -> ```AboutFragment```
	* E.g. in file ```values/string.xml```	

		```
		<string name="hello_first_fragment">Slideshow</string>
		```


* Update/Create App - Structure
	* Build configurations
		* e.g. *build.gradle*, *proguard-rules.pro*   
	* Static Resources
		* Configurations e.g. *AndroidManifest.xml*
		* Static assets such as images, sound e.g. *res* folder 
		* Translations (internationalisation, I18N)
	* General structure
		* structure by functionality 
		* optional: structure by module (split app, reusable components)
	* Model 
		* Descriptions of data format (data transfer objects DTO)
		* Mapping JSON to/from classes/structs
		* e.g.: *db/dao/UserDao.kt*
	* Services
		* Get data from / store data to internal / external systems
		* Interfaces/protocols 
			* e.g. how to store/log data locally
			* e.g. how to access web service APIs
			* e.g. *service/LocationService.kt*
		* classes with functionality: *managers*/*services*
			* E.g. Logging
			* E.g. Local data persistence (O/R-M), preferences
	* UI
		* I.e. nested folders for user interfaces with substructure
		* Main UI (at startup, not the login/register)
		* (Reusable) Dialogs 
			* e.g. Alerts
			* e.g. Notifications 
			* e.g. Sharing
		* Navigation (dynamic, depends on *state*)
			* e.g. stack/details
				* e.g. a list *ui/highscores/Highscore.kt*
				* e.g. detail *ui/highscores/HighscoreDetail.kt*
			* e.g. login unless logged-in, register unless registered
		* For each view
			* Lifecycle logic	*ui/highscores/HighscoreDetailFragment.kt*
			* Viewmodel logic *ui/highscores/HighscoreDetailFragment.kt*
	* Util
		* e.g. helper functionality
		* e.g. *util/helper.kt*
	* Main entry point
		* e.g. *MainActivity.kt* 

For example, in Android Studio select parent folder ```at.fhj.omd.android.slideshow03```, then :

> New/Package: ```model```, ```service```, ```ui```

		
* For each feature
	* Prepare (demo) data
		* E.g. drop images (```sunset.jpg```, ...) to ```res/drawable/``` folder 
	* Prepare GUI
		* Design / code user interface (UI)
			* E.g. add ```ImageView``` and select image ```sunset```. 
		* Without functionality
	
	* Use TestDrivenDevelopment TDD 
		* write simple test for a functionaliy (!)
		* provide test / dummy data
		* implement logic

	* Connect UI with logic
		* Input: connect gestures / taps / shake 
		* Output: update UI from (modified) data
		* Improve user experinece, provide user feedback 
			* missing/wrong input
			* update status
			* wait to process data (continuously update status)
			* optionally allow to abort long processes 

* Test
	* Prepare test cases with criteria (a user should accomplish ...)  
	* On real devices
	* With real users (optionally record usage)
	* Collect feedback	
	
* Improve :) 

* Deployment
	* App Store 


[Next part (Kotlin)](../04-Slideshow-Language+Algorithms--Kotlin/README-04.md)


---

*This is the README-03.md of <https://git-iit.fh-joanneum.at/Feine/omd-droid-devel/Part-4-Android-APIs>.*
[up](../study-material--android-apis.md)

Vibration Feedback with actuator

# Step 10a Haptic Feedback

* Permissions


```
<uses-permission android:name="android.permission.VIBRATE" />
```


```kotlin
import android.os.Vibrator;
```
```kotlin
val vibra = context?.getSystemService(VIBRATOR_SERVICE) as? Vibrator
val effect = VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE)
vibra?.vibrate(effect)
```

[Next part: Part 4 - Step 10b Shake Sensor](../10b-Slideshow--Sensors+Actuators-Shake-for-RandomImage/README-10b.md)


---

*This is the README-10a.md of <https://git-iit.fh-joanneum.at/Feine/omd-droid-devel/Part-4-Android-APIs>.*
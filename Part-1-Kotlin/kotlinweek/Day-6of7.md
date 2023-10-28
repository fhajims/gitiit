[Home](./README-KotlinWeek.md)

# Kotlin Week 6/7:

<https://pl.kotl.in/unyWYPv_4>. Open in private browser window for a fresh session. Copy the code below in a dedicated online playground or into an Android Studio Scratch file. Thx.

## Task **Generics**:

### Rewrite following code using idiomatic Kotlin:

*Optimise .... until reworked code is only one class (of about 3 lines).*

Improve code by implementing more Kotlin language features, such as string interpolation, smart casts, range expressions, companion objects and others.

```kotlin
class SecureTheIntData(t:Int) {
    var internalDataStore:Int = t
}
class SecureTheStringData(s:String) {
    var internalDataStore:String = s
}
```

Usage

```kotlin
fun main() {
    println("Kotlin Week 6/7 – Generics: Please Improve")
    val secureIntegerDataStore:SecureTheIntData = SecureTheIntData(1)
    val secureStringDataStore:SecureTheStringData = SecureTheStringData("mypassword")

    println(" => $secureIntegerDataStore and $secureStringDataStore")
}
```



[Day 7: Coroutines](./Day-7of7.md)
Kotlin Week 6/7: 

## Task **Generics**:


### A Possible Solution

```kotlin
class SecureData<T>(input:T) {
    var internalDataStore = input
}
```

or (if immutable is ok)


```kotlin
data class SecureTheData<T>(val internalDataStore: T)
```


Usage

```kotlin
fun main() {
    val secureIntegerDataStore = SecureData(1)
    val secureStringDataStore = SecureData("mypassword")

    println("${secureIntegerDataStore.internalDataStore} and ${secureStringDataStore.internalDataStore} => $secureIntegerDataStore and $secureStringDataStore")
}
```

Kotlin Week 2/7: 

## Task **output filtered, sorted, unique list of 'P'-photos contries**:


### A Possible Solution

```kotlin
println(slideshow.
            filter {it.title.startsWith("P")}.
            sortedByDescending { it.location.country.lowercase() }.
            map {it.location.country}.
            toSortedSet().
            reversed()
        )
````

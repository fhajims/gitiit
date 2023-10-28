[Home](./README-KotlinWeek.md)

# Kotlin Week 3/7: 

<https://pl.kotl.in/NTMMqDnPl>. Open in private browser window for a fresh session. Copy the code below in a dedicated online playground or into an Android Studio Scratch file. Thx.

## Task **Properties: Be lazy, observe and delegate**:

### Rewrite following code using idiomatic Kotlin:

*Optimise .... until reworked code is only about 20 lines long.*

Reduce code for classes by coding with lazy and observable properties

```kotlin
import java.util.Base64

data class Position(val x:Int, val y:Int)


class GamePlayer {
    private var currentPosition:Position = Position(0,0)
    private var name = "undefined"
    private var playerImage:ByteArray? = null

    constructor(name:String, startPosition:Position){
        this.name = name
        this.currentPosition=startPosition
    }

    fun getPlayerImage():ByteArray{
        if (this.playerImage == null) {
            println("LOG: Decoding the image")
            val base64Str:String = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mP8z/C/HgAGgwJ/lK3Q6wAAAABJRU5ErkJggg==" // load JSON from web service "${this.name}.jpg"
            this.playerImage = Base64.getDecoder().decode(base64Str)
        }
        return this.playerImage as ByteArray
    }

    public fun moveTo(newPosition:Position){
        val delta=Position((this.currentPosition.x-newPosition.x),(this.currentPosition.y-newPosition.y) )
        println("LOG: moved ${delta.x}/${delta.y} from ${this.currentPosition} to new position $newPosition")
        this.currentPosition = newPosition
    }
}
```

Improve also usage of instances insofar, as to make the code shorter:

```kotlin
fun main() {
    print("Kotlin Week 3/7 – Properties: Please Improve using lazy properties and observable attributes")

    val charles = GamePlayer("Charly", Position(10,10))
    charles.moveTo( Position(12,12) )
    charles.moveTo( Position(12,14) )
    
    println( charles.getPlayerImage() )

    println("Kotlin Week 3/7 – Properties: Solution 3a Lazy Properties (Keywords: by lazy)")
	println("Kotlin Week 3/7 – Properties: Solution 3b Property Delegation / Property Observer  (keywords: by Delegates.observable)")


}
```


[Day 4: Operator overloading](./Day-4of7.md)
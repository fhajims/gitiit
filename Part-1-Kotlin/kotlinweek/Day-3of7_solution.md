Kotlin Week 3/7: 

## Task **Properties: Be lazy, observe and delegate**:


### A Possible Solution

```kotlin
data class Pos(val x:Int, val y:Int)
class Player(private val name:String, private val startPosition:Pos){
    private val initialPos = Pos(0,0)
    private val playerImage:ByteArray by lazy {
        val base64Str:String = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mP8z/C/HgAGgwJ/lK3Q6wAAAABJRU5ErkJggg==" // load JSON from web service "${this.name}.jpg"
        Base64.getDecoder().decode(base64Str)
    }
    var pos:Pos by Delegates.observable (initialPos) { _ , prevPos, newPos ->
        val delta=Pos((prevPos.x-newPos.x),(prevPos.y-newPos.y) )
        println("LOG: player moved ${delta.x}/${delta.y} from $prevPos to $newPos")
    }
    init {
        this.pos = startPosition
    }
}
val charly = Player("Charly", Pos(10,10))
charly.pos = Pos(12,12)
charly.pos = Pos(12,14)
```

Kotlin Week 4/7: 

## Task **OOP Inheritance and Operator Overloading**:


### A Possible Solution

Improved classes

```kotlin
open class Shape(open var color: String) {
    open fun brighthenUp() {
        println("TO be implemented")
    }
}

class Emoji(override var color: String, val what: String) : Shape(color) {
    override fun brighthenUp() {
        this.color = "Light${this.color}"
    }
}

data class Piece(val name: String)

class TheGame(val name: String) {
    val pieces = mutableListOf<Piece>()
    fun addPiece(newPiece: Piece) {
        pieces.add(newPiece)
    }
    operator fun plusAssign(piece: Piece) {
        addPiece(piece)
    }
}

```

Improved usage

```kotlin
fun main() {
    println("Kotlin Week 4/7 – OOP: Solution 4b Operator Overloading")

    val smile = Emoji("Blue","Smile")
    smile.brighthenUp()

    val chess = TheGame("Chess")
    chess.addPiece(Piece("King"))
    chess += Piece("Queen")
    println("Players for ${chess.name}: ${chess.pieces}")
}
```

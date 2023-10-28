[Home](./README-KotlinWeek.md)

# Kotlin Week 4/7:

<https://pl.kotl.in/vyPlraC73>. Open in private browser window for a fresh session. Copy the code below in a dedicated online playground or into an Android Studio Scratch file. Thx.

## Task **OOP Inheritance and Operator Overloading**:

### Rewrite following code using idiomatic Kotlin:

*Optimise .... until reworked code is more powerful (alltogether, about 40 lines).*

Improve code to allow inheritance and provide selected operators (instead of methods) for shorter usage

```kotlin
class Shape (var color:String){
    fun brighthenUp(){
        println("TO be implemented")
    }
}

// Uncomment and make it work (probably you need to change the base class?)
// TODO: remove ERROR Shape is final, ...
// class Emoji(override var color:String, val what:String):Shape (color) {
    // TODO: remove Error: brighthenUp is final ...
    //override fun brighthenUp(){
    //    this.color = "Light${this.color}"
    //}
//}




class Piece (val name:String)

// Add operator overloading, e.g. for operator +=:
class TheGame(val name:String){
    val pieces = mutableListOf<Piece>()
    fun addPiece(newPiece:Piece) {
        pieces.add(newPiece)
    }
}

```

Improve also usage of instances by working with operators instead of method calls:

```kotlin
fun main() {
   print("Kotlin Week 4/7 – OOP: Provide a solution for inheriting classes")
	// TODO make this work
	// val smile = Emoji("Blue","Smile")
   // smile.brighthenUp()

   
   println("Kotlin Week 4/7 – OOP: Provide a solution for  operator overloading")

	val chess = TheGame("Chess")
	chess.addPiece( Piece("King") )
	//TODO make this work:
	// chess += Piece("Queen")
	println("Players for ${chess.name}: ${chess.pieces}")
}
```

*Important note: be aware of the differences of operators **add** "+" and **plusAssign** "+=". The first one must be a pure function without side effect. I.e. a + b MUST NOT modify the object a.*


[Day 5: Various Kotlin Language Features](./Day-5of7.md)
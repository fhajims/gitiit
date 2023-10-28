#!/usr/bin/env kotlin

// run this Kotlin script on the commandline: 
//    kotlin task1of7.kts
// or
//    chmod +x *.kts
//    ./task1of7.kts

// Task:
class Shape (var color:String){
    fun brighthenUp(){
        println("TO be implemented")
    }
}

//print("Kotlin Week 4/7 – OOP: Solution 4a Inheritance for Open Classes")

// uncomment and make it work (probably you need to change the base class?)
//
// TODO: remove ERROR Shape is final, ...
// class Emoji(override var color:String, val what:String):Shape (color) {
    // TODO: remove Error: brighthenUp is final ...
    //override fun brighthenUp(){
    //    this.color = "Light${this.color}"
    //}
//}

//val smile = Emoji("Blue","Smile")
//smile.brighthenUp()
class Piece (val name:String)
class TheGame(val name:String){
    val pieces = mutableListOf<Piece>()
    fun addPiece(newPiece:Piece) {
        pieces.add(newPiece)
    }
}



fun main() {
    println("Kotlin Week 4/7 – OOP: Solution 4b Operator Overloading")


	val chess = TheGame("Chess")
	chess.addPiece( Piece("King") )
	//TODO make this work:
	// chess += Piece("Queen")
	println("Players for ${chess.name}: ${chess.pieces}")
}

main()
Kotlin Week 5/7: 

## Task **Various Kotlin Language Features**:


### A Possible Solution

```kotlin
class TheStudent {
    companion object {
        private var no = 2019000
        fun getAnotherStudentID(): Int = no++
    }
}

fun main() {
    println("Kotlin Week 5/7 – Others: Solution 5a String Interpolation")
    
    // TASK Output 'We got 005.570€.'
	 val aValue:Float = 5.57f
	 println("We got ${String.format("%07.3f",aValue)}€.")


    println("Kotlin Week 5/7 – Others: Solution 5b Conditionals as Expression")
    // Task: define function max in one line
    fun veryLongDefinitionOfMax(a: Double, b: Double): Double {
        return if (a > b) a else b
    }
    println("We calc the max of pi and e: ${veryLongDefinitionOfMax(Math.PI, Math.E)}")


    println("Kotlin Week 5/7 – Others: Solution 5c Smart Casts (keyworkds is, as, if, when)")
    open class A(open var about: String = "cl a, the base")
    class B(override var about: String = "cl b") : A(about)
    class C(override var about: String = "cl c") : A(about)

    // Task: detect type of a variable and output a message
    fun detectTypeOfAVariableCastAndUseIt(x: Any) {
        when (x) {
            is C -> println("The type of x is C: ${x.about}")
            is B -> println("The type of x is B: ${x.about}")
            is A -> println("The type of x is A: ${x.about}")
            is Int -> println("The type of x is Int")
            is String -> println("The type of x is String")
            else -> throw IllegalArgumentException()
        }
    }

    detectTypeOfAVariableCastAndUseIt(A())  //
    detectTypeOfAVariableCastAndUseIt(B())  //
    detectTypeOfAVariableCastAndUseIt(C())  // The type of x is C
    detectTypeOfAVariableCastAndUseIt(4) //


    println("Kotlin Week 5/7 – Others: Solution 5d Companion Objects (keywords: companion object) ")
    // TASK: modify code to tie the functionality to class (NOT to instance) (compare Java "static" keyword):
    var no = TheStudent.getAnotherStudentID()
    println(no)
    no = TheStudent.getAnotherStudentID()
    println(no)


    println("Kotlin Week 5/7 – Others: Solution 5e Range Expression (keywords: rangeTo, downTo, reversed,...)")
    // Taks print sequences // 2018, 2019, 2020,...
    // Taks print sequences // 2030, 2026, 2022,...
    // Taks print sequences // _Z_ _Y_ _X_ ,...
    for (i in 2018.rangeTo(2022)) print("$i ")
    println()
    for (i in (2018..2030 step 4).reversed()) print("$i ")
    println()
    for (x in 'Z' downTo 'A') print(" _${x}_ ")
    println()


    println("Kotlin Week 5/7 – Others: Solution 5f Type Inference")
    val message = ""
    val number = Float.POSITIVE_INFINITY

    println("TODO: shorter with type inference: ${message} ${number}")
} 

```

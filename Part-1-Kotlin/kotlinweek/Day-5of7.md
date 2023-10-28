[Home](./README-KotlinWeek.md)

# Kotlin Week 5/7:

<https://pl.kotl.in/K8hkX9mqB>. Open in private browser window for a fresh session. Copy the code below in a dedicated online playground or into an Android Studio Scratch file. Thx.

## Task **Various Kotlin Language Features**:

### Rewrite following code using idiomatic Kotlin:

*Optimise .... until reworked code is more powerful (alltogether, about 40 lines).*

Improve code by implementing more Kotlin language features, such as string interpolation, smart casts, range expressions, companion objects and others.

```kotlin
fun main() {
    println("Kotlin Week 5/7 – Others: Please Improve")

    println("Kotlin Week 5/7 – Others: Solution 5a String Interpolation")

    // TASK Output 'We got 005.570€.'
	 val aValue:Float = 5.57f

	 print("Missing string interpolation for a float as formatted string: ")
	 var strValue = aValue.toString()
	 val strLen = strValue.length
	 val posPoint = strValue.lastIndexOf(".")
	 val prefix= "0".repeat(3-posPoint)
	 print("We got ")
	 print( prefix)
	 print(strValue)
	 print("0".repeat(7-(strLen+prefix.length)))
	 println("€." )
    

    println("Kotlin Week 5/7 – Others: Solution 5b Conditionals as Expression")

    // Task: define function max in one line
    fun veryLongDefinitionOfMax(a: Double, b: Double):Double {
        if (a > b){
            return a
        } else {
            return b
        }
    }
    println("We calc the max of pi and e: ${veryLongDefinitionOfMax(Math.PI,Math.E)}")

    
    
    
    println("Kotlin Week 5/7 – Others: Solution 5c Smart Casts (keyworkds is, as, if, when)")
    open class A(open var about:String="cl a, the base")
    class B(override var about:String="cl b"):A(about)
    class C(override var about:String="cl c"):A(about)
    // Task: detect type of a variable and output a message
    fun detectTypeOfAVariableCastAndUseIt(x: Any){
        if (x is String){
            println("The type of x is a String")
            val xStr = x.toString()
            println("Its safe now to use method of String, such as length: ${xStr.length}")
        }else if (x is C){
            println("The type of x is C")
        }
    }

    detectTypeOfAVariableCastAndUseIt( A() )  //
    detectTypeOfAVariableCastAndUseIt( B() )  //
    detectTypeOfAVariableCastAndUseIt( C() )  // The type of x is C
    detectTypeOfAVariableCastAndUseIt( 4 ) //

    
    
    println("Kotlin Week 5/7 – Others: Solution 5d Companion Objects (keywords: companion object) ")

    // TASK: modify code to tie the functionality to class (NOT to instance) (compare Java "static" keyword):
    var no = 2019000
    class TheStudent(var no:Int) {
        fun getAnotherStudentID():Int{
            this.no = this.no +1
            return this.no
        }
    }
    no = TheStudent(no).getAnotherStudentID()
    println(no)
    no = TheStudent(no).getAnotherStudentID()
    println(no)
    
    
    print("Kotlin Week 5/7 – Others: Solution 5e Range Expression (keywords: rangeTo, downTo, reversed,...)")
    // Taks print sequences // 2018, 2019, 2020,...
    // Taks print sequences // 2030, 2026, 2022,...
    // Taks print sequences // _Z_ _Y_ _X_ ,...
    var y:Int=2018
    while (y< 2023) { println(y); y += 1}
    // ...
    val letters = listOf('A','B','C','X', /* too lazy */  'Y','Z')
    for (l in letters.asReversed() ){
        print( " _${l}_ " )
    }
 
    println("Kotlin Week 5/7 – Others: Solution 5f Type Inference")
    val message:String = ""
    val number:Float = Float.POSITIVE_INFINITY

    println("TODO: shorter with type inference: ${message} ${number}")
 
}

```



[Day 6: Generics](./Day-6of7.md)
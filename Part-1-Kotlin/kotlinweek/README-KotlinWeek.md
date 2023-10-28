# A Week of Kotlin

Seven daily tasks to train Kotlin

* [Day 1](./Day-1of7.md): Shorter code with **data classes** and singletons (**object**)
* [Day 2](./Day-2of7.md): Functional (map, filter)
* [Day 3](./Day-3of7.md): Properties: **by lazy**, observe, delegate
* [Day 4](./Day-4of7.md): Operator overloading
* [Day 5](./Day-5of7.md): Various language features (smart casts, companion objects, range expressions)
* [Day 6](./Day-6of7.md): Generics
* [Day 7](./Day-7of7.md): Concurrency with coroutines[^include]


[Start with Day 1](./Day-1of7.md)

- - - 


## Kotlin Resources


* Kotlin Code Snippets: <https://git-iit.fh-joanneum.at/omd/code-snippets/-/tree/master/kotlin>

* The **Cheat Sheet** by KT.Academy: <https://kt.academy/static/Kotlin_Cheat_Sheet.pdf>

* Kotlin Standard Library Cookbook June 2018 (eBook, FH Library) <http://permalink.obvsg.at/fhj/AC15521894>

* **Further reading, further coding:** Find many, many great tutorials on **Google Developer Codelabs** <https://codelabs.developers.google.com>. 
	* For example you might start with the Android **Kotlin Fundamentals** Course <https://codelabs.developers.google.com/android-kotlin-fundamentals/> and move on the the 
	* Expert session **Advanced Android** in Kotlin <https://codelabs.developers.google.com/advanced-android-kotlin-training/> later.
	* **Coroutines** Motivation Video (Talk+Demo) <https://www.youtube.com/watch?v=FWxeDqM_WIU>, Coroutines background <https://developer.android.com/kotlin/coroutines> and usage in Android Apps <https://developer.android.com/codelabs/kotlin-coroutines> 

* Check out the free **Udacity lectures** Developing Android Apps with Kotlin <https://www.udacity.com/course/developing-android-apps-with-kotlin--ud9012> and **Advanced Android with Kotlin** <https://www.udacity.com/course/advanced-android-with-kotlin--ud940> (both by Google).



[^include]: To run coroutines locally (compile to java bytecode) we need to include the runtime e.g. *kotlinx-coroutines-core-1.6.x.jar*. See [`task7of7.sh`](./task7of7.sh).
val state = MutableStateFlow("empty") // flow to update UI (in our case just print to logcat)

fun main() {
    runSync()
    runAsync()

    // subscribe to flow updates and print state.value to logcat.
}

fun runSync() {
    println("runSync method.")
    for (i in 0..1000) {
        GlobalScope.launch(Dispatchers.Main) {
            coroutineScope {
                launch {
                    doWork("${i}")
                }
            }
        }
    }
    //  launch 1000 coroutines. Invoke doWork(index/number of coroutine) one after another. Example 1, 2, 3, 4, 5, etc.
}

fun runAsync() {
    println("runAsync method.")
    for(i in 0..1000){
        GlobalScope.launch(Dispatchers.Main) {
            val deferredJob1 = async{
                doWork("$i")
            }

            deferredJob1.await()

        }
    }
    //  launch 1000 coroutines. Invoke doWork(index/number of coroutine) in async way. Example 1, 2, 5, 3, 4, 8, etc.
}

private suspend fun doWork(name: String) {
    delay(500)
    state.update { "$name completed." }
}
fun main() {

    val listInts = listOf(1, 3, 5, 4, 2, 5)
    val listStrs = listOf('a', 'B', 'A', 'b', 'A', 'a',2,5,3,2)
    val resultInt  = listInts.uniqueOrderedList(listInts)
    val resultStr  = listStrs.uniqueOrderedList(listStrs)
    println(resultInt)
    print(resultStr)
}
fun <T> List<T>.uniqueOrderedList(list:List<T>):List<T>{
    return list.distinct().sortedBy { it.toString() }
}
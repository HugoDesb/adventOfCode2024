

fun <T> List<T>.middle(): T = this[size/2]


class Day05: Day("05") {

    private val data: List<String>

    private val rules: List<Pair<Int, Int>>
    private val updates: List<List<Int>>

    init {
        data = getLines()
        val splitIndex = data.indexOf("")
        val pagesOrderingLines = data.take(splitIndex)
        val updatePagesLines = data.drop(splitIndex+1)
        rules = pagesOrderingLines.map {
            val (first, second) = it.split('|', limit = 2)
                .map(String::toInt)
            first to second
        }
        updates = updatePagesLines.map { it.split(",").map(String::toInt)  }
    }
    override fun readData(){
    }

    override fun part1() {
        val sum = updates.filter(::isValid)
            .sumOf { it.middle() }
        println("Sum: $sum")
    }


    override fun part2() {
        val sum = updates.filterNot(::isValid)
            .map { it.toMutableList() }
            .sumOf { update ->

                loop@ while(true) {
                    for(index in 0..<update.lastIndex){
                        val left = update[index]
                        val right = update[index+1]

                        if(missingInRules(left, right)){
                            update[index] = right
                            update[index+1] = left
                            continue@loop
                        }
                    }
                    break
                }

                update.middle()
            }
        print("Sum: $sum")
    }


    fun isValid(ints: List<Int>): Boolean {
        for(firstIndex in ints.indices){
            for(secondIndex in firstIndex+1..ints.lastIndex){
                if(missingInRules(ints[firstIndex], ints[secondIndex])){
                    return false
                }
            }
        }
        return true
    }

    private fun missingInRules(left: Int, right: Int) = left to right !in rules
}


fun main() = Day05().run()


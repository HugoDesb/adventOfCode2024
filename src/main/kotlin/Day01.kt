import java.io.File
import kotlin.math.max
import kotlin.math.min

class Day01: Day("01") {

    lateinit var data: Pair<MutableList<Int>, MutableList<Int>>
    override fun readData(){
        val left: MutableList<Int> = mutableListOf()
        val right: MutableList<Int> = mutableListOf()
        getLines()
            .forEach { s ->
                val l = s.split("   ")
                left.add(l[0].toInt())
                right.add(l[1].toInt())
            }
        data = Pair(left, right);
    }


    override fun part1() {

        data.first.sort()
        data.second.sort()

        var sum = 0
        data.first.forEachIndexed { i, value ->
            val min = min(value, data.second[i])
            val max = max(value, data.second[i])
            sum += max - min
        }
        println("Part01: $sum")


    }


    override fun part2() {

        val mapRightOccurences = data.second.groupBy { it }
            .mapValues { it.value.size }

        val sum = data.first.sumOf {
            it * mapRightOccurences.getOrDefault(it, 0)
        }
        println("Part02: $sum")

    }

}


fun main() = Day01().run()


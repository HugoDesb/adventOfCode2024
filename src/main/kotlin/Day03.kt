import java.io.File
import kotlin.math.max
import kotlin.math.min

class Day03 : Day("03") {

    lateinit var data: List<String>
    override fun readData() {
        data = getLines()
    }


    override fun part1() {
        val mulRegex = Regex("mul\\((\\d+),(\\d+)\\)")
        var sum = 0

        data.forEach { line ->
            val results = mulRegex.findAll(line)
            results.forEach {
                sum += it.groupValues[1].toInt() * it.groupValues[2].toInt()
            }
        }
        println("Part01: $sum")
    }


    override fun part2() {
        val mulRegex = Regex("(do\\(\\))|(don\'t\\(\\))|(mul\\((\\d+),(\\d+)\\))")

        val operations: MutableList<Operation> = mutableListOf()
        data.forEach { line ->
            val results = mulRegex.findAll(line)
            results.forEach {
                if (it.value.contains("mul")) {
                    operations.add(Mul(it.groupValues[4], it.groupValues[5]))
                } else if (it.value == "do()") {
                    operations.add(Do())
                } else {
                    operations.add(Dont())
                }
            }
        }

        var doOp = true
        var sum = 0
        operations.forEach {
            if(it is Mul && doOp){
                sum += it.p1!!.toInt() * it.p2!!.toInt()
            }else if (it is Do){
                doOp = true
            }else{
                doOp = false
            }
        }


        println("Part02: $sum")
    }

}

open class Operation(val name: String, val p1: String? = null, val p2: String? = null)

class Mul(p1: String, p2: String) : Operation("mul", p1, p2){
    override fun toString(): String {
        return "$p1 x $p2"
    }
}

class Do(): Operation("do"){
    override fun toString(): String {
        return "Do()"
    }
}
class Dont(): Operation("don't"){
    override fun toString(): String {
        return "Dont()"
    }
}


fun main() = Day03().run()


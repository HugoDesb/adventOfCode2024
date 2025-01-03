import java.io.File
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.time.Duration.Companion.milliseconds

fun <T> List<T>.tail() = drop(1)
fun <T> List<T>.head() = first()

fun Int.pow(n: Int) = this.toDouble().pow(n.toDouble()).toInt()

fun ULong.concat(other: ULong) = other + 10.pow(other.toString().length).toULong()*this


class Day07: Day("07") {

    val data: List<Pair<ULong, List<ULong>>>


    init{
        data = getLines().map {
            val splited = it.split(":")
            val testVal = splited[0].toULong()
            val nums = splited[1].trim().split(' ').map { it.toULong() }
            testVal to nums
        }

    }



    fun isValidCalibration2op(testValue: ULong, nums: List<ULong>, acc: ULong): Boolean{
        if (nums.isEmpty()) return acc == testValue

        return isValidCalibration2op(testValue, nums.tail(), acc*nums.head())
                || isValidCalibration2op(testValue, nums.tail(), acc+nums.head())
    }
    fun isValidCalibration3op(testValue: ULong, nums: List<ULong>, acc: ULong): Boolean{
        if (nums.isEmpty()) return acc == testValue

        return isValidCalibration3op(testValue, nums.tail(), acc*nums.head())
                || isValidCalibration3op(testValue, nums.tail(), acc+nums.head())
                || isValidCalibration3op(testValue, nums.tail(), acc.concat(nums.head()))
    }

    override fun part1() {
        val sum = data.map { it.first to isValidCalibration2op(it.first, it.second.tail(), it.second.head()) }
            .filter { it.second }
            .sumOf { it.first }

        // 5030892083587 is too low
        println("Sum part 1: $sum")
    }


    override fun part2() {

        val sum = data.map { it.first to isValidCalibration3op(it.first, it.second.tail(), it.second.head()) }
            .filter { it.second }
            .sumOf { it.first }

        // 5030892083587 is too low
        println("Sum part 1: $sum")
    }

}


fun main() = Day07().run()


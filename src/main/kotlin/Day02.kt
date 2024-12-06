class Day02 : Day("02") {

    private lateinit var data: List<Report>
    override fun readData() {
        data = getLines().map { line ->
            val levels = line.split(" ")
            Report(levels.map { it.toInt() })
        }
    }

    override fun part1() {
        val safecount = data.filter(Report::isSafe01).size
        println("Part01: $safecount")
    }


    override fun part2() {
        val (safe, unsafe) = data.partition(Report::isSafe01)
        var safecount = safe.size

        val redeemable = unsafe.partition(Report::canBeTolerated)


        safecount += redeemable.first.size
        println("Part02: $safecount")
    }

}

enum class Direction02 {
    ASC, DESC
}

data class Report(val levels: List<Int>) {

    fun isSafe01(): Boolean {
        val dir = if (levels[0] < levels[1]) {
            Direction02.ASC
        } else if (levels[0] > levels[1]) {
            Direction02.DESC
        } else {
            null
        }

        if (dir == null) return false

        return isGradual01(dir, levels.first(), levels.drop(1))
    }

    fun isGradual01(dir: Direction02, head: Int, tail: List<Int>): Boolean {

        if(tail.isEmpty()) return true;

        val isStepOk = when (dir) {
            Direction02.DESC -> head - tail.first() in 1..3
            Direction02.ASC -> tail.first() - head in 1..3
        }

        return isStepOk && isGradual01(dir, tail.first(), tail.drop(1))
    }

    fun canBeTolerated(): Boolean {
        val canBeDampened = List(levels.size) { i ->
            val mut = levels.toMutableList()
            mut.removeAt(i)
            val report = Report(mut)
            report.isSafe01()
        }.any { it }
        return canBeDampened
    }

}


fun main() = Day02().run()


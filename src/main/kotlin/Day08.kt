class Day08 : Day("08") {

    val data: Grid
    val antennas: MutableMap<Char, MutableList<Vector>> = mutableMapOf()


    init {
        data = Grid(getLines().map { it.toMutableList() })
        data.list.forEachIndexed { x, line ->
            line.forEachIndexed { y, c ->
                if (c.isDigit() || c.isLetter()) {
                    val list = antennas.getOrElse(c) { mutableListOf() }
                    list.add(Vector(x, y))
                    antennas[c] = list
                }
            }
        }
    }


    fun getCombinations(elements: MutableList<Vector>): MutableList<Pair<Vector, Vector>> {
        val combinations = mutableListOf<Pair<Vector, Vector>>()
        elements.forEach { first ->
            elements.forEach { second ->
                if (first != second) combinations.add(first to second)
            }
        }
        return combinations
    }

    fun getAntinodeLocation(antennas: Pair<Vector, Vector>) =
        antennas.second + antennas.second - antennas.first

    override fun part1() {
        val antinodes = mutableSetOf<Vector>()
        var sum = 0
        antennas.forEach { freq ->
            getCombinations(freq.value).forEach {
                    if (getAntinodeLocation(it) in data)
                        antinodes.add(getAntinodeLocation(it))
                }
        }
        println("Sum part 1: ${antinodes.size}")
    }


    override fun part2() {

    }

}


fun main() = Day08().run()


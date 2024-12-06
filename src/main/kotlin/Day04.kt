import java.awt.Point
import java.awt.geom.Point2D
import java.io.File
import kotlin.math.max
import kotlin.math.min

class Vector(val x: Int, val y: Int) {
    operator fun plus(vector: Vector) = Vector(this.x + vector.x, this.y + vector.y)
}


class Grid(val data: List<List<Char>>) {
    fun isInBounds(vector: Vector) = vector.x in data.indices && vector.y in data[0].indices
    fun at(vector: Vector) = data[vector.x][vector.y]

    val directions = mapOf(
        "N" to Vector(0, -1),
        "NE" to Vector(1, -1),
        "E" to Vector(1, 0),
        "SE" to Vector(1, 1),
        "S" to Vector(0, 1),
        "SO" to Vector(-1, 1),
        "O" to Vector(-1, 0),
        "NO" to Vector(-1, -1)
    )

    fun <T> mapVectors(f : (Vector)->T): List<T>{
        return data.flatMapIndexed { i, line ->
            List(line.size) { j ->
                f(Vector(i, j))
            }
        }
    }
}

private enum class Direction04(val vector: Vector){
    N(Vector(0, -1)),
    NE(Vector(1, -1)),
    E(Vector(1, 0)),
    SE(Vector(1, 1)),
    S(Vector(0, 1)),
    SO(Vector(-1, 1)),
    O(Vector(-1, 0)),
    NO(Vector(-1, -1))
}

class Day04 : Day("04") {

    lateinit var data: Grid
    override fun readData() {
        data = Grid(getLines().map { it.toList() })
    }

    private fun isXMAS(startXPoint: Vector, direction: Vector): Boolean {
        val secondPt = startXPoint + direction
        if (!data.isInBounds(secondPt) || data.at(secondPt) != 'M') {
            return false
        }

        val thirdPt = secondPt + direction
        if (!data.isInBounds(thirdPt) || data.at(thirdPt) != 'A') {
            return false
        }


        val fourthPt = thirdPt + direction
        if (!data.isInBounds(fourthPt) || data.at(fourthPt) != 'S') {
            return false
        }

        return true
    }

    private fun getXMASCount(startXPoint: Vector): Int {

        if (data.at(startXPoint) != 'X') {
            return 0
        }

        val directions = listOf(
            Vector(1, 1),
            Vector(1, 0),
            Vector(1, -1),
            Vector(-1, 1),
            Vector(-1, 0),
            Vector(-1, -1),
            Vector(0, 1),
            Vector(0, 0),
            Vector(0, -1)
        )

        return directions.count { isXMAS(startXPoint, it) }
    }


    private fun getX_MASCount(center: Vector): Int {
        if (data.at(center) != 'A') {
            return 0
        }


        val isImpossible = Direction04.entries.any { !data.isInBounds(center + it.vector) }
        if (isImpossible){
            return 0
        }


        val has_diagonal = data.at(center) == 'A' &&
                ((data.at(center + Direction04.SE.vector) == 'M' && data.at(center +Direction04.NO.vector) == 'S') ||
                        (data.at(center + Direction04.SE.vector) == 'S' && data.at(center +Direction04.NO.vector) == 'M')) &&
                ((data.at(center +Direction04.SO.vector) == 'M' && data.at(center +Direction04.NE.vector) == 'S') ||
                        (data.at(center +Direction04.SO.vector) == 'S' && data.at(center +Direction04.NE.vector) == 'M'))


        val has_horizontal = data.at(center) == 'A' &&
                ((data.at(center +Direction04.N.vector) == 'M' && data.at(center +Direction04.S.vector) == 'S') ||
                        (data.at(center +Direction04.N.vector) == 'S' && data.at(center +Direction04.S.vector) == 'M')) &&
                ((data.at(center +Direction04.E.vector) == 'M' && data.at(center +Direction04.O.vector) == 'S') ||
                        (data.at(center +Direction04.E.vector) == 'S' && data.at(center +Direction04.O.vector) == 'M'))


        return if (has_diagonal && has_horizontal) 2 else if (has_diagonal || has_horizontal) 1 else 0
    }


    override fun part1() {
        val sum = data
            .mapVectors { getXMASCount(it) }
            .sum()
        println("Part 01: $sum")
    }


    override fun part2() {
        val sum = data
            .mapVectors { getX_MASCount(it) }
            .sum()
        println("Part 02: $sum")
    }


}


fun main() = Day04().run()


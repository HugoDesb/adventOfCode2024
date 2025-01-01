import java.awt.Point
import java.awt.geom.Point2D
import java.io.File
import kotlin.math.max
import kotlin.math.min


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

    val data: Grid

    init {
        data = Grid(getLines().map { it.toMutableList() })
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
        if (data.at(startXPoint) != 'X') return 0
        return Direction04.entries.count { isXMAS(startXPoint, it.vector) }
    }


    private fun getX_MASCount(center: Vector): Int {
        if (data.at(center) != 'A') {
            return 0
        }

        val isImpossible = Direction04.entries.any { !data.isInBounds(center + it.vector) }
        if (isImpossible){
            return 0
        }

        val has_downwards_mas = data.at(center+Direction04.NO.vector) == 'M' && data.at(center+Direction04.SE.vector) == 'S'
        val has_downwards_sam = data.at(center+Direction04.NO.vector) == 'S' && data.at(center+Direction04.SE.vector) == 'M'
        val has_upward_mas = data.at(center+Direction04.SO.vector) == 'M' && data.at(center+Direction04.NE.vector) == 'S'
        val has_upward_sam = data.at(center+Direction04.SO.vector) == 'S' && data.at(center+Direction04.NE.vector) == 'M'

        val has_diagonal = data.at(center) == 'A' &&
                (has_downwards_mas || has_downwards_sam) &&
                (has_upward_mas || has_upward_sam)
        return if (has_diagonal) 1 else 0
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


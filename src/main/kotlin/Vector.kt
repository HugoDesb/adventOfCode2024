import kotlin.math.cos
import kotlin.math.round
import kotlin.math.sign
import kotlin.math.sin

data class Vector(val x: Int, val y: Int) {
    operator fun plus(vector: Vector) = Vector(this.x + vector.x, this.y + vector.y)

    operator fun minus(vector: Vector) = Vector(x - vector.x, y - vector.y)

    operator fun rangeTo(p2: Vector): List<Vector> {
        val dir = (p2-this).normalize()
        val vectors = mutableListOf(this)

        var curr = this + dir
        while(curr != p2){
            vectors.add(curr)
            curr += dir
        }
        vectors.add(p2)
        return vectors.toList()
    }


    fun rotateRight() = rotate(-90.0)

    private fun rotate(theta: Double): Vector {
        val xp = round(x * cos(theta) - y * sin(theta)).toInt()
        val yp = round(x * sin(theta) + y * cos(theta)).toInt()

        return Vector(xp, yp)
    }

    fun normalize(): Vector = Vector(x.sign, y.sign)

    override fun toString(): String = "($x, $y)"
    fun getChar(): Char {
        val v = this.normalize()
        if(v.x == -1 && v.y == 0) return '^'
        if(v.x == 1 && v.y == 0) return 'v'
        if(v.x == 0 && v.y == -1) return '<'
        if(v.x == 0 && v.y == 1) return '>'
        return '*'
    }

}
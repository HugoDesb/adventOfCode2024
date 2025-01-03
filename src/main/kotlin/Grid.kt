data class Grid(
    val list: List<MutableList<Char>>
) {

    operator fun contains(vector: Vector) = isInBounds(vector)

    operator fun get(vector: Vector) = at(vector)

    operator fun set(vector: Vector, c: Char){
        list[vector.x][vector.y] = c
    }

    fun isInBounds(vector: Vector) = vector.x in list.indices && vector.y in list[0].indices
    fun at(vector: Vector) = list[vector.x][vector.y]

    fun positionOf(c: Char): Vector{
        val (x, y) = list.map { it.indexOf(c) }
            .mapIndexed{index, el -> index to el }
            .first { it.second != -1 }
        return Vector(x, y)
    }

    fun copyWithBlock(at: Vector): Grid{
        val newGrid = this.copy()
        newGrid[at] = '#'
        return newGrid
    }

    fun copy(): Grid{
        val newList = list.map { it.map { it1 -> it1 }.toMutableList() }
        return Grid(newList)
    }

    fun <T> mapVectors(f : (Vector)->T): List<T> {
        return list.flatMapIndexed { i, line ->
            List(line.size) { j ->
                f(Vector(i, j))
            }
        }
    }

    override fun toString(): String {
//        val a = mutableListOf<String>()
//        a.apply {  }
//        val dataWithIndexes = mutableListOf(mutableListOf(' ','0', '1', '2', '3', '4', '5', '6', '7', '8', '9'))
//            .apply {
//                addAll(list.mapIndexed {index, chars ->
//                    mutableListOf(index.digitToChar()).apply { addAll(chars) }
//                })
//            }
//
//        return dataWithIndexes.joinToString(separator = "\n") {
//            it.joinToString(separator = " ")
//        }
        return "grid tmp"
    }


}



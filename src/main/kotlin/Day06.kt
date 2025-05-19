class Day06: Day("06") {

    val grid: Grid

    init {
        grid = Grid(getLines().map { it.toMutableList() })
    }

    override fun part1() {
        val sum = walkTheBeat(grid.copy())
        println("Sum part 1: $sum")
    }


    override fun part2() {
        val sum = countPossibleObstructions(grid.copy())
        println("Sum part 2: $sum")
    }


    private fun walkTheBeat(grid: Grid): Int{
        var position = grid.positionOf('^')
        var direction = Vector(-1, 0)
        val walkedData = grid.list.map { it.toMutableList() }
        walkedData[position.x][position.y] = 'X'

        while(true){
            val nextPosition = position+direction
            if(nextPosition !in grid){
                walkedData[position.x][position.y] = 'X'
                return walkedData.sumOf { row -> row.filter { it == 'X'}.size }
            }
            if(grid.at(nextPosition) != '#'){
                //mark old position
                walkedData[position.x][position.y] = 'X'
                //step forward
                position = nextPosition
            }else{
                direction = direction.rotateRight()
            }
        }
    }

    private fun countPossibleObstructions(grid: Grid): Int{
        val turnPoints = mutableListOf<Vector>()
        val startPosition = grid.positionOf('^')
        var position = startPosition
        var direction = Vector(-1, 0)

        var possibleObstructions = mutableListOf<Vector>()

        while(true){
            val nextPosition = position+direction
            if(nextPosition !in grid){
                println(possibleObstructions)
                return possibleObstructions.size
            }

            if(grid.at(nextPosition) == '#'){
                turnPoints.add(position)
                direction = direction.rotateRight()
                grid[position] = direction.getChar()

            }else{
                val newTurnPoints = turnPoints.toMutableList()
                //newTurnPoints.add(position)
                val isLoopPossibleHere = isWalkLoop(position, direction.rotateRight(), grid.copyWithBlock(nextPosition))

                if(isLoopPossibleHere) {

                    possibleObstructions.add(nextPosition)
                }

                //step forward
                grid[position] = '.'
                grid[nextPosition] = direction.getChar()
                position = nextPosition
            }
        }
    }

    /**
     * Walk until either the walk is finished outside the grid (returns false), or if the walk repeats itself.
     * In that case the method returns true
     *
     * The walk starts at the [start] position, and starts walking in the direction [direction]. The method determines
     * it is stuck in a loop if next position is the start position
     *
     */
    private fun isWalkLoop(start: Vector, startDir: Vector, grid: Grid): Boolean{

        var position = start
        var direction = startDir
        var nextPosition: Vector

        while (true){
            nextPosition = position+direction

            if(nextPosition !in grid) return false

            if(nextPosition == start) return true

            if(grid.at(nextPosition) == '#'){
                //turn
                direction = direction.rotateRight()
                grid[position] = direction.getChar()
            }else{
                //step forward
                grid[position] = '.'
                grid[nextPosition] = direction.getChar()
                position = nextPosition
            }
        }
    }
}


fun main() = Day06().run()


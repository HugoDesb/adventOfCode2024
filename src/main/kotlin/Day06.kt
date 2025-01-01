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

        var possibleObstructions = 0

        while(true){
            val nextPosition = position+direction
            println("------------------------")
            println(grid)
            if(nextPosition !in grid){
                return possibleObstructions
            }

            if(grid.at(nextPosition) == '#'){
                turnPoints.add(position)
                direction = direction.rotateRight()
                grid[position] = direction.getChar()

            }else{
                val newTurnPoints = turnPoints.toMutableList()
                newTurnPoints.add(position)
                val isLoopPossibleHere = isWalkLoop(position, direction.rotateRight(), newTurnPoints, grid.copyWithBlock(nextPosition))

                if(isLoopPossibleHere) possibleObstructions++

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
     * it is stuck in a loop if a turn point is shared with the [turnPoints] list
     *
     */
    private fun isWalkLoop(start: Vector, startDir: Vector, turnPoints: MutableList<Vector>, grid: Grid): Boolean{

        var position = start
        var direction = startDir


        while (true){
            val nextPosition = position+direction
            if(nextPosition !in grid){
                return false // if we get out of the grid, then there's no loop
            }

            if(grid.at(nextPosition) == '#'){
                if(turnPoints.contains(position)){
                    return true
                }else{
                    turnPoints.add(position)
                    direction = direction.rotateRight()
                    grid[position] = direction.getChar()
                }
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


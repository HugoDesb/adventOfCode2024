abstract class Day(val number: String) {


    abstract fun readData()
    abstract fun part1()
    abstract fun part2()

    init {
        readData()
    }

    fun run(){
        part1()
        part2()
    }


    fun getLines(): List<String> = this::class.java.getResource("input$number.txt")!!
            .readText(Charsets.UTF_8)
            .reader()
            .readLines()


}
fun main() {
    fun part1(input: List<String>): Int {
        return input.map { it.toInt() }.zipWithNext().count { it.second > it.first }
    }

    fun part2(input: List<String>): Int {
        val list = input.map { it.toInt() }
        val averaged = list.zip(list.drop(1)).zip(list.drop(2)).map { it.first.first + it.first.second + it.second }
        return averaged.zipWithNext().count { it.second > it.first }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    val input = readInput("Day01")

    println(part1(testInput))
    check(part1(testInput) == 7)
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 5)
    println(part2(input))
}

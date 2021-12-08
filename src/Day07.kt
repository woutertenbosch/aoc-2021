import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val crabs = input.first().split(",").map { it.toInt() }
        return (0..crabs.maxOf { it }).minOf { target ->
            crabs.sumOf { abs(it - target) }
        }
    }

    fun part2(input: List<String>): Int {
        val crabs = input.first().split(",").map { it.toInt() }
        return (0..crabs.maxOf { it }).minOf { target ->
            crabs.map { abs(it - target) }.sumOf { (it * (it + 1)) / 2 }
        }
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    val input = readInput("Day07")

    println(part1(testInput))
    check(part1(testInput) == 37)
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 168)
    println(part2(input))
}

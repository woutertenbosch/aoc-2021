import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {


        val crabs = input.first().split(",").map { it.trim().toInt() }

        val max = crabs.maxOrNull()!!

        var best = Int.MAX_VALUE
        for (i in 0..max) {


            val score = crabs.map { abs(it - i) }.sum()
            if (score < best) {
                best = score
            }
        }

        return best
    }

    fun part2(input: List<String>): Int {

        val crabs = input.first().split(",").map { it.trim().toInt() }

        val max = crabs.maxOrNull()!!

        var best = Int.MAX_VALUE
        for (i in 0..max) {


            val score = crabs.map { abs(it - i) }.sumOf { (it * (it + 1)) / 2 }
            if (score < best) {
                best = score
            }
        }

        return best
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

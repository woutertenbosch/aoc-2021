fun main() {
    fun part1(input: List<String>): Int {
        var depth = 0
        var position = 0

        input.forEach {
            val (command, amount) = it.split(" ")

            when (command) {
                "down" -> depth += amount.toInt()
                "up" -> depth -= amount.toInt()
                "forward" -> position += amount.toInt()
            }
        }

        return depth * position
    }

    fun part2(input: List<String>): Int {
        var depth = 0
        var position = 0
        var aim = 0

        input.forEach {
            val (command, amount) = it.split(" ")

            when (command) {
                "down" -> aim += amount.toInt()
                "up" -> aim -= amount.toInt()
                "forward" -> {
                    position += amount.toInt()
                    depth += aim * amount.toInt()
                }
            }
        }

        return depth * position
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    val input = readInput("Day02")

    println(part1(testInput))
    check(part1(testInput) == 150)
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 900)
    println(part2(input))
}

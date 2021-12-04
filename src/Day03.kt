fun main() {
    fun part1(input: List<String>): Int {
        var mostOccuring = ""
        var leastOccuring = ""

        for (b in 0 until input.first().length) {
            var ones = 0
            var zeros = 0
            input.forEach {
                if (it.get(b) == '0') {
                    zeros++
                } else {
                    ones++
                }
            }
            if (ones > zeros) {
                mostOccuring += "1"
                leastOccuring += "0"
            } else {
                mostOccuring += "0"
                leastOccuring += "1"
            }
        }
        return mostOccuring.toInt(2) * leastOccuring.toInt(2)
    }

    fun part2(input: List<String>): Int {
        var mostOccuring = input.toMutableList()
        var leastOccuring = input.toMutableList()

        for (b in 0 until input.first().length) {
            var ones = 0
            var zeros = 0
            mostOccuring.forEach {
                if (it.get(b) == '0') {
                    zeros++
                } else {
                    ones++
                }
            }
            if (mostOccuring.size > 1) {
                if (ones >= zeros) {
                    mostOccuring = mostOccuring.filter { it[b] == '1' }.toMutableList()
                } else {
                    mostOccuring = mostOccuring.filter { it[b] == '0' }.toMutableList()
                }
            }

            ones = 0
            zeros = 0
            leastOccuring.forEach {
                if (it.get(b) == '0') {
                    zeros++
                } else {
                    ones++
                }
            }
            if (leastOccuring.size > 1) {
                if (ones >= zeros) {
                    leastOccuring = leastOccuring.filter { it[b] == '0' }.toMutableList()
                } else {
                    leastOccuring = leastOccuring.filter { it[b] == '1' }.toMutableList()
                }
            }
        }
        return leastOccuring.first().toInt(2) * mostOccuring.first().toInt(2)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    val input = readInput("Day03")

    println(part1(testInput))
    check(part1(testInput) == 198)
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 230)
    println(part2(input))
}

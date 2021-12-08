fun main() {
    fun part1(input: List<String>): Int {
        val outputDigits = input.map { it.split(" | ") }.map { it[1] }.map { it.split(" ").map { it.trim() } }

        return outputDigits.sumOf { it.count { listOf(2, 3, 4, 7).contains(it.length) } }
    }


    fun part2(input: List<String>): Int {
        val digits = input.map {
            val s = it.split(" | ")
            s[0].split(" ").map { it.trim() } to s[1].split(" ").map { it.trim() }
        }

        val segments = listOf("a", "b", "c", "d", "e", "f", "g")
        val p = permutations(segments).toList().map { it.zip(segments).toMap() }.toList()

        val numbers = mapOf(
            "abcefg" to 0,
            "cf" to 1,
            "acdeg" to 2,
            "acdfg" to 3,
            "bcdf" to 4,
            "abdfg" to 5,
            "abdefg" to 6,
            "acf" to 7,
            "abcdefg" to 8,
            "abcdfg" to 9,
        )

        var sum = 0

        for (row in digits) {

            val (inputs, outputs) = row

            for (permut in p) {
                if (inputs.all { inputDigit ->
                    val convertedDigit = inputDigit.map { permut[it.toString()]!! }.sorted().joinToString("")
                    numbers.contains(convertedDigit)
                })  {
                    sum += outputs.map { outputDigit ->
                        val convertedDigit = outputDigit.map { permut[it.toString()]!! }.sorted().joinToString("")
                        numbers[convertedDigit]
                    }.joinToString("").toInt()
                }
            }


        }


        return sum
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    val input = readInput("Day08")

    println(part1(testInput))
    check(part1(testInput) == 26)
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 61229)
    println(part2(input))
}

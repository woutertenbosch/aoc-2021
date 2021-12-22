fun main() {
    fun increaseImageSize(input: List<List<Boolean>>, count: Int = 1): List<MutableList<Boolean>> {
        return sequence {
            yield((0 until input.first().size + count * 2).map { false }.toMutableList())
            yieldAll(
                input.map { (1..count).map { false }.plus(it).plus((1..count).map { false }).toMutableList() }
            )
            yield((0 until input.first().size + count * 2).map { false }.toMutableList())
        }.toList()
    }

    fun List<List<Boolean>>.print() {
        println(joinToString("\n") {
            it.map { if (it) "#" else "." }.joinToString("")
        })
    }

    fun List<List<Boolean>>.get(x: Int, y: Int): Boolean {
        return getOrNull(x)?.getOrNull(y) ?: get(0, 0)
    }

    fun part1(input: List<String>): Int {
        val algorithm = input.first().map { it == '#' }
        check(algorithm.size == 512)

        var image = input.drop(2).map { line -> line.map { it == '#' } }
        for (i in 1..20)
            image = increaseImageSize(image)

        for (s in 1..2) {
            var outputImage = increaseImageSize(image, 0)
            for (r in 0 until image.size) {
                for (c in 0 until image[r].size) {

                    var bitString = listOf(
                        image.get(r - 1, c - 1),
                        image.get(r - 1, c),
                        image.get(r - 1, c + 1),
                        image.get(r, c - 1),
                        image.get(r, c),
                        image.get(r, c + 1),
                        image.get(r + 1, c - 1),
                        image.get(r + 1, c),
                        image.get(r + 1, c + 1),
                    ).map { if (it) "1" else "0" }.joinToString("")

                    var index = bitString.toInt(2)
                    var outputPixel = algorithm[index]
                    outputImage[r][c] = outputPixel
                }
            }
            image = outputImage
        }
   //     image.print()

        return image.sumOf { it.count { it } }
    }

    fun part2(input: List<String>): Int {
        val algorithm = input.first().map { it == '#' }
        check(algorithm.size == 512)

        var image = input.drop(2).map { line -> line.map { it == '#' } }
        for (i in 1..100)
            image = increaseImageSize(image)

        for (s in 1..50) {
            var outputImage = increaseImageSize(image, 0)
            for (r in 0 until image.size) {
                for (c in 0 until image[r].size) {

                    var bitString = listOf(
                        image.get(r - 1, c - 1),
                        image.get(r - 1, c),
                        image.get(r - 1, c + 1),
                        image.get(r, c - 1),
                        image.get(r, c),
                        image.get(r, c + 1),
                        image.get(r + 1, c - 1),
                        image.get(r + 1, c),
                        image.get(r + 1, c + 1),
                    ).map { if (it) "1" else "0" }.joinToString("")

                    var index = bitString.toInt(2)
                    var outputPixel = algorithm[index]
                    outputImage[r][c] = outputPixel
                }
            }
            image = outputImage
        }
        image.print()

        return image.sumOf { it.count { it } }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day20_test")
    val input = readInput("Day20")

    println(part1(testInput))
    check(part1(testInput) == 35)
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 3351)
    println(part2(input))
}

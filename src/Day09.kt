fun main() {
    fun part1(input: List<String>): Int {
        val inp = input.map({ it.map { it.toString().toInt() } })

        var result = 0

        fun getVal(x: Int, y: Int): Int {
            return inp.getOrNull(x)?.getOrNull(y) ?: 9999
        }

        for (x in 0 until inp.size) {
            for (y in 0 until inp[0].size) {
                if (getVal(x - 1, y) > getVal(x, y) && getVal(x + 1, y) > getVal(x, y) && getVal(x, y - 1) > getVal(x, y) && getVal(
                        x,
                        y + 1
                    ) > getVal(x, y)
                ) {
                    result += 1 + getVal(x, y)

                    //println("$x $y -> $c")
                }
            }

        }

        return result
    }


    fun part2(input: List<String>): Int {
        val inp = input.map({ it.map { it.toString().toInt() } })

        fun getVal(x: Int, y: Int): Int {
            return inp.getOrNull(x)?.getOrNull(y) ?: 9999
        }

        val lowPoints = mutableListOf<Pair<Int, Int>>()
        for (x in 0 until inp.size) {
            for (y in 0 until inp[0].size) {
                if (getVal(x - 1, y) > getVal(x, y) && getVal(x + 1, y) > getVal(x, y) && getVal(x, y - 1) > getVal(x, y) && getVal(
                        x,
                        y + 1
                    ) > getVal(x, y)
                ) {
                    lowPoints.add(x to y)
                }
            }

        }

var curBasin = mutableListOf<Pair<Int, Int>>()
        val basins = mutableListOf<List<Pair<Int, Int>>>()

        fun traverse(x: Int, y: Int, s: Int) {
            if (curBasin.contains(x to y) || getVal(x, y) > 20) {
                return
            }
            if (getVal(x, y) > s) {
                if (getVal(x, y) != 9)
                curBasin.add(x to y)
            } else {
                return
            }

            traverse(x - 1, y, getVal(x, y))
            traverse(x + 1, y, getVal(x, y))
            traverse(x, y - 1, getVal(x, y))
            traverse(x, y + 1, getVal(x, y))

        }

        fun findBasin(x: Int, y: Int) {
            traverse(x, y, - 1)
        }

        for ((x, y) in lowPoints) {
            curBasin = mutableListOf<Pair<Int, Int>>()
            findBasin(x, y)
            basins.add(curBasin)
        }

        println(basins)

        val t = basins.map { it.size }.sortedDescending().take(3)

        return t.fold(1) {a,b ->a*b}
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    val input = readInput("Day09")

    println(part1(testInput))
    check(part1(testInput) == 15)
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 1134)
    println(part2(input))
}

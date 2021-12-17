fun main() {
    fun part1(input: List<String>): Int {

        val dots = input.takeWhile { it != "" }.map { it.split(",") }.map { it[0].toInt() to it[1].toInt() }
        var maxX = dots.maxOf { it.first }
        var maxY = dots.maxOf { it.second }

        val grid = mutableListOf<MutableList<Boolean>>()
        grid.addAll((0..maxX).map {
            (0..maxY).map { false }.toMutableList()
        })

        for (dot in dots) {
            grid[dot.first][dot.second] = true
        }

        val folds = input.dropWhile { it != "" }.drop(1).map { it.contains("x") to it.filter { it.isDigit() }.toInt() }

        for (fold in folds.take(1)) {

            if (fold.first) {

                val splitX = fold.second

                for (x in 0 until splitX) {
                    for (y in 0..maxY) {
                        if (grid[maxX - x][y])
                            grid[x][y] = true
                    }
                }

                maxX = splitX

            } else {

                val splitY = fold.second

                for (x in 0..maxX) {
                    for (y in 0 until splitY) {
                        if (grid[x][maxY - y])
                            grid[x][y] = true
                    }
                }

                maxY = splitY
            }

        }
        //  println(grid)
        //  println(folds)

        var ctr = 0
        for (x in 0..maxX) {
            for (y in 0..maxY) {
                if (grid[x][y]) ctr++
            }
        }

        return ctr
    }

    fun part2(input: List<String>): Int {

        val dots = input.takeWhile { it != "" }.map { it.split(",") }.map { it[0].toInt() to it[1].toInt() }
        var maxX = dots.maxOf { it.first }
        var maxY = dots.maxOf { it.second }

        val grid = mutableListOf<MutableList<Boolean>>()
        grid.addAll((0..maxX).map {
            (0..maxY).map { false }.toMutableList()
        })

        for (dot in dots) {
            grid[dot.first][dot.second] = true
        }

        val folds = input.dropWhile { it != "" }.drop(1).map { it.contains("x") to it.filter { it.isDigit() }.toInt() }

        for (fold in folds) {

            if (fold.first) {

                val splitX = fold.second

                for (x in 0 until splitX) {
                    for (y in 0..maxY) {
                        if (grid[(splitX - x)+splitX][y])
                            grid[x][y] = true
                    }
                }

                maxX = splitX

            } else {

                val splitY = fold.second

                for (x in 0..maxX) {
                    for (y in 0 until splitY) {
                        if (grid[x][(splitY - y)+splitY])
                            grid[x][y] = true
                    }
                }

                maxY = splitY
            }

        }
        for (y in 0..maxY) {
            for (x in 0..maxX) {
                if (grid[x][y])
                    print("#") else
                    print(".")
            }
            println()
        }


        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day13_test")
    val input = readInput("Day13")

    println(part1(testInput))
    check(part1(testInput) == 17)
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 0)
    println(part2(input))
}

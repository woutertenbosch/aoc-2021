fun main() {
    fun part1(input: List<String>): Int {
        val grid = input.map { it.map { it.toString().toInt() }.toMutableList() }

        fun incSafeGrid(x: Int, y: Int) {
            if ((0 until grid.size).contains(x) && (0 until grid[x].size).contains(y))
                if (grid[x][y] > 0)
                    grid[x][y]++
        }

        fun getSafeGrid(x: Int, y: Int): Int? {
            if ((0 until grid.size).contains(x) && (0 until grid[x].size).contains(y))
                return grid[x][y]
            return null
        }


        var flashes = 0
        fun flash(x: Int, y: Int) {
            grid[x][y] = 0
            flashes++

            for (x1 in -1..1)
                for (y1 in -1..1)
                    if (x1 != 0 || y1 != 0) {
                        incSafeGrid(x + x1, y + y1)
                        if ((getSafeGrid(x + x1, y + y1) ?: 0) > 9) {
                            flash(x + x1, y + y1)
                        }
                    }
        }


        for (s in 1..100) {

            for (x in 0 until grid.size) {
                for (y in 0 until grid[x].size) {
                    grid[x][y]++
                }
            }

            for (x in 0 until grid.size) {
                for (y in 0 until grid[x].size) {
                    if (grid[x][y] > 9) {
                        flash(x, y)
                    }
                }
            }
        }

        return flashes
    }

    fun part2(input: List<String>): Int {

        val grid = input.map { it.map { it.toString().toInt() }.toMutableList() }

        fun incSafeGrid(x: Int, y: Int) {
            if ((0 until grid.size).contains(x) && (0 until grid[x].size).contains(y))
                if (grid[x][y] > 0)
                    grid[x][y]++
        }

        fun getSafeGrid(x: Int, y: Int): Int? {
            if ((0 until grid.size).contains(x) && (0 until grid[x].size).contains(y))
                return grid[x][y]
            return null
        }


        var flashes = 0
        fun flash(x: Int, y: Int) {
            grid[x][y] = 0
            flashes++

            for (x1 in -1..1)
                for (y1 in -1..1)
                    if (x1 != 0 || y1 != 0) {
                        incSafeGrid(x + x1, y + y1)
                        if ((getSafeGrid(x + x1, y + y1) ?: 0) > 9) {
                            flash(x + x1, y + y1)
                        }
                    }
        }

        var allFlashing = false
        var step = 0
        while (!allFlashing) {
            step++

            for (x in 0 until grid.size) {
                for (y in 0 until grid[x].size) {
                    grid[x][y]++
                }
            }

            for (x in 0 until grid.size) {
                for (y in 0 until grid[x].size) {
                    if (grid[x][y] > 9) {
                        flash(x, y)
                    }
                }
            }

            allFlashing = grid.all { it.all { it == grid[0][0] } }
        }

        return step
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    val input = readInput("Day11")

    println(part1(testInput))
    check(part1(testInput) == 1656)
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 195)
    println(part2(input))
}

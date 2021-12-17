fun main() {
    fun part1(input: List<String>): Int {


        var board = (0..10000).map { _ -> (0..10000).map { _ -> 0 }.toMutableList() }.toMutableList()


        input.forEach {
            val (from, to) = it.split(" -> ")
            val (x1s, y1s) = from.split(",")
            val (x2s, y2s) = to.split(",")

            val x1 = x1s.toInt()
            val y1 = y1s.toInt()
            val x2 = x2s.toInt()
            val y2 = y2s.toInt()

            if (x1 == x2) {

                for (y in minOf(y1, y2)..maxOf(y1, y2)) {

                    val x = x1
                    board[y][x]++

                }

            }
            if (y1 == y2) {

                for (x in minOf(x1, x2)..maxOf(x1, x2)) {

                    val y = y1
                    board[y][x]++

                }

            }

        }



        return board.sumOf { it.count { it > 1 } }
    }

    fun part2(input: List<String>): Int {

        var board = (0..10000).map { _ -> (0..10000).map { _ -> 0 }.toMutableList() }.toMutableList()


        input.forEach {
            val (from, to) = it.split(" -> ")
            val (x1s, y1s) = from.split(",")
            val (x2s, y2s) = to.split(",")

            val x1 = x1s.toInt()
            val y1 = y1s.toInt()
            val x2 = x2s.toInt()
            val y2 = y2s.toInt()

            if (x1 == x2) {

                for (y in minOf(y1, y2)..maxOf(y1, y2)) {

                    val x = x1
                    board[y][x]++

                }

            } else if (y1 == y2) {

                for (x in minOf(x1, x2)..maxOf(x1, x2)) {

                    val y = y1
                    board[y][x]++

                }

            } else {

                var xs = (minOf(x1, x2)..maxOf(x1, x2)).toList()
                if (x1 > x2) {
                    xs = xs.reversed()
                }
                var ys = (minOf(y1, y2)..maxOf(y1, y2)).toList()
                if (y1 > y2) {
                    ys = ys.reversed()
                }

                assert(xs.size == ys.size)

                xs.zip(ys).forEach {

                    board[it.second][it.first]++

                }

            }

        }

        return board.sumOf { it.count { it > 1 } }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    val input = readInput("Day05")

    println(part1(testInput))
    check(part1(testInput) == 5)
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 12)
    println(part2(input))
}

fun main() {
    fun part1(input: List<String>): Int {
        val drawnNumbers = input.get(0).split(",").map { it.toInt() }

        val boards = input.drop(1).windowed(6, 6).map {
            it.drop(1).map { it.split(" ").filter { it != "" }.map { it.toInt() }.toMutableList() }.toMutableList()
        }

        for (number in drawnNumbers) {

            for (board in boards) {
                board.forEachIndexed { i, r ->
                    r.forEachIndexed { j, e ->
                        if (e == number) {
                            board[i][j] = -1
                        }
                    }
                }
                //println(board)

                if (board.any { r ->
                    r.all { it == -1 }
                }) {
                    //println("ro")
                    return board.sumOf { it.filter { it >= 0 }.sum() } * number
                }
                for (i in 0 until 5) {
                    if (board.map { it[i] }.all { it == -1 }) {
                        //println("col")
                        return board.sumOf { it.filter { it >= 0 }.sum() } * number
                    }
                }


            }

        }

        return 0
    }

    fun part2(input: List<String>): Int {
        val drawnNumbers = input.get(0).split(",").map { it.toInt() }

        val boards = input.drop(1).windowed(6, 6).map {
            it.drop(1).map { it.split(" ").filter { it != "" }.map { it.toInt() }.toMutableList() }.toMutableList()
        }

        val boardsDone = boards.map { false }.toMutableList()
        for (number in drawnNumbers) {

            var bid = 0
            for (board in boards) {
                board.forEachIndexed { i, r ->
                    r.forEachIndexed { j, e ->
                        if (e == number) {
                            board[i][j] = -1
                        }
                    }
                }
                //println(board)

                if (board.any { r ->
                        r.all { it == -1 }
                    }) {
                    //println("ro")
                    boardsDone[bid] = true
                    if (boardsDone.all { it }) {
                        return board.sumOf { it.filter { it >= 0 }.sum() } * number
                    }
                }
                for (i in 0 until 5) {
                    if (board.map { it[i] }.all { it == -1 }) {
                        //println("col")
                        boardsDone[bid] = true
                        if (boardsDone.all { it }) {
                            return board.sumOf { it.filter { it >= 0 }.sum() } * number
                        }
                    }
                }
                bid++

            }

        }

        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    val input = readInput("Day04")

    println(part1(testInput))
    check(part1(testInput) == 4512)
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 1924)
    println(part2(input))
}

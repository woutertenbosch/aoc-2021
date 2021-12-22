fun main() {

    fun part1(input: List<String>): Int {
        class DDie() {
            private var v = 0
            var rolls = 0
            fun roll(): Int {
                v %= 100
                rolls++
                return ++v
            }

            fun roll3(): Int {
                return (1..3).sumOf { roll() }
            }
        }

        var player1pos = input[0].last().toString().toInt() - 1
        var player2pos = input[1].last().toString().toInt() - 1
        var player1points = 0
        var player2points = 0
        val die = DDie()

        while (player1points < 1000 && player2points < 1000) {
            val p1throw = die.roll3()
            player1pos += p1throw
            player1pos %= 10
            player1points += player1pos + 1
            if (player1points >= 1000)
                break

            val p2throw = die.roll3()
            player2pos += p2throw
            player2pos %= 10
            player2points += player2pos + 1
        }

        return minOf(player1points, player2points) * die.rolls
    }

    fun part2(input: List<String>): Long {
        val player1pos = input[0].last().toString().toInt()
        val player2pos = input[1].last().toString().toInt()

        val throws = nested(listOf(1, 2, 3), 3).map { it.sum() }.toList()

        val mem = mutableMapOf<Pair<Pair<Int, Int>, Pair<Int, Int>>, Pair<Long, Long>>()

        fun calculate(pos1: Int, pos2: Int, points1: Int, points2: Int): Pair<Long, Long> {
            val key = (pos1 to pos2) to (points1 to points2)
            if (mem.containsKey(key))
                return mem[key]!!

            var wins = 0L to 0L
            for (throw1 in throws) {
                val newPos1 = (pos1 - 1 + throw1) % 10 + 1
                val newPoints1 = points1 + newPos1

                if (newPoints1 >= 21) {
                    wins = wins.first + 1 to wins.second
                    continue
                }

                for (throw2 in throws) {
                    val newPos2 = (pos2 - 1 + throw2) % 10 + 1
                    val newPoints2 = points2 + newPos2

                    if (newPoints2 >= 21) {
                        wins = wins.first to wins.second + 1
                        continue
                    }

                    val subs = calculate(newPos1, newPos2, newPoints1, newPoints2)
                    wins = wins.first + subs.first to wins.second + subs.second
                }
            }
            mem[key] = wins

            return wins
        }

        val w = calculate(player1pos, player2pos, 0, 0)

        println(w)
        return maxOf(w.first, w.second)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day21_test")
    val input = readInput("Day21")

    println(part1(testInput))
    check(part1(testInput) == 739785)
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 444356092776315L)
    println(part2(input))
}

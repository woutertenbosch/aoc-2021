import kotlin.math.sign

fun main() {
    fun part1(input: List<String>): Int {
        val target = input.first().removePrefix("target area: x=").split(", y=").map { it.split("..") }.map { it[0].toInt()..it[1].toInt() }
        val (targetX, targetY) = target



        fun simulateProbe(ivx: Int, ivy: Int): Int? {
            var vx = ivx
            var vy = ivy
            var x = 0
            var y = 0
            var hy = 0

            while (true) {
                x += vx
                y += vy
                vx -= vx.sign
                vy -= 1
                hy = maxOf(hy, y)

                if (targetX.contains(x) && targetY.contains(y)) {
                    return hy
                }

                if (y < -1000)
                    return null
            }
        }

        return  (0..200).maxOf { ivx -> (0..200).maxOf { ivy -> simulateProbe(ivx, ivy) ?: Int.MIN_VALUE } }
    }

    fun part2(input: List<String>): Int {
        val target = input.first().removePrefix("target area: x=").split(", y=").map { it.split("..") }.map { it[0].toInt()..it[1].toInt() }
        val (targetX, targetY) = target

        fun simulateProbe(ivx: Int, ivy: Int): Int? {
            var vx = ivx
            var vy = ivy
            var x = 0
            var y = 0
            var hy = 0

            while (true) {
                x += vx
                y += vy
                vx -= vx.sign
                vy -= 1
                hy = maxOf(hy, y)

                if (targetX.contains(x) && targetY.contains(y)) {
                    return hy
                }

                if (y < -100)
                    return null
            }
        }

        return (-2000..2000).sumOf { ivx -> (-2000..2000).count { ivy -> simulateProbe(ivx, ivy) != null } }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day17_test")
    val input = readInput("Day17")

    println(part1(testInput))
    check(part1(testInput) == 45)
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 112)
    println(part2(input))
}

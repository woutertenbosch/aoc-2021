fun main() {
    fun part1(input: List<String>): Long {


        val fish = input.first().split(",").map { it.trim().toInt() }.toMutableList()

        val fishCounts = (0..8).map { a -> fish.count { it == a }.toLong() }.toMutableList()

        for (i in 0 until 80) {
            val newFish = fishCounts[0]

            fishCounts[0] = fishCounts[1]
            fishCounts[1] = fishCounts[2]
            fishCounts[2] = fishCounts[3]
            fishCounts[3] = fishCounts[4]
            fishCounts[4] = fishCounts[5]
            fishCounts[5] = fishCounts[6]
            fishCounts[6] = fishCounts[7] + newFish
            fishCounts[7] = fishCounts[8]
            fishCounts[8] = newFish
        }

        return fishCounts.sum()
    }

    fun part2(input: List<String>): Long {

        val fish = input.first().split(",").map { it.trim().toInt() }.toMutableList()

        val fishCounts = (0..8).map { a -> fish.count { it == a }.toLong() }.toMutableList()

        for (i in 0 until 256) {
            val newFish = fishCounts[0]

            fishCounts[0] = fishCounts[1]
            fishCounts[1] = fishCounts[2]
            fishCounts[2] = fishCounts[3]
            fishCounts[3] = fishCounts[4]
            fishCounts[4] = fishCounts[5]
            fishCounts[5] = fishCounts[6]
            fishCounts[6] = fishCounts[7] + newFish
            fishCounts[7] = fishCounts[8]
            fishCounts[8] = newFish
        }

        return fishCounts.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    val input = readInput("Day06")

    println(part1(testInput))
    check(part1(testInput) == 5934L)
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 26984457539L)
    println(part2(input))
}

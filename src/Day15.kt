import java.util.*

fun main() {
    fun part1(input: List<String>): Int {

        val grid = input.map { it.map { it.toString().toInt() }.toMutableList() }


        val sumToMem = mutableMapOf<Pair<Int, Int>, Int>()
        class CustomIntegerComparator : Comparator<Pair<Int, Int>> {
            override fun compare(o1: Pair<Int, Int>, o2: Pair<Int, Int>): Int {
                return if (sumToMem[o1]!! < sumToMem[o2]!!) -1 else 1
            }
        }

        for (p in grid.allPositions())
            sumToMem[p] = Int.MAX_VALUE
        sumToMem[0 to 0] = 0

        val unvisitedSet = grid.allPositions().toMutableSet()
        val prioQ = PriorityQueue(CustomIntegerComparator())

        var currentNode = 0 to 0
        var done = false
        while (!done) {

            for (neighbour in grid.neighbours(currentNode).filter { unvisitedSet.contains(it) }) {

                val score = grid.get(neighbour)!! + sumToMem[currentNode]!!
                if (score < sumToMem[neighbour]!!) {
                    sumToMem[neighbour] = score
                    if (prioQ.contains(neighbour))
                        prioQ.remove(neighbour)
                    prioQ.add(neighbour)
                }
            }
            unvisitedSet.remove(currentNode)
            if (prioQ.contains(currentNode))
                prioQ.remove(currentNode)

            if (currentNode == grid.maxX() to grid.maxY())
                done = true else
                currentNode = prioQ.poll()
        }

        return sumToMem[grid.maxX() to grid.maxY()]!!
    }

    fun part2(input: List<String>): Int {
        val baseGrid = input.map { it.map { it.toString().toInt() }.toMutableList() }

        val grid = (0 until ((baseGrid.maxY() + 1) * 5)).map { _ ->
            (0 until ((baseGrid.maxX() + 1) * 5)).map { _ ->
                0
            }.toMutableList()
        }

        for (lx in 0 until 5) {
            for (ly in 0 until 5) {
                for (p in baseGrid.allPositions()) {

                    val multi = lx + ly
                    grid[p.second + lx * (baseGrid.maxX() + 1)][p.first + ly * (baseGrid.maxY() + 1)] = ((baseGrid.get(p)!! - 1) + multi) % 9 + 1
                }

            }
        }

        val sumToMem = mutableMapOf<Pair<Int, Int>, Int>()
        class CustomIntegerComparator : Comparator<Pair<Int, Int>> {
            override fun compare(o1: Pair<Int, Int>, o2: Pair<Int, Int>): Int {
                return if (sumToMem[o1]!! < sumToMem[o2]!!) -1 else 1
            }
        }

        for (p in grid.allPositions())
            sumToMem[p] = Int.MAX_VALUE
        sumToMem[0 to 0] = 0

        val unvisitedSet = grid.allPositions().toMutableSet()
        val prioQ = PriorityQueue(CustomIntegerComparator())

        var currentNode = 0 to 0
        var done = false
        while (!done) {

            for (neighbour in grid.neighbours(currentNode).filter { unvisitedSet.contains(it) }) {

                val score = grid.get(neighbour)!! + sumToMem[currentNode]!!
                if (score < sumToMem[neighbour]!!) {
                    sumToMem[neighbour] = score
                    if (prioQ.contains(neighbour))
                        prioQ.remove(neighbour)
                    prioQ.add(neighbour)
                }
            }
            unvisitedSet.remove(currentNode)
            if (prioQ.contains(currentNode))
                prioQ.remove(currentNode)

            if (currentNode == grid.maxX() to grid.maxY())
                done = true else
                currentNode = prioQ.poll()
        }

        return sumToMem[grid.maxX() to grid.maxY()]!!
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day15_test")
    val input = readInput("Day15")

    println(part1(testInput))
    check(part1(testInput) == 40)
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 315)
    println(part2(input))
}


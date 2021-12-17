fun main() {
    fun part1(input: List<String>): Int {

        val rules = input.drop(2).map {
            (it[0] to it[1]) to it[6]
        }

        val pairs =  input.first().zipWithNext().groupBy { it }.map { it.key to it.value.count() }.toMap().toMutableMap()

        for (s in 0 until 10) {

            val inserts = mutableListOf<Pair<Pair<Char, Char>, Int>>()
            for (r in rules) {
                val insertCount = pairs[r.first] ?: 0
                val insertPair1 = r.first.first to r.second
                val insertPair2 = r.second to r.first.second
                inserts.add(insertPair1 to insertCount)
                inserts.add(insertPair2 to insertCount)
                inserts.add(r.first to -insertCount)
            }
            for (i in inserts) {
                pairs[i.first] = i.second + (pairs[i.first] ?: 0)
            }

        }

        val occurs = mutableMapOf<Char, Int>()
        occurs[input.first().last()] = 1
        for (p in pairs) {
            occurs.putIfAbsent(p.key.first, 0)
            occurs[p.key.first] = occurs[p.key.first]!! + p.value
        }

        val sortedOccurs = occurs.toList().sortedBy { it.second }

        return sortedOccurs.last().second - sortedOccurs.first().second
    }

    fun part2(input: List<String>): Long {
        val rules = input.drop(2).map {
            (it[0] to it[1]) to it[6]
        }

        val pairs = input.first().zipWithNext().groupBy { it }.map { it.key to it.value.count().toLong() }.toMap().toMutableMap()

        for (s in 0 until 40) {

            val inserts = mutableListOf<Pair<Pair<Char, Char>, Long>>()
            for (r in rules) {
                val insertCount = pairs[r.first] ?: 0
                val insertPair1 = r.first.first to r.second
                val insertPair2 = r.second to r.first.second
                inserts.add(insertPair1 to insertCount)
                inserts.add(insertPair2 to insertCount)
                inserts.add(r.first to -insertCount)
            }
            for (i in inserts) {
                pairs[i.first] = i.second + (pairs[i.first] ?: 0)
            }

        }

        val occurs = mutableMapOf<Char, Long>()
        occurs[input.first().last()] = 1
        for (p in pairs) {
            occurs.putIfAbsent(p.key.first, 0)
            occurs[p.key.first] = occurs[p.key.first]!! + p.value
        }

        val sortedOccurs = occurs.toList().sortedBy { it.second }

        return sortedOccurs.last().second - sortedOccurs.first().second
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    val input = readInput("Day14")

    println(part1(testInput))
    check(part1(testInput) == 1588)
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 2188189693529L)
    println(part2(input))
}

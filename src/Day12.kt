fun main() {
    fun part1(input: List<String>): Int {

        val nodes = mutableMapOf<String, MutableList<String>>()
        input.forEach {
            val (from, to) = it.split("-")

            nodes.putIfAbsent(from, mutableListOf())
            nodes.putIfAbsent(to, mutableListOf())
            nodes[from]!!.add(to)
            nodes[to]!!.add(from)
        }

        fun findPath(node : String, tabuList: Set<String>) : List<String> {
            val paths = mutableListOf<String>()
            for (to in nodes[node]!!) {
                if (to == "end") {
                    paths.add(node + "-end")
                    continue
                }
                if (to == "start")
                    continue
                if (!tabuList.contains(to)) {
                    if (to.first().isLowerCase()) {
                        val newTabu = tabuList.plus(to).toSet()
                        paths.addAll(findPath(to, newTabu).map { "$node-$it" })
                    } else {
                        paths.addAll(findPath(to, tabuList).map { "$node-$it" })
                    }
                }
            }
            return paths
        }
        val paths = findPath("start", setOf())

        //    println(paths.sorted().joinToString("\n"))

        return paths.size
    }

    fun part2(input: List<String>): Int {

        val nodes = mutableMapOf<String, MutableList<String>>()
        input.forEach {
            val (from, to) = it.split("-")

            nodes.putIfAbsent(from, mutableListOf())
            nodes.putIfAbsent(to, mutableListOf())
            nodes[from]!!.add(to)
            nodes[to]!!.add(from)
        }

        fun findPath(node : String, tabuList: Set<String>, jokerUsed: Boolean) : List<String> {
            val paths = mutableListOf<String>()
            for (to in nodes[node]!!) {
                if (to == "end") {
                    paths.add(node + "-end")
                    continue
                }
                if (to == "start")
                    continue
                if (!tabuList.contains(to)) {
                    if (to.first().isLowerCase()) {
                        val newTabu = tabuList.plus(to).toSet()
                        paths.addAll(findPath(to, newTabu, jokerUsed).map { "$node-$it" })
                    } else {
                        paths.addAll(findPath(to, tabuList, jokerUsed).map { "$node-$it" })
                    }
                } else if (!jokerUsed) {
                    paths.addAll(findPath(to, tabuList, true).map { "$node-$it" })
                }
            }
            return paths
        }
        val paths = findPath("start", setOf(), false)

        //    println(paths.sorted().joinToString("\n"))

        return paths.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    val input = readInput("Day12")

    println(part1(testInput))
    check(part1(testInput) == 10)
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 36)
    println(part2(input))
}

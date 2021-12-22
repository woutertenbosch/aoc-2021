fun main() {
    fun readInput(input: List<String>): Map<Int, List<XYZ>> {

        val result = mutableMapOf<Int, MutableList<XYZ>>()
        var scanner = 0
        for (line in input) {
            if (line.startsWith("---")) {
                scanner = line.filter { it.isDigit() }.toInt()
                result[scanner] = mutableListOf()
            } else if (line.contains(",")) {
                val s = line.split(",").map { it.toInt() }
                result[scanner]!!.add(XYZ(s[0], s[1], s[2]))
            }
        }
        return result
    }

    fun findOverlappingBeacons(beacons1: List<XYZ>, beacons2: List<XYZ>): Pair<XYZ, Orientation>? {
        val m = mutableListOf<Pair<XYZ, Orientation>>()
        for (ro in Orientation.orientations) {
            val diffs = mutableMapOf<Pair<XYZ, XYZ>, XYZ>()
            for (b1 in beacons1) {
                for (b2 in beacons2) {
                    diffs[b1 to b2] = b2.reorient(ro).minus(b1)
                }
            }
            val matches = diffs.values.groupBy { it }.map { it.value.count() to it.key }.filter { it.first >= 12 }.sortedByDescending { -it.first }
            m.addAll(matches.map { it.second to ro })
        }
        if (m.size == 1) {
            return m.first()
        } else if (m.size > 1) {
            error("multimatch")
        }
        return null
    }

    fun part1(input: List<String>): Int {
        val source = readInput(input)

        val beacons = mutableListOf<XYZ>()
        beacons.addAll(source[0]!!)

        val keysToDo = source.keys.minus(0).toMutableSet()

        while (keysToDo.isNotEmpty()) {
            val doneKeys = mutableSetOf<Int>()
            for (s in keysToDo) {
                val fix = findOverlappingBeacons(beacons, source[s]!!)
                if (fix != null) {
                   // println(s)
                   // println(fix)
                    beacons.addAll(source[s]!!.map {
                        it.reorient(fix.second).minus(fix.first)
                    })
                    val db = beacons.distinct()
                    beacons.clear()
                    beacons.addAll(db)
                  //  println(beacons.size)
                    doneKeys.add(s)
                }
            }
            keysToDo.removeAll(doneKeys)
        }
        return beacons.distinct().size
    }

    fun part2(input: List<String>): Int {
        val source = readInput(input)

        val beacons = mutableListOf<XYZ>()
        beacons.addAll(source[0]!!)

        val keysToDo = source.keys.minus(0).toMutableSet()

        val scanners = mutableListOf<XYZ>()
        scanners.add(XYZ(0,0,0))
        while (keysToDo.isNotEmpty()) {
            val doneKeys = mutableSetOf<Int>()
            for (s in keysToDo) {
                val fix = findOverlappingBeacons(beacons, source[s]!!)
                if (fix != null) {
                    // println(s)
                    // println(fix)
                    scanners.add(fix.first)
                    beacons.addAll(source[s]!!.map {
                        it.reorient(fix.second).minus(fix.first)
                    })
                    val db = beacons.distinct()
                    beacons.clear()
                    beacons.addAll(db)
                    //  println(beacons.size)
                    doneKeys.add(s)
                }
            }
            keysToDo.removeAll(doneKeys)
        }
        return scanners.maxOf { s1 -> scanners.maxOf { s2 -> s1.manhattanDistance(s2) } }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day19_test")
    val input = readInput("Day19")

    println(part1(testInput))
    check(part1(testInput) == 79)
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 3621)
    println(part2(input))
}

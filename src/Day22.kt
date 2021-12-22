import kotlin.math.max

fun main() {
    fun part1(input: List<String>): Long {
        val rules = input.map {
            val on = it.startsWith("on")
            val f = it.replace("..", ",").filter { it.isDigit() || it == ',' || it == '-' }.split(",").map { it.toInt() }
            on to (XYZ(f[0], f[2], f[4]) to XYZ(f[1], f[3], f[5]))
        }

        val minVals = XYZ(
            rules.minOf { it.second.first.x },
            rules.minOf { it.second.first.y },
            rules.minOf { it.second.first.z },
        )
        val maxVals = XYZ(
            rules.maxOf { it.second.second.x },
            rules.maxOf { it.second.second.y },
            rules.maxOf { it.second.second.z },
        )

        val mem = mutableMapOf<Pair<Pair<XYZ, XYZ>, Int>, Long>()

        fun onInRegion(from: XYZ, to: XYZ, rules: List<Pair<Boolean, Pair<XYZ, XYZ>>>): Long {

            val key = (from to to) to rules.count()
            if (mem.containsKey(key))
                return mem[key]!!

            if (rules.isEmpty()) return 0L
            val prevRules = rules.dropLast(1).toList()
            val prevCount = onInRegion(from, to, prevRules)

            val lastRule = rules.last()
            val (oRuleF, oRuleT) = lastRule.second

            val ruleF = XYZ(
                maxOf(oRuleF.x, from.x),
                maxOf(oRuleF.y, from.y),
                maxOf(oRuleF.z, from.z),
            )

            val ruleT = XYZ(
                minOf(oRuleT.x, to.x),
                minOf(oRuleT.y, to.y),
                minOf(oRuleT.z, to.z),
            )

            val cubesInRegions = 1L * max(0, ruleT.x - ruleF.x + 1) * max(0, ruleT.y - ruleF.y + 1) * max(0, ruleT.z - ruleF.z + 1)
            if (cubesInRegions == 0L)
                return prevCount

            val result = if (lastRule.first) {
                val turnedOn = cubesInRegions - onInRegion(ruleF, ruleT, prevRules)
                prevCount + turnedOn
            } else {
                val turnedOff = onInRegion(ruleF, ruleT, prevRules)
                prevCount - turnedOff
            }
            mem[key] = result
            return result
        }

        return onInRegion(XYZ(-50,-50,-50), XYZ(50,50,50), rules)
    }

    fun part2(input: List<String>): Long {

        val rules = input.map {
            val on = it.startsWith("on")
            val f = it.replace("..", ",").filter { it.isDigit() || it == ',' || it == '-' }.split(",").map { it.toInt() }
            on to (XYZ(f[0], f[2], f[4]) to XYZ(f[1], f[3], f[5]))
        }

        val minVals = XYZ(
            rules.minOf { it.second.first.x },
            rules.minOf { it.second.first.y },
            rules.minOf { it.second.first.z },
        )
        val maxVals = XYZ(
            rules.maxOf { it.second.second.x },
            rules.maxOf { it.second.second.y },
            rules.maxOf { it.second.second.z },
        )

        val mem = mutableMapOf<Pair<Pair<XYZ, XYZ>, Int>, Long>()

        fun onInRegion(from: XYZ, to: XYZ, rules: List<Pair<Boolean, Pair<XYZ, XYZ>>>): Long {

            val key = (from to to) to rules.count()
            if (mem.containsKey(key))
                return mem[key]!!

            if (rules.isEmpty()) return 0L
            val prevRules = rules.dropLast(1).toList()
            val prevCount = onInRegion(from, to, prevRules)

            val lastRule = rules.last()
            val (oRuleF, oRuleT) = lastRule.second

            val ruleF = XYZ(
                maxOf(oRuleF.x, from.x),
                maxOf(oRuleF.y, from.y),
                maxOf(oRuleF.z, from.z),
            )

            val ruleT = XYZ(
                minOf(oRuleT.x, to.x),
                minOf(oRuleT.y, to.y),
                minOf(oRuleT.z, to.z),
            )

            val cubesInRegions = 1L * max(0, ruleT.x - ruleF.x + 1) * max(0, ruleT.y - ruleF.y + 1) * max(0, ruleT.z - ruleF.z + 1)
            if (cubesInRegions == 0L)
                return prevCount

            val result = if (lastRule.first) {
                val turnedOn = cubesInRegions - onInRegion(ruleF, ruleT, prevRules)
                prevCount + turnedOn
            } else {
                val turnedOff = onInRegion(ruleF, ruleT, prevRules)
                prevCount - turnedOff
            }
            mem[key] = result
            return result
        }

        return onInRegion(minVals, maxVals, rules)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day22_test")
    val testInput2 = readInput("Day22_test2")
    val input = readInput("Day22")

    println(part1(testInput))
    check(part1(testInput) == 590784L)
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput2) == 2758514936282235L)
    println(part2(input))
}

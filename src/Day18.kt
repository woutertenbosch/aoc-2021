import kotlin.math.ceil
import kotlin.math.floor

fun main() {

    abstract class Fish {

        abstract fun setByIndex(index: Int, value: Int)
        abstract fun getByIndex(index: Int): Int?
        abstract fun updateIndices(index: Int): Int
        abstract fun findExplodingPair(depth: Int): Int?
        abstract fun removeExplodingPair(depth: Int): Boolean
        abstract fun splitFirstPair(): Boolean
        abstract fun getMagnitude(): Int

    }

    class FishNum(var number: Int) : Fish() {

        private var index = 0

        override fun getByIndex(index: Int): Int? {
            return if (index == this.index) number else null
        }

        override fun setByIndex(index: Int, value: Int) {
            if (index == this.index) number = value
        }

        override fun updateIndices(index: Int): Int {
            this.index = index
            return index + 1
        }

        override fun findExplodingPair(depth: Int): Int? {
            return null
        }

        override fun removeExplodingPair(depth: Int): Boolean {
            return false
        }

        override fun splitFirstPair(): Boolean {
            return false
        }

        override fun toString(): String {
            return number.toString()
        }

        override fun getMagnitude(): Int {
            return number
        }
    }

    class FishPair(var left: Fish, var right: Fish) : Fish() {

        private var index = 0

        override fun getByIndex(index: Int): Int? {
            return left.getByIndex(index) ?: right.getByIndex(index)
        }

        override fun setByIndex(index: Int, value: Int) {
            left.setByIndex(index, value)
            right.setByIndex(index, value)
        }

        override fun updateIndices(index: Int): Int {
            this.index = index
            return right.updateIndices(left.updateIndices(index))
        }

        override fun findExplodingPair(depth: Int): Int? {
            if (depth == 4) {
                return index
            }
            val leftResult = left.findExplodingPair(depth + 1)
            if (leftResult != null)
                return leftResult
            return right.findExplodingPair(depth + 1)
        }

        override fun removeExplodingPair(depth: Int): Boolean {
            if (depth == 3) {
                if (left is FishPair) {
                    left = FishNum(0)
                    return true
                }
                if (right is FishPair) {
                    right = FishNum(0)
                    return true
                }
            }
            if (left.removeExplodingPair(depth + 1))
                return true
            return right.removeExplodingPair(depth + 1)
        }

        override fun splitFirstPair(): Boolean {

            if (left is FishNum && (left as FishNum).number >= 10) {
                val halfNum = (left as FishNum).number / 2f
                left = FishPair(FishNum(floor(halfNum).toInt()), FishNum(ceil(halfNum).toInt()))
                return true
            }

            if (left.splitFirstPair())
                return true

            if (right is FishNum && (right as FishNum).number >= 10) {
                val halfNum = (right as FishNum).number / 2f
                right = FishPair(FishNum(floor(halfNum).toInt()), FishNum(ceil(halfNum).toInt()))
                return true
            }

            return right.splitFirstPair()
        }

        override fun toString(): String {
            return "[$left,$right]"
        }

        override fun getMagnitude(): Int {
            return 3 * left.getMagnitude() + 2 * right.getMagnitude()
        }
    }

    fun part1(input: List<String>): Int {

        fun parse(inp: String): Fish {
            if (inp.startsWith('[')) {
                val l = inp.removePrefix("[").removeSuffix("]")
                var depth = 0
                var splitAt = 0
                for (i in l.indices) {
                    if (l[i] == '[') {
                        depth++
                    } else if (l[i] == ']') {
                        depth--
                    } else if (l[i] == ',' && depth == 0) {
                        splitAt = i
                    }
                }
                val p1 = l.substring(0 until splitAt)
                val p2 = l.substring(splitAt + 1 until l.length)
                return FishPair(parse(p1), parse(p2))
            } else {
                return FishNum(inp.toInt())
            }
        }

        val parsed = input.map { parse(it) }

        var sf = parsed.first()
        var sfl = sf.updateIndices(0)

        for (inFish in parsed.drop(1)) {
            sf = FishPair(sf, inFish)
            sfl = sf.updateIndices(0)
            // println(sf)
            var done = false
            while (!done) {
                val explodingIndex = sf.findExplodingPair(0)
                if (explodingIndex != null) {
                    if (explodingIndex - 1 >= 0) {
                        sf.setByIndex(explodingIndex - 1, sf.getByIndex(explodingIndex - 1)!! + sf.getByIndex(explodingIndex)!!)
                    }
                    if (explodingIndex + 2 <= sfl - 1) {
                        sf.setByIndex(explodingIndex + 2, sf.getByIndex(explodingIndex + 2)!! + sf.getByIndex(explodingIndex + 1)!!)
                    }
                    sf.removeExplodingPair(0)
                    sfl = sf.updateIndices(0)
                    //     println("    after explode:  " + sf)
                    continue
                }
                if (sf.splitFirstPair()) {
                    sfl = sf.updateIndices(0)
                    //      println("    after split:    " + sf)
                    continue
                }
                done = true
            }


        }
        //println(sf)

        return sf.getMagnitude()
    }

    fun part2(input: List<String>): Int {
        fun parse(inp: String): Fish {
            if (inp.startsWith('[')) {
                val l = inp.removePrefix("[").removeSuffix("]")
                var depth = 0
                var splitAt = 0
                for (i in l.indices) {
                    if (l[i] == '[') {
                        depth++
                    } else if (l[i] == ']') {
                        depth--
                    } else if (l[i] == ',' && depth == 0) {
                        splitAt = i
                    }
                }
                val p1 = l.substring(0 until splitAt)
                val p2 = l.substring(splitAt + 1 until l.length)
                return FishPair(parse(p1), parse(p2))
            } else {
                return FishNum(inp.toInt())
            }
        }

        val inputRange = 0 until input.size
        val r = inputRange.flatMap { sf1i ->
            inputRange.filter { it != sf1i }.map { sf2i ->

                val parsed = input.map { parse(it) }

                var sf = FishPair(parsed[sf1i], parsed[sf2i])
                var sfl = sf.updateIndices(0)

                var done = false
                while (!done) {
                    val explodingIndex = sf.findExplodingPair(0)
                    if (explodingIndex != null) {
                        if (explodingIndex - 1 >= 0) {
                            sf.setByIndex(explodingIndex - 1, sf.getByIndex(explodingIndex - 1)!! + sf.getByIndex(explodingIndex)!!)
                        }
                        if (explodingIndex + 2 <= sfl - 1) {
                            sf.setByIndex(explodingIndex + 2, sf.getByIndex(explodingIndex + 2)!! + sf.getByIndex(explodingIndex + 1)!!)
                        }
                        sf.removeExplodingPair(0)
                        sfl = sf.updateIndices(0)
                        //     println("    after explode:  " + sf)
                        continue
                    }
                    if (sf.splitFirstPair()) {
                        sfl = sf.updateIndices(0)
                        //      println("    after split:    " + sf)
                        continue
                    }
                    done = true
                }

                (sf1i to sf2i) to sf.getMagnitude()
            }
        }


        return r.maxOf { it.second }
        //return sf.getMagnitude()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day18_test")
    val input = readInput("Day18")

    println(part1(testInput))
    check(part1(testInput) == 4140)
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 3993)
    println(part2(input))
}

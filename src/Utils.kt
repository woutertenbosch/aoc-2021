import java.io.File

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

fun <X> permutations(input: List<X>): Sequence<List<X>> {
    return sequence {
        if (input.isEmpty()) {
            yield(listOf<X>())
        } else {
            for (element in input) {
                yieldAll(permutations(input.filterNot { it == element }).map { mutableListOf(element).plus(it) })
            }
        }
    }
}

typealias Grid = List<MutableList<Int>>

fun Grid.get(x: Int, y: Int): Int? {
    return getOrNull(y)?.getOrNull(x)
}

fun Grid.get(p: Pair<Int, Int>): Int? {
    return get(p.first, p.second)
}

fun Grid.neighbours(p: Pair<Int, Int>): List<Pair<Int, Int>> {
    return listOf(
        p.first + 1 to p.second,
        p.first to p.second + 1,
        p.first - 1 to p.second,
        p.first to p.second - 1
    ).filter { inBounds(it.first, it.second) }
}

fun Grid.print() {
    println(joinToString("\n") {
        it.joinToString("")
    })
}

fun Grid.maxX(): Int {
    return get(0).lastIndex
}

fun Grid.maxY(): Int {
    return lastIndex
}

fun Grid.inBounds(x: Int, y: Int): Boolean {
    return x >= 0 && x <= maxX() && y >= 0 && y <= maxY()
}

fun Grid.allPositions(): Sequence<Pair<Int, Int>> {
    return sequence {
        for (x in 0..maxX())
            for (y in 0..maxY())
                yield(x to y)
    }
}



import java.io.File
import kotlin.math.abs

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

fun <X> nested(input: List<X>, repeat: Int) : Sequence<List<X>> {
    return sequence {
        if (repeat == 1) {
            for (i in input) {
                yield(listOf(i))
            }
        } else {
            for (i in input) {
                yieldAll(nested(input, repeat - 1).map { mutableListOf(i).plus(it) })
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


data class XYZ(val x: Int, val y: Int, val z: Int) : Comparable<XYZ> {
    fun plus(xyz: XYZ): XYZ {
        return XYZ(x + xyz.x, y + xyz.y, z + xyz.z)
    }

    fun minus(xyz: XYZ): XYZ {
        return XYZ(x - xyz.x, y - xyz.y, z - xyz.z)
    }

    fun manhattanDistance(xyz: XYZ): Int {
        return abs(x - xyz.x) + abs(y - xyz.y) + abs(z - xyz.z)
    }

    fun reorient(orientation: Orientation): XYZ {
        val newZ = get(orientation.forwardDirection)
        val newY = get(orientation.upDirection)
        val newX = get(orientation.rightDirection)
        return XYZ(newX, newY, newZ)
    }

    fun get(direction: XYZDirection): Int {
        return when (direction) {
            XYZDirection.PX -> x
            XYZDirection.NX -> -x
            XYZDirection.PY -> y
            XYZDirection.NY -> -y
            XYZDirection.PZ -> z
            XYZDirection.NZ -> -z
        }
    }

    override fun compareTo(other: XYZ): Int {
        return x.compareTo(other.x)
    }

    override fun toString(): String {
        return "$x,$y,$z"
    }
}

enum class XYZDirection(val xyz: XYZ) {
    PX(XYZ(1, 0, 0)),
    NX(XYZ(-1, 0, 0)),
    PY(XYZ(0, 1, 0)),
    NY(XYZ(0, -1, 0)),
    PZ(XYZ(0, 0, 1)),
    NZ(XYZ(0, 0, -1));

    fun getUpDirections(): List<XYZDirection> {
        return when (this) {
            PX, NX -> listOf(PY, NY, PZ, NZ)
            PY, NY -> listOf(PX, NX, PZ, NZ)
            PZ, NZ -> listOf(PX, NX, PY, NY)
        }
    }
}

data class Orientation(val forwardDirection: XYZDirection, val upDirection: XYZDirection) {

    companion object {
        val orientations = XYZDirection.values().flatMap { fd -> fd.getUpDirections().map { Orientation(fd, it) } }
        val default = Orientation(XYZDirection.PZ, XYZDirection.PY)
    }

    val rightDirection = kotlin.run {
        val a = upDirection.xyz
        val b = forwardDirection.xyz
        val v = XYZ(
            a.y * b.z - a.z * b.y,
            a.z * b.x - a.x * b.z,
            a.x * b.y - a.y * b.x,
        )
        XYZDirection.values().filter { it.xyz == v }.first()
    }

}
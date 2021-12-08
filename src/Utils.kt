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
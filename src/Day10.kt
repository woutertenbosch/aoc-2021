fun main() {
    fun part1(input: List<String>): Int {
        val inp = input.map({ it.map { it } })

        var score = 0;
        for (l in inp) {

            val stack = mutableListOf<Char>()
            for (c in l) {

                if (c == ')') {
                    if (stack.last() != '(') {
                        score += 3;
                    }
                    stack.removeLast()
                    continue
                }

                if (c == ']') {
                    if (stack.last() != '[') {
                        score += 57;
                    }
                    stack.removeLast()
                    continue
                }

                if (c == '}') {
                    if (stack.last() != '{') {
                        score += 1197;
                    }
                    stack.removeLast()
                    continue
                }


                if (c == '>') {
                    if (stack.last() != '<') {
                        score += 25137;
                    }
                    stack.removeLast()
                    continue
                }



                stack.add(c)

            }

        }

        return score
    }

    fun part2(input: List<String>): Long {
        val inp = input.map({ it.map { it } })


        val scores = mutableListOf<Long>()

        for (l in inp) {

            var correct = true
            val stack = mutableListOf<Char>()
            for (c in l) {

                if (c == ')') {
                    if (stack.last() != '(') {
                        correct = false
                    }
                    stack.removeLast()
                    continue
                }

                if (c == ']') {
                    if (stack.last() != '[') {
                        correct = false
                    }
                    stack.removeLast()
                    continue
                }

                if (c == '}') {
                    if (stack.last() != '{') {
                        correct = false
                    }
                    stack.removeLast()
                    continue
                }

                if (c == '>') {
                    if (stack.last() != '<') {
                        correct = false
                    }
                    stack.removeLast()
                    continue
                }

                stack.add(c)
            }


            if (correct) {
                var score = 0L
                for (s in stack.reversed()) {

                    if (s == '(') {
                        score = score * 5 + 1
                    }
                    if (s == '[') {
                        score = score * 5 + 2
                    }

                    if (s == '{') {
                        score = score * 5 + 3
                    }

                    if (s == '<') {
                        score = score * 5 + 4
                    }


                }

                scores.add(score)
            }
        }

        println(scores.sorted())
        return scores.sorted()[(scores.size - 1) / 2]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    val input = readInput("Day10")

    println(part1(testInput))
    check(part1(testInput) == 26397)
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 288957L)
    println(part2(input))
}

fun main() {

    class MutableString(private var str : String) {

        fun parse(bits: Int) : Long {
            val returnValue = str.take(bits)
            str = str.drop(bits)
            return returnValue.toLong(2)
        }

        fun get(bits: Int) : String {
            val returnValue = str.take(bits)
            str = str.drop(bits)
            return returnValue
        }

        fun set(str1: String) {
            str = str1
        }

        fun remainingString() : String {
            return str
        }
    }

    fun part1(input: List<String>): Int {

        val bits = input.first().map { Integer.parseInt(it.toString(), 16) }.joinToString("") { Integer.toBinaryString(it).padStart(4, '0') }

        var versionNumbers = 0

        fun parsePacket(packetBits: String) : String {
            val packet = MutableString(packetBits)

            val version = packet.parse(3)
            val typeId = packet.parse(3)
            val operator = typeId != 4L

            versionNumbers += version.toInt()
            if (operator) {
                val lengthTypeId = packet.parse(1)
                if (lengthTypeId == 0L) {
                    val length = packet.parse(15).toInt()
                    var subPackets = packet.get(length)

                    while (subPackets != "") {
                        subPackets = parsePacket(subPackets)
                    }
                } else {
                    var subPacketsCount = packet.parse(11)
                    var subPacketString = packet.remainingString()
                    while (subPacketsCount > 0) {
                        subPacketString = parsePacket(subPacketString)
                        subPacketsCount--
                    }
                    packet.set(subPacketString)
                }
            } else {

                var done = false
                var data = ""
                while (!done) {
                    done = packet.parse(1) == 0L
                    data += packet.get(4)
                }
                //println(data.toInt(2))

            }

            return packet.remainingString()
        }

        parsePacket(bits)

        return versionNumbers
    }

    fun part2(input: List<String>): Long {

        val bits = input.first().map { Integer.parseInt(it.toString(), 16) }.joinToString("") { Integer.toBinaryString(it).padStart(4, '0') }

        fun parsePacket(packetBits: String) : Pair<Long, String> {
            val packet = MutableString(packetBits)

            val version = packet.parse(3)
            val typeId = packet.parse(3).toInt()
            val operator = typeId != 4

            var rV : Long? = null

            if (operator) {
                val lengthTypeId = packet.parse(1)
                val values = mutableListOf<Long>()
                if (lengthTypeId.toInt() == 0) {
                    val length = packet.parse(15).toInt()
                    var subPackets = packet.get(length)

                    while (subPackets != "") {
                        val (v, s) = parsePacket(subPackets)
                        subPackets = s
                        values.add(v)
                    }
                } else {
                    var subPacketsCount = packet.parse(11)
                    var subPacketString = packet.remainingString()

                    while (subPacketsCount > 0) {
                        val (v, s) = parsePacket(subPacketString)
                        subPacketString = s
                        values.add(v)
                        subPacketsCount--
                    }
                    packet.set(subPacketString)
                }

                when (typeId) {
                    0 -> {
                        rV = values.sum()
                    }
                    1 -> {
                        rV = values.fold(1L) { a, b -> a * b }
                    }
                    2 -> {
                        rV = values.minOrNull()!!
                    }
                    3 -> {
                        rV = values.maxOrNull()!!
                    }
                    5 -> {
                        rV = if (values[0] > values[1]) 1 else 0
                    }
                    6 -> {
                        rV = if (values[0] < values[1]) 1 else 0
                    }
                    7 -> {
                        rV = if (values[0] == values[1]) 1 else 0
                    }
                    else -> {
                        println("no typeId $typeId")
                    }
                }

            } else {
                var done = false
                var data = ""
                while (!done) {
                    done = packet.parse(1).toInt() == 0
                    data += packet.get(4)
                }
                rV = data.toLong(2)
            }

            return rV!! to packet.remainingString()
        }

        return parsePacket(bits).first
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day16_test")
    val input = readInput("Day16")

    println(part1(testInput))
    check(part1(testInput) == 20)
    println(part1(input))

    println(part2(testInput))
    check(part2(testInput) == 1L)
    println(part2(input))
}

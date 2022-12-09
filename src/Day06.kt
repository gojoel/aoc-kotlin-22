fun main() {
    val input = readInput("06")
//    val input = readInput("06_test")

    fun findMarker(input: String, uniqueChars: Int) : Int {
        val stream = input.toList()
        var curr = 0

        here@ while (curr + uniqueChars < stream.size) {
            val end = curr + uniqueChars
            val sub = stream.subList(curr, end)

            val recordMap = hashMapOf<Char, Boolean>()
            var markerFound = true
            for (char in sub) {
                if (recordMap[char] != null) {
                    markerFound = false
                    break
                }

                recordMap[char] = true
            }

            if (markerFound) {
                return end
            }

            curr++
        }


        return 0
    }

    fun part1(input: List<String>) : Int {
        assert(input.size == 1)
        return findMarker(input[0], 4)
    }

    fun part2(input: List<String>) : Int {
        assert(input.size == 1)
        return findMarker(input[0], 14)
    }

    println(part1(input))
    println(part2(input))
}
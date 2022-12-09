fun main() {
    val input = readInput("04")
//    val input = readInput("04_test")

    data class Section(val lower: Int, val upper: Int)

    fun getPairs(input: List<String>) : ArrayList<Pair<Section, Section>> {
        val list: ArrayList<Pair<Section, Section>> = arrayListOf()

        input.map {line ->
            line.split(",")
                .also { sections ->
                    val result = sections.map { section->
                        val bounds = section.split("-")
                        Section(bounds[0].toInt(), bounds[1].toInt())
                    }

                    list.add(Pair(result[0], result[1]))
                }
        }

        return list
    }

    fun contained(s1: Section, s2: Section) : Boolean {
        return (s1.lower <= s2.lower && s1.upper >= s2.upper)
                || (s2.lower <= s1.lower && s2.upper >= s1.upper)
    }

    fun overlap(s1: Section, s2: Section) : Boolean {
        return !((s1.lower < s2.lower && s1.upper < s2.lower)
                || (s1.lower > s2.lower && s1.lower > s2.upper))
    }

    fun part1(input: List<String>) : Int {
        return getPairs(input).sumOf { pair ->
            if (contained(pair.first, pair.second)) {
                1
            } else {
                0
            }.toInt()
        }
    }

    fun part2(input: List<String>) : Int {
        return getPairs(input).sumOf { pair ->
            if (overlap(pair.first, pair.second)) {
                1
            } else {
                0
            }.toInt()
        }
    }

    println(part1(input))
    println(part2(input))
}
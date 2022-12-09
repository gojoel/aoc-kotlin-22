fun main() {

    val input = readInput("03")
//    val input = readInput("03_test")

    val types = ('a'..'z').toMutableList()
    types.addAll(('A'..'Z'))

    fun getPriority(char: Char?) : Int {
        return char?.let {
            types.indexOf(it) + 1 // offset index from 0
        } ?: run {
            0
        }
    }

    fun getCommonType(list: List<List<String>>) : Char? {
        val rs1 = list[0]
        val rucksacks = list.subList(1, list.size)

        for (item in rs1) {
            var contains = true
            for (rucksack in rucksacks) {
                if (!rucksack.contains(item)) {
                    contains = false
                    break
                }
            }

            if (contains) {
                return item.single()
            }
        }

        return null
    }

    fun part1(input: List<String>) : Int {
        return input.sumOf { line ->
            val items = line.split("")
                .filter { it.isNotBlank() }
            val c = getCommonType(listOf(items.take(items.size / 2), items.takeLast(items.size / 2)))
            getPriority(c)
        }
    }

    fun part2(input: List<String>) : Int {
        var fromIndex = 0
        var toIndex = 3
        var sum = 0

        while (toIndex <= input.size) {
            val subList = input.subList(fromIndex, toIndex)
            val rucksacks = subList.map { line ->
                line.split("")
                    .filter { it.isNotBlank() }
            }.toList()

            val c = getCommonType(rucksacks)
            sum += getPriority(c)

            if (toIndex == input.size) {
                break
            }

            fromIndex = toIndex
            toIndex += if (toIndex + 3 <= input.size) {
                3
            } else {
                input.size - toIndex
            }
        }

        return sum
    }

    println(part1(input))
    println(part2(input))
}
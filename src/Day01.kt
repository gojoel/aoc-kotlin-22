import kotlin.math.max

fun main() {
    val input = readInput("01")
//    val input = readInput("01_test")

    // calculate total from elf with most calories
    fun part1(input: List<String>): Int {
        var total = 0
        var current = 0

        input.forEachIndexed { index, calories ->
            current += if (calories.isBlank()) { 0 } else { calories.toInt() }
            if (calories.isBlank() || index == input.size - 1) {
                total = max(total, current)
                current = 0
            }
        }

        return total
    }

    // calculate total from top three elves
    fun part2(input: List<String>): Int {
        val totals = arrayListOf<Int>()
        var current = 0

        input.forEachIndexed { index, calories ->
            current += if (calories.isBlank()) { 0 } else { calories.toInt() }
            if (calories.isBlank() || index == input.size - 1) {
                totals.add(current)
                current = 0
            }
        }

        totals.sortDescending()
        return totals.take(3).sum()
    }

    println(part1(input))
    println(part2(input))
}


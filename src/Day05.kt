import java.rmi.UnexpectedException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

fun main() {
    val input = readInput("05")
//    val input = readInput("05_test")

    data class Step(val amount: Int, val sourceIndex: Int, val destIndex: Int)

    fun buildStack(row: String, cratesMap: HashMap<Int, ArrayDeque<Char>>)  {
        var mutableRow = row
        var position = 1
        while (mutableRow.isNotEmpty()) {
            // each crate is represented by 3 characters followed separating space
            val crate = mutableRow.take(3)[1]

            if (!crate.isWhitespace()) {
                val queue = cratesMap.getOrElse(position) { ArrayDeque() }
                queue.offer(crate)
                cratesMap[position] = queue
            }

            mutableRow = mutableRow.drop(4)
            position++
        }
    }

    fun buildSteps(step: String, list: ArrayList<Step>) {
        val stepValues = step.split(" ")
            .filter { it.toIntOrNull()?.let { true } ?: false }
            .map { it.toInt() }
            list.add(Step(stepValues[0], stepValues[1], stepValues[2]))
    }

    fun moveCrate(step: Step, crateMap: HashMap<Int, ArrayDeque<Char>>, movesMultiple: Boolean) {
        val src = crateMap.getOrElse(step.sourceIndex) { throw UnexpectedException("Crate stack should not be null") }
        val dst = crateMap.getOrElse(step.destIndex) { throw UnexpectedException("Crate stack should not be null") }

        if (movesMultiple) {
            val crates = src.take(step.amount).reversed()
            for (i in 1..step.amount) {
                dst.push(crates[i - 1])
                src.pop()
            }
        } else {
            for (i in 1..step.amount) {
                dst.push(src.pop())
            }
        }
    }

    fun organizeStacks(input: List<String>, movesMultiple: Boolean = false) : List<ArrayDeque<Char>> {
        val indexRegex = """(\s*\d)+\s*""".toRegex()

        val cratesMap: HashMap<Int, ArrayDeque<Char>> = hashMapOf()
        val steps: ArrayList<Step> = arrayListOf()

        input
            .filter { it.isNotBlank() }
            .forEach {
                if (it.matches(indexRegex)) {
                    // no-op
                } else if (it.startsWith("move")) {
                    buildSteps(it, steps)
                } else {
                    buildStack(it, cratesMap)
                }
        }

//        println(cratesMap)
        for (step in steps) {
            moveCrate(step, cratesMap, movesMultiple)
//            println(cratesMap)
        }

        return cratesMap.values.toList()
    }

    fun topCrates(stacks: List<ArrayDeque<Char>>) : String {
        var topCrates = ""
        for (crateStack in stacks) {
            topCrates = "$topCrates${crateStack.peek()}"
        }

        return topCrates
    }


    fun part1(input: List<String>) : String {
        return topCrates(organizeStacks(input))
    }

    fun part2(input: List<String>) : String {
        return topCrates(organizeStacks(input, true))
    }

    println(part1(input))
    println(part2(input))
}
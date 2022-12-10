import java.lang.Integer.max
import kotlin.math.abs
import kotlin.math.min

enum class StepDirection { U, D, L, R }

fun main() {
    val input = readInput("09")
//    val input = readInput("09_test")

    data class Move(val direction: StepDirection, val steps: Int)

    fun retrieveMoves(input: List<String>): List<Move> {
        return input.map {
            val split = it.split(" ")
            Move(StepDirection.valueOf(split[0]), split[1].toInt())
        }
    }

    fun limit(value: Int) : Int {
        return if (value < -1) {
            -1
        } else if (value > 1) {
            1
        } else {
            value
        }
    }

    fun nextMove(head: Pair<Int, Int>, tail: Pair<Int, Int>): Pair<Int, Int> {
        var xDiff = head.first - tail.first
        var yDiff = head.second - tail.second

        return if (abs(xDiff) == 1 && abs(yDiff) == 1) {
            Pair(0, 0)
        } else if (xDiff == 0 && abs(yDiff) == 1) {
            Pair(0, 0)
        } else if (yDiff == 0 && abs(xDiff) == 1) {
            Pair(0, 0)
        } else {
            xDiff = limit(xDiff)
            yDiff = limit(yDiff)

            Pair(xDiff, yDiff)
        }
    }

    fun updatePositions(list: MutableList<Pair<Int, Int>>, direction: StepDirection) {
        for (i in 1 until list.size) {
            val prev = list[i - 1]
            val curr = list[i]
            val diff = nextMove(prev, curr)

            val pos = when (direction) {
                StepDirection.U -> {
                    Pair(curr.first + diff.first, curr.second + min(diff.second, 1))
                }
                StepDirection.D -> {
                    Pair(curr.first + diff.first, curr.second + max(diff.second, -1))
                }
                StepDirection.L -> {
                    Pair(curr.first + max(diff.first, -1), curr.second + diff.second)
                }
                StepDirection.R -> {
                    Pair(curr.first + min(diff.first, 1), curr.second + diff.second)
                }
            }

            list[i] = pos
        }
    }

    fun moveKnots(input: List<String>, knotCount: Int = 2) : Int {
        val moves = retrieveMoves(input)
        val tailPositions: HashSet<Pair<Int, Int>> = hashSetOf()
        val knotsList = MutableList(knotCount) {
            Pair(0, 0)
        }

        // starting position
        tailPositions.add(Pair(0, 0))

        moves.forEach { move ->
            for (i in 1..move.steps) {
                val curr = knotsList[0]
                val head = when (move.direction) {
                    StepDirection.U -> {
                        Pair(curr.first, curr.second + 1)
                    }

                    StepDirection.D -> {
                        Pair(curr.first, curr.second - 1)
                    }

                    StepDirection.L -> {
                        Pair(curr.first - 1, curr.second)
                    }

                    StepDirection.R -> {
                        Pair(curr.first + 1, curr.second)
                    }
                }

                knotsList[0] = head
                updatePositions(knotsList, move.direction)
                tailPositions.add(knotsList[knotsList.size - 1])

            }
        }

        return tailPositions.size
    }


    fun part1(input: List<String>): Int {
        return moveKnots(input, 2)
    }

    fun part2(input: List<String>): Int {
        return moveKnots(input, 10)
    }

    println(part1(input))
    println(part2(input))
}
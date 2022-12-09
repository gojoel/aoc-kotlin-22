enum class Option {
    // *** elf options ***
    A, // rock
    B, // paper
    C, // scissors
    // *** player options ***
    X, // rock, lose
    Y, // paper, draw
    Z; // scissors, win

    fun value(): Int {
        return when (this) {
            X -> 1
            Y -> 2
            Z -> 3
            else -> 0
        }
    }
}
data class Round(val opponent: Option , val player: Option) {
    fun isWin() : Boolean {
        return player == winOption()
    }

    fun isDraw() : Boolean {
        return player == drawOption()
    }

    fun winOption(): Option {
        return when (opponent) {
            Option.A -> Option.Y
            Option.B -> Option.Z
            Option.C -> Option.X
            else -> throw IllegalArgumentException("Unexpected opponent option $opponent")
        }
    }

    fun loseOption(): Option {
        return when (opponent) {
            Option.A -> Option.Z
            Option.B -> Option.X
            Option.C -> Option.Y
            else -> throw IllegalArgumentException("Unexpected opponent option $opponent")
        }
    }

    fun drawOption(): Option {
        return when (opponent) {
            Option.A -> Option.X
            Option.B -> Option.Y
            Option.C -> Option.Z
            else -> throw IllegalArgumentException("Unexpected opponent option $opponent")
        }
    }
}

fun main() {
    val input = readInput("02")
//    val input = readInput("02_test")

    fun part1(input: List<String>) : Int {
        return input.map { line ->
            Round(
                Option.valueOf(line.substringBefore(" ")),
                Option.valueOf(line.substringAfter(" ")))
        }.sumOf { round ->
            round.player.value() + if (round.isWin()) {
                6
            } else if (round.isDraw()) {
                3
            } else {
                0
            }
        }
    }

    fun part2(input: List<String>) : Int {
        return input.map { line ->
            Round(
                Option.valueOf(line.substringBefore(" ")),
                Option.valueOf(line.substringAfter(" ")))
        }.sumOf {
            when (it.player) {
                Option.X ->
                    0 + it.loseOption().value()
                Option.Y ->
                    3 + it.drawOption().value()
                Option.Z ->
                    6 + it.winOption().value()
                else -> 0
            }
        }
    }

    println(part1(input))
    println(part2(input))
}
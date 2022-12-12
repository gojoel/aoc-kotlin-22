fun main() {
    val input = readInput("10")
//    val input = readInput("10_test")

    fun valuesAtCycle(input: List<String>, cycle: Int) : HashMap<Int, Int> {
        val values: HashMap<Int, Int> = hashMapOf()
        var currentCycle = 1
        var value = 1

        values[1] = value

        for (i in 1 .. cycle) {
            val cmd = input[i - 1]
            if (cmd.startsWith("addx")) {
                currentCycle += 2

                if (currentCycle > cycle) {
                    break
                }

                values[currentCycle - 1] = value
                value += cmd.split(" ")[1].toInt()
                values[currentCycle] = value
            } else {
                currentCycle += 1
                if (currentCycle > cycle) {
                    break
                }

                values[currentCycle] = value
            }
        }

        if (values[cycle] == null) {
            values[cycle] = value
        }

        return values
    }

    fun signalStrength(input: List<String>, cycle: Int) : Int {
        val value = valuesAtCycle(input, cycle)[cycle] ?: 0
        return value * cycle
    }

    fun part1(input: List<String>): Int {
        val cycles = arrayListOf(20, 60, 100, 140, 180, 220)

        return cycles.sumOf {
            signalStrength(input, it)
        }
    }

    fun part2(input: List<String>) {
        val cycle = 240
        val valueRegister = valuesAtCycle(input, cycle)
        var spriteX: Int

        var x = 1
        for (i in 1..cycle) {
            spriteX = valueRegister[i] ?: 0

            if (spriteX - x == 0 || x - spriteX == 1 || x - spriteX == 2) {
                print("#")
            } else {
                print(".")
            }

            x++

            if (i % 40 == 0) {
                x = 1
                println()
            }
        }
    }

    println(part1(input))
    println(part2(input))
}
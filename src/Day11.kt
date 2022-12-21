
fun main() {
    val input = readInput("11")
//    val input = readInput("11_test")

    data class Monkey(val id: Int, val items: ArrayList<Long>, var inspections: Long = 0, val op: (old: Long) -> Long,
                      val mod: Long, val test: (worry: Long) -> Boolean, val recipients: Pair<Int, Int>)

    fun operation(input: String) : (old: Long) -> Long {
        val subString = input.substringAfter("=").split(" ").takeLast(2)
        val operator = subString[0]
        val operand = subString[1]
        return { i: Long ->
            val value = if (operand == "old") { i } else { operand.toLong() }
            when (operator) {
                "+" -> i + value
                "*" -> i * value
                else -> i
            }
        }
    }

    fun parseMonkey(input: List<String>) : Monkey {
        val monkeyId = input[0].filter { char -> char.isDigit() }.toInt()
        val items = ArrayList(input[1].substringAfter(":").split(",").map { item -> item.trim().toLong() })
        val op = operation(input[2])

        val mod = input[3].split(" ").last().trim().toLong()
        val test = { i : Long -> i % mod == 0L }

        val trueRecipient = input[4].split(" ").last().trim().toInt()
        val falseRecipient = input[5].split(" ").last().trim().toInt()

        return Monkey(monkeyId, items, 0, op, mod, test, Pair(trueRecipient, falseRecipient))
    }

    fun getMonkeys(input: List<String>) : HashMap<Int, Monkey> {
        val monkeyMap: HashMap<Int, Monkey> = hashMapOf()

        for (i in 0..input.size step 7) {
            val monkey = parseMonkey(input.subList(i, i+6))
            monkeyMap[monkey.id] = monkey
        }

        return monkeyMap
    }

    fun part1(input: List<String>) : Long {
        val map = getMonkeys(input)
        for (i in 0 until 20) {
            map.keys.forEach {
                map[it]?.let { monkey ->
                    val itr = monkey.items.iterator()
                    while (itr.hasNext()) {
                        val item = itr.next()

                        var worryLevel = monkey.op(item)
                        worryLevel = (worryLevel / 3)

                        monkey.inspections++
                        if (monkey.test(worryLevel)) {
                            map[monkey.recipients.first]?.items?.add(worryLevel)
                        } else {
                            map[monkey.recipients.second]?.items?.add(worryLevel)
                        }

                        itr.remove()
                    }
                }
            }
        }

        return map.values.sortedBy { m -> m.inspections }.map { m -> m.inspections }.takeLast(2).fold(1L) { mul, item -> mul * item.toLong() }
    }

    fun part2(input: List<String>) : Long {
        val map = getMonkeys(input)
        var worryMod: Long = 1
        map.keys.forEach { key ->
            map[key]?.let {
                worryMod *= it.mod
            }
        }

        for (i in 0 until 10000) {
            map.keys.forEach {
                map[it]?.let { monkey ->
                    val itr = monkey.items.iterator()
                    while (itr.hasNext()) {
                        val item = itr.next()

                        var worryLevel = monkey.op(item)
                        worryLevel %= worryMod

                        monkey.inspections++
                        if (monkey.test(worryLevel)) {
                            map[monkey.recipients.first]?.items?.add(worryLevel)
                        } else {
                            map[monkey.recipients.second]?.items?.add(worryLevel)
                        }

                        itr.remove()
                    }
                }
            }
        }

        return map.values.sortedBy { m -> m.inspections }.map { m -> m.inspections }.takeLast(2).fold(1L) { mul, item -> mul * item.toLong() }
    }

    println(part1(input))
    println(part2(input))
}
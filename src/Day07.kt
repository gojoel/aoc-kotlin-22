import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

fun main() {
    val input = readInput("07")
//    val input = readInput("07_test")

    data class File(val name: String, val size: Int)

    fun parseDirFiles(input: List<String>) : HashMap<String, ArrayList<File>>  {
        val dirStack: Stack<String> = Stack()
        val dirMap: HashMap<String, ArrayList<File>> = hashMapOf()
        input.forEach { cmd ->
            if (cmd.startsWith("$ cd")) {
                val dir = cmd.split(" ")[2].trim()
                if (dir == "..") {
                    if (dirStack.size != 1) {
                        dirStack.pop()
                    }
                } else {
                    val key = if (dirStack.size <= 1) { dir } else { "${dirStack.peek()}/$dir" }
                    dirStack.push(key)
                }

//                println(dirStack)
            } else if (!cmd.startsWith("$") && !cmd.startsWith("dir")) {
                val fileSplit = cmd.split(" ")
                for (dir in dirStack) {
                    val dirFiles = dirMap.getOrElse(dir) { arrayListOf() }
                    dirFiles.add(File(fileSplit[1], fileSplit[0].toInt()))
                    dirMap[dir] = dirFiles
                }
            }
        }

        return dirMap
    }

    fun directorySize(dirMap: HashMap<String, ArrayList<File>>) : HashMap<String, Int> {
        val sizeMap: HashMap<String, Int> = hashMapOf()
        dirMap.keys.forEach { key ->
            dirMap[key]?.let { files ->
                val size = files.sumOf { file -> file.size }
                sizeMap[key] = size
            }
        }

        return sizeMap
    }

    fun part1(input: List<String>) : Int {
        return directorySize(parseDirFiles(input)).values.filter { size -> size <= 100000 }
            .sum()
    }

    fun part2(input: List<String>): Int {
        val usedSpace = directorySize(parseDirFiles(input))
        val freeSpace = 70000000 - (usedSpace["/"] ?: 0)
        val neededSpace = 30000000 - freeSpace
        return usedSpace.filter { entry -> entry.value >= neededSpace }
            .values.minOf { it }
    }

    println(part1(input))
    println(part2(input))
}
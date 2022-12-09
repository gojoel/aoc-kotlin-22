import kotlin.math.max

enum class Direction { UP, DOWN, LEFT, RIGHT }

// TODO: improve performance
fun main() {
    val input = readInput("08")
//    val input = readInput("08_test")

    fun buildGrid(input: List<String>) : ArrayList<IntArray> {
        val numCols = input[0].length
        val grid: ArrayList<IntArray> = arrayListOf()
        input.forEach { treeRow ->
            val arr = IntArray(numCols)
            treeRow.forEachIndexed { index, c ->
                arr[index] = Character.getNumericValue(c)
            }

            grid.add(arr)
        }

        return grid
    }

    fun previousMax(grid: ArrayList<IntArray>, row: Int, col: Int, direction: Direction) : Int {
        var max = 0
        when (direction) {
            Direction.UP -> {
                var pos = row
                while (pos >= 0) {
                    max = max(max, grid[pos][col])
                    pos--
                }
            }

            Direction.DOWN -> {
                var pos = row
                while (pos < grid.size) {
                    max = max(max, grid[pos][col])
                    pos++
                }
            }

            Direction.RIGHT -> {
                var pos = col
                while (pos < grid[row].size) {
                    max = max(max, grid[row][pos])
                    pos++
                }
            }

            Direction.LEFT -> {
                var pos = col
                while (pos >= 0) {
                    max = max(max, grid[row][pos])
                    pos--
                }
            }
        }

        return max
    }

    fun viewingDistance(tree: Int, grid: ArrayList<IntArray>, row: Int, col: Int, direction: Direction) : Int {
        var distance = 0
        when (direction) {
            Direction.UP -> {
                var pos = row
                while (pos >= 0) {
                    distance++
                    if (grid[pos][col] >= tree) {
                        break
                    }
                    pos--
                }
            }

            Direction.DOWN -> {
                var pos = row
                while (pos < grid.size) {
                    distance++
                    if (grid[pos][col] >= tree) {
                        break
                    }
                    pos++
                }
            }

            Direction.LEFT -> {
                var pos = col
                while (pos >= 0) {
                    distance++
                    if (grid[row][pos] >= tree) {
                        break
                    }
                    pos--
                }
            }


            Direction.RIGHT -> {
                var pos = col
                while (pos < grid[row].size) {
                    distance++
                    if (grid[row][pos] >= tree) {
                        break
                    }
                    pos++
                }
            }
        }

        return distance;
    }

    fun part1(input: List<String>): Int {
        val grid = buildGrid(input)
        var visible = 0

        grid.forEachIndexed { rowIdx, row ->
            row.forEachIndexed { treeIdx, tree ->
                if (rowIdx == 0 || rowIdx == grid.size - 1 || treeIdx == 0 || treeIdx == row.size - 1) {
                    // edges
                    visible++
                } else {

                    val left = previousMax(grid, rowIdx, treeIdx - 1, Direction.LEFT)
                    val right = previousMax(grid, rowIdx, treeIdx + 1, Direction.RIGHT)
                    val top = previousMax(grid, rowIdx - 1, treeIdx, Direction.UP)
                    val bottom = previousMax(grid, rowIdx + 1, treeIdx, Direction.DOWN)

                    if (left < tree || right < tree || top < tree || bottom < tree) {
                        visible++
                    }
                }
            }
        }

        return visible
    }

    fun part2(input: List<String>): Int {
        val grid = buildGrid(input)
        var scenicScore = 0

        grid.forEachIndexed { rowIdx, row ->
            row.forEachIndexed { treeIdx, tree ->
                val left = viewingDistance(tree, grid, rowIdx, treeIdx - 1, Direction.LEFT)
                val right = viewingDistance(tree, grid, rowIdx, treeIdx + 1, Direction.RIGHT)
                val top = viewingDistance(tree, grid, rowIdx - 1, treeIdx, Direction.UP)
                val bottom = viewingDistance(tree, grid, rowIdx + 1, treeIdx, Direction.DOWN)

                val score = left * right * top * bottom
                scenicScore = max(scenicScore, score)
            }
        }

        return scenicScore
    }

    println(part1(input))
    println(part2(input))
}

fun main() {

    val input = readInput("Day08")

    fun checkIfValueBiggerThanLeftAndRightValues(listOfInts: List<Int>, j: Int): Boolean {
        val height = listOfInts[j]
        val leftList = listOfInts.dropLast(listOfInts.size - j)
        val rightList = listOfInts.drop(j + 1)

        return height > leftList.max() || height > rightList.max()
    }

    fun convertToListOfNumbers(input: List<String>) =
        input.map { it.map { it.digitToInt() } }

    fun part1(input: List<String>): Int {
        val listOfLists = convertToListOfNumbers(input)

        var countOfVisibleTrees = 0
        listOfLists.forEachIndexed { i, listOfInts ->
            listOfInts.forEachIndexed { j, _ ->
                if (i == 0 || i == listOfLists.lastIndex ||
                    j == 0 || j == listOfInts.lastIndex ||
                    checkIfValueBiggerThanLeftAndRightValues(listOfInts, j) ||
                    checkIfValueBiggerThanLeftAndRightValues(listOfLists.map { it[j] }, i)
                    )
                    countOfVisibleTrees += 1
            }
        }

        return countOfVisibleTrees
    }

    fun getNumberOfVisibleTrees(height: Int, list: List<Int>): Int {
        var countOfVisibleTrees = 0

        for (i in 0..list.lastIndex) {
            val elem = list[i]
            if (elem < height) {
                countOfVisibleTrees++
            }
            if (elem >= height) {
                countOfVisibleTrees++
                break
            }
        }
        return countOfVisibleTrees
    }

    fun part2(input: List<String>): Int {
        val listOfLists = convertToListOfNumbers(input)
        val scenicScores = mutableListOf<Int>()

        listOfLists.forEachIndexed { i, listOfInts ->
            listOfInts.forEachIndexed { j, height ->
                var scenicScore: Int

                val leftList = listOfInts.dropLast(listOfInts.size - j)
                val rightList = listOfInts.drop(j + 1)

                listOfLists.map { it[j] }.let { upAndDownList ->
                    val upList = upAndDownList.dropLast(upAndDownList.size - i)
                    val downList = upAndDownList.drop(i + 1)

                    scenicScore = getNumberOfVisibleTrees(height, upList.reversed())
                    scenicScore *= getNumberOfVisibleTrees(height, downList)
                }

                scenicScore *= getNumberOfVisibleTrees(height, leftList.reversed())
                scenicScore *= getNumberOfVisibleTrees(height, rightList)

                scenicScores.add(scenicScore)
            }
        }

        return scenicScores.max()
    }

    println(part1(input))
    println(part2(input))
}

fun main() {

    val input = readInput("Day08")

    fun checkIfValueBiggerThanLeftAndRightValues(listOfInts: List<Int>, j: Int): Boolean {
        val height = listOfInts[j]
        val leftList = listOfInts.dropLast(listOfInts.size - j)
        val rightList = listOfInts.drop(j + 1)

        return height > leftList.max() || height > rightList.max()
    }

    fun part1(input: List<String>): Int {
        val listOfLists = input.map { it.split("").filter { it != "" }.map { it.toInt() } }

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

    fun part2(input: List<String>): Long = 1

    println(part1(input))
    println(part2(input))
}

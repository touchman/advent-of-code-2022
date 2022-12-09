import java.util.LinkedList

fun main() {
    val input = readInput("Day05")
    val listOfIndexes = listOf(1, 5, 9, 13, 17, 21, 25, 29, 33)

    fun fillQueues(input: List<String>): MutableList<LinkedList<Char>> {
        val queues = mutableListOf<LinkedList<Char>>()

        for (i in 0..8) {
            queues.add(LinkedList<Char>())
        }

        input.subList(0, 8).forEach { string ->
            listOfIndexes.forEachIndexed { index, indexToExtract ->
                string[indexToExtract]
                    .takeIf { it.isLetterOrDigit() }
                    ?.let {
                        queues[index].add(it)
                    }

            }
        }
        return queues
    }

    fun getQueueTopResults(queues: MutableList<LinkedList<Char>>) =
        queues.fold("") { acc, chars -> if (chars.isNotEmpty()) acc + chars.first else acc }

    fun part1(input: List<String>): String {
        val queues = fillQueues(input)
        input.subList(10, input.size)
            .forEach { action ->
                val actions = action.split(" ")
                val moveNumber = actions[1].toInt()
                val fromNumber = actions[3].toInt() - 1
                val toNumber = actions[5].toInt() - 1

                queues[toNumber]
                    .apply {
                        repeat(moveNumber) {
                            addFirst(queues[fromNumber].first)
                            queues[fromNumber].removeFirst()
                        }
                    }
            }
        return getQueueTopResults(queues)
    }

    fun part2(input: List<String>): String {
        val queues = fillQueues(input)
        input.subList(10, input.size)
            .forEach { action ->
                val actions = action.split(" ")
                val moveNumber = actions[1].toInt()
                val fromNumber = actions[3].toInt() - 1
                val toNumber = actions[5].toInt() - 1

                queues[toNumber]
                    .apply {
                        val tempList = mutableListOf<Char>()

                        repeat(moveNumber) {
                            tempList.add(queues[fromNumber].first)
                            queues[fromNumber].removeFirst()
                        }

                        tempList.reversed().forEach(this::addFirst)
                    }
            }
        return getQueueTopResults(queues)
    }

    println(part1(input))
    println(part2(input))
}
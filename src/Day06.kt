fun main() {
//    val input = readInput("Day06")
    val input = listOf("mjqjpqmgbljsphdztnvjfqwrcgsmlb")

    fun part1(input: List<String>): Int {
        val toCharArray = input[0].toCharArray()
        for((i, c) in toCharArray.withIndex()) {
            val set = mutableSetOf<Char>()
            set.addAll(toCharArray.slice(i..i+3))

            if (set.size == 4) {
                return i + 4
            }
        }

        throw Exception("marker not found")
    }

    fun part2(input: List<String>): Int {
        val set = mutableSetOf<Char>()
        val toCharArray = input[0].toCharArray()
        for (i in 0..toCharArray.lastIndex) {
            set.add(toCharArray[i])

            if (set.size == 15) {
                return i
            }
        }
        throw Exception("marker not found")
    }

    println(part1(input))
    println(part2(input))
}
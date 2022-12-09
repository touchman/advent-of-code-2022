
fun main() {
    val input = readInput("Day04")

    fun part1(input: List<String>) =
        input.map {
            it.split(",").let {
                it[0] to it[1]
            }.let {
                val firstSplit = it.first.split("-").map(String::toInt)
                val secondSplit = it.second.split("-").map(String::toInt)
                if ((firstSplit[0] <= secondSplit[0] && firstSplit[1] >= secondSplit[1]) ||
                    (secondSplit[0] <= firstSplit[0] && secondSplit[1] >= firstSplit[1])) {
                    1
                } else {
                    0
                }
            }
        }.reduce { acc, i -> acc + i}

    fun part2(input: List<String>) =
        input.map {
            it.split(",").let {
                it[0] to it[1]
            }.let {
                val firstSplit = it.first.split("-").map(String::toInt)
                val secondSplit = it.second.split("-").map(String::toInt)
                var firstElem = firstSplit[0]
                var secondElem = secondSplit[0]

                val firstSeq = generateSequence { (firstElem++).takeIf { it <= firstSplit[1] } }.toSet()
                val secondSeq = generateSequence { (secondElem++).takeIf { it <= secondSplit[1] } }.toSet()

                if (firstSeq.intersect(secondSeq).isNotEmpty() ||
                    secondSeq.intersect(firstSeq).isNotEmpty()) {
                    1
                } else {
                    0
                }
            }
        }.reduce { acc, i -> acc + i}

    println(part1(input))
    println(part2(input))
}
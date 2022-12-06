import java.lang.Exception

fun main() {
    val input = readInput("Day03")

    fun getNumberForChar(it: String) = it.toCharArray()[0]
        .let {
            if (it.isLowerCase()) {
                it - 'a' + 1
            } else {
                it - 'A' + 1 + 26
            }
        }


    fun part1(input: List<String>) =
        input.map {
            it.chunked(it.length/2)
                .let {
                    it[0].split("").intersect(it[1].split(""))
                }.filterNot { it == "" }
                .map {
                    getNumberForChar(it)
                }[0]
        }.reduce { acc, i -> acc + i }

    fun part2(input: List<String>) =
        input.chunked(3)
            .let {
                it.map { group ->
                    val firstAndSecondIntersect = group[0].toSet()
                        .intersect(
                            group[1].toSet()
                        )

                    val secondAndThirdIntersect = group[1].toSet()
                        .intersect(
                            group[2].toSet()
                        )
                    firstAndSecondIntersect.intersect(secondAndThirdIntersect)
                }.map {
                    String(it.toCharArray())
                }.filterNot { it == "" }
                .let {
                    it.map {
                        getNumberForChar(it)
                    }.reduce { acc, i -> acc + i }
                }
            }

    println(part1(input))
    println(part2(input))
}
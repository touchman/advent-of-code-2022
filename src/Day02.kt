import java.lang.Exception

fun main() {
    val input = readInput("Day02")

    fun part1(input: List<String>) =
        input.map {
            it.split(" ")
                .let {
                    parseItem(it[0]) to parseItem(it[1])
                }.let { pair ->
                    getResult(pair.second, pair.first) + pair.second.value
                }
        }.reduce { acc: Int, i: Int -> acc + i }

    fun part2(input: List<String>) =
        input.map {
            it.split(" ")
                .let {
                    parseItem(it[0]) to getItemForAction(parseItem(it[0]), it[1])
                }.let { pair ->
                    getResult(pair.second, pair.first) + pair.second.value
                }
        }.reduce { acc: Int, i: Int -> acc + i }

    println(part1(input))
    println(part2(input))
}


enum class Item(val value: Int) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3),
}

fun parseItem(item: String): Item =
    when (item) {
        in listOf("A", "X") -> Item.ROCK
        in listOf("B", "Y") -> Item.PAPER
        in listOf("C", "Z") -> Item.SCISSORS
        else -> throw Exception("Unknown items: $item")
    }

fun getResult(item1: Item, item2: Item): Int =
    when {
        item1 == item2 -> 3
        item1 == Item.ROCK && item2 == Item.PAPER-> 0
        item1 == Item.ROCK && item2 == Item.SCISSORS-> 6
        item1 == Item.PAPER && item2 == Item.SCISSORS-> 0
        item1 == Item.PAPER && item2 == Item.ROCK-> 6
        item1 == Item.SCISSORS && item2 == Item.ROCK-> 0
        item1 == Item.SCISSORS && item2 == Item.PAPER-> 6
        else -> throw Exception("Unknown items: $item1 and $item2")
    }

fun getItemForAction(oppItem: Item, action: String) =
    when (action) {
        "X" -> {
            when (oppItem) {
                Item.ROCK -> Item.SCISSORS
                Item.PAPER -> Item.ROCK
                Item.SCISSORS -> Item.PAPER
            }
        }
        "Y" -> oppItem
        "Z" ->
            when (oppItem) {
                Item.ROCK -> Item.PAPER
                Item.PAPER -> Item.SCISSORS
                Item.SCISSORS -> Item.ROCK
            }
        else -> throw Exception("Unknown action: $action")
    }
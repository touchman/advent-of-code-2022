fun main() {
    fun part1(input: List<String>): Int {
        val elfCalories = getElfsCalories(input)

        return elfCalories.max()
    }

    fun part2(input: List<String>): Int {
        val elfCalories = getElfsCalories(input)

        return elfCalories
            .apply {
                this.sortDescending()
            }.reduceIndexed { inx,  acc, i -> if (inx in 0..2) {
                    acc + i
                } else {
                    acc
                }
        }
    }

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}


fun getElfsCalories(input: List<String>): MutableList<Int> {
    val elfCalories = mutableListOf<Int>()

    var caloriesBuffer = 0
    for (s in input) {
        if (s == "") {
            elfCalories.add(caloriesBuffer)
            caloriesBuffer = 0
        } else {
            caloriesBuffer += s.toInt()
        }
    }
    return elfCalories
}
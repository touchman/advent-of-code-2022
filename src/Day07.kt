fun main() {
    val input = readInput("Day07")

    val hierarchy: Folder = createHierarchy(input)

    fun part1(input: List<String>): Int {
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    println(part1(input))
    println(part2(input))
}

fun createHierarchy(input: List<String>): Folder {
    val hierarchy = Folder("root", mutableListOf())
    var currentPointer: Folder? = null

    input.forEach { line ->
        when {
            line.contains("$ cd /") -> {
                currentPointer = hierarchy
            }
            line.contains("$") && line.contains("cd") -> {
                currentPointer = if (line.contains("..")) {
                    currentPointer?.parent
                } else {
                    currentPointer?.children?.first { it.name == line.split(" ")[2] }
                }
            }
            line == "$ ls" -> {}
            else -> {
                if (line.contains("dir")) {
                    createFolder(line, currentPointer)
                } else {
                    createFileInFolder(line, currentPointer)
                }
            }
        }
    }

    return hierarchy
}

fun createFileInFolder(line: String, currentPointer: Folder?) {
    line.split(" ").let {
        currentPointer?.files?.add(File(it[1], it[0].toLong()))
    }

}

fun createFolder(line: String, currentPointer: Folder?): Folder {
    val folder = Folder(line.substringAfter(" "), mutableListOf()).apply {
        parent = currentPointer
    }
    currentPointer?.children?.add(folder)
    return folder
}



data class Folder(
    val name: String,
    val files: MutableList<File>
) {
    var parent: Folder? = null
    val children: MutableList<Folder> = mutableListOf()
}

data class File(
    val name: String,
    val size: Long,
)
fun main() {
    val input = readInput("Day07")
    val hierarchy: Folder = createHierarchy(input)

    fun findAllFoldersWithFileSize(folderNameToSize: MutableMap<String, Long>, pointer: Folder): Pair<String, Long> {
        var currentName = pointer.parent?.name + "/" + pointer.name

        pointer.children.forEach {
            val (name, size) = findAllFoldersWithFileSize(folderNameToSize, it)
            folderNameToSize[name] = size
            currentName = pointer.parent?.name + "/" + name
        }

        return currentName to pointer.files.sumOf { it.size }
    }
/*
"a/e" -> {Long@906} 584
"root/a/e" -> {Long@908} 94269
"root/d" -> {Long@910} 24933642
 */
    fun part1(hierarchy: Folder): Long {
        val allFolders = mutableMapOf<String, Long>()
        findAllFoldersWithFileSize(allFolders, hierarchy)

//        return allFolders.filter { it.value <= 100_000 }
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    println(part1(hierarchy))
    println(part2(input))
}

fun createHierarchy(input: List<String>): Folder {
    val hierarchy = Folder("root", mutableListOf())
    var currentPointer: Folder? = hierarchy

    for (i in 1..input.lastIndex) {
        val line = input[i]
        when {
            line.contains("$ cd") -> {
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
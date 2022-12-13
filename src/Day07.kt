fun main() {
    val input = readInput("Day07")
    val hierarchy: Folder = createHierarchy(input)
    val allFolders = mutableMapOf<String, Long>()
    findAllFoldersWithFileSize(allFolders, hierarchy)
    addSizeOfSubFolders(allFolders)

    fun part1(allFolders: MutableMap<String, Long>): Long =
        allFolders.values.filter { it <= 100_000 }.sum()

    fun part2(allFolders: MutableMap<String, Long>): Long {
        val unusedSpace = 70_000_000 - allFolders["/root"]!!
        val requiredMemoryForDelete = 30_000_000 - unusedSpace

        return allFolders.values.sortedDescending()
            .let { sorted ->
                val indexOfTheClosest = sorted.mapIndexed { index, l -> index to l - requiredMemoryForDelete }
                    .filter { it.second > 0 }
                    .minByOrNull { pair -> pair.second }!!.first

                sorted[indexOfTheClosest]
            }

    }

    println(part1(allFolders))
    println(part2(allFolders))
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

fun findAllFoldersWithFileSize(folderNameToSize: MutableMap<String, Long>, pointer: Folder, incrementingName: String = ""): Pair<String, Long> {
    val currentName = incrementingName + "/" + pointer.name

    pointer.children.forEach {
        val (name, size) = findAllFoldersWithFileSize(folderNameToSize, it, currentName)
        folderNameToSize[name] = size
    }

    if (currentName == "/root") {
        folderNameToSize[currentName] = pointer.files.sumOf { it.size }
    }

    return currentName to pointer.files.sumOf { it.size }
}

fun addSizeOfSubFolders(allFolders: MutableMap<String, Long>) {
    allFolders.forEach { (t, u) ->
        val sizeOfAllSubFolders = allFolders.keys
            .filter { it.contains(t) && t != it && it.substringAfter(t).split("/").size == 2 }
            .map { allFolders[it]!! }
            .takeIf { it.isNotEmpty() }
            ?.reduce { acc, l -> acc + l }

        sizeOfAllSubFolders?.let {
            if (sizeOfAllSubFolders != 0L) {
                allFolders[t] = u + sizeOfAllSubFolders
            }
        }
    }
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
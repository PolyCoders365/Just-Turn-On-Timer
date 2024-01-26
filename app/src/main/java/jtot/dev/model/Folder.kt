package jtot.dev.model

import jtot.dev.utils.addFirst
import jtot.dev.utils.getToday

data class Folder(
    var title: String = "",
    var docs: List<Any> = listOf(),
) {
    fun createDummy() =
        this.copy(
            title = getToday(),
            docs =
                listOf(
                    Folder(
                        title = "work",
                        docs = listOf(Todo().createDummy(), Schedule().createDummy()),
                    ),
                    Todo().createDummy(),
                    Todo().createStarDummy(),
                    Schedule().createDummy(),
                ),
        )

    fun createDummyFolders(title: String) =
        this.copy(
            title = title,
            docs =
                listOf(
                    Folder(
                        title = "work",
                        docs =
                            listOf(
                                Todo(title = "asd"),
                            ),
                    ),
                    Folder(
                        title = "star",
                        docs =
                            listOf(
                                Folder(title = "tfg", docs = listOf()),
                                Schedule(title = "2024-01-01"),
                            ),
                    ),
                ),
        )

    fun createFolder(targetFolder: Folder) {
        if (this.title == targetFolder.title && this.docs == targetFolder.docs) {
            // Found the target folder, change the current folder
            this.docs.addFirst(Folder(title = "새로운 폴더", docs = listOf()))
        }

        for (doc in this.docs) {
            if (doc is Folder) {
                val foundFolder = createFolder(targetFolder)
                if (foundFolder != null) {
                    // Found the target folder in the nested folder
                    this.docs.addFirst(Folder(title = "새로운 폴더", docs = listOf()))
                }
            }
        }
    }

    fun findFolder(targetFolder: Folder): Folder? {
        if (this.title == targetFolder.title && this.docs == targetFolder.docs) {
            // Found the target folder, change the current folder
            return this
        }

        // Search recursively in nested folders
        for (doc in this.docs) {
            if (doc is Folder) {
                val foundFolder = doc.findFolder(targetFolder)
                if (foundFolder != null) {
                    // Found the target folder in the nested folder
                    return foundFolder
                }
            }
        }

        // Target folder not found in the current folder or its nested folders
        return null
    }

    fun findTodo(targetTodoTitle: String): List<Todo>? {
        val todosInFolder = mutableListOf<Todo>()

        // Check if the current folder has Todos
        if (this.docs.any { it is Todo && it.title.contains(targetTodoTitle) }) {
            todosInFolder.addAll(this.docs.filterIsInstance<Todo>().filter { it.title.contains(targetTodoTitle) })
        }

        // Check nested folders
        this.docs.filterIsInstance<Folder>().forEach { nestedFolder ->
            findTodo(targetTodoTitle)?.let { todosInFolder.addAll(it) }
        }

        return todosInFolder
    }

    fun removeFolder(targetFolder: Folder): Folder? {
        if (this.title == targetFolder.title && this.docs == targetFolder.docs) {
            return this
        }

        for (doc in this.docs) {
            if (doc is Folder) {
                val foundFolder = doc.removeFolder(targetFolder)
                if (foundFolder != null) {
                    val removedDocs = this.docs.toMutableList()
                    removedDocs.remove(foundFolder)
                    this.docs = removedDocs
                    return foundFolder
                }
            }
        }
        return null
    }
}

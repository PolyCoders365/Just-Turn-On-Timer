package jtot.dev.model

import jtot.dev.utils.getToday

data class Folder(
    val title: String = "",
    val docs: List<Any> = listOf(),
) {
    fun createDummy() =
        this.copy(
            title = getToday(),
            docs =
                listOf(
                    Folder().copy(title = "work", docs = listOf(Todo().createDummy(), Schedule().createDummy())),
                    Todo().createDummy(),
                    Todo().createStarDummy(),
                    Schedule().createDummy(),
                ),
        )
}

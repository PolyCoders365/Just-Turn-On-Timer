package jtot.dev.model

data class Folder(
    val title: String = "",
    val docs: List<Any> = listOf(),
) {
    fun createDummy() =
        this.copy(
            title = "work",
            docs =
                listOf(
                    Todo().createDummy(),
                    Todo().createStarDummy(),
                    Schedule().createDummy(),
                ),
        )
}

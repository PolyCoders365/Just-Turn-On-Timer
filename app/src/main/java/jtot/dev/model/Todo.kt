package jtot.dev.model

import jtot.dev.utils.createStringDummy

data class Todo(
    val tag: String = "",
    val date: String = "",
    val startTime: String = "",
    val endTime: String = "",
    val breakTime: String = "",
    val todos: List<Any> = listOf(),
    override val title: String = "",
    override val content: String = "",
) : Document() {
    fun createDummy() =
        this.copy(
            title = "work",
            content = createStringDummy(),
            tag = "work",
            date = "2024-01-01",
            startTime = "12:00",
            endTime = "14:00",
            breakTime = "90",
            todos = listOf(),
        )

    fun createStarDummy() =
        this.copy(
            title = "starWork",
            content = createStringDummy(),
            tag = "work",
            date = "2024-01-01",
            startTime = "14:00",
            endTime = "15:00",
            breakTime = "30",
            todos = listOf(createStringDummy(), Todo().createDummy()),
        )
}

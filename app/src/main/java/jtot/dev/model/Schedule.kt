package jtot.dev.model

import jtot.dev.utils.createStringDummy

data class Schedule(
    val todos: List<Any> = listOf(),
    override val title: String = "",
    override val content: String = "",
) : Document() {
    fun createDummy(): Schedule {
        return this.copy(
            title = "2024-01-01",
            content = createStringDummy(),
            todos = listOf(Todo().createDummy()),
        )
    }
}

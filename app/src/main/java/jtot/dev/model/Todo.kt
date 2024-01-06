package jtot.dev.model

data class Todo(
    val star: Boolean,
    val tag: String,
    val date: String,
    val startTime: String,
    val endTime: String,
    val breakTime: String,
    val todos: List<Todo>,
) : Document()

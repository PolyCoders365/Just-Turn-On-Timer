package jtot.dev.model

data class Todo(
    val title: String = "",
    val date: String = "",
    val startTime: String = "",
    val endTime: String = "",
    val breakTime: String = "",
    val contents: List<Any> = listOf(),
) {
//    fun createDummy() =
//        this.copy(
//            title = "work",
//            date = "2024-01-01",
//            startTime = "12:00",
//            endTime = "14:00",
//            breakTime = "90",
//            contents = listOf(),
//        )

    fun createDummy(depth: Int = 3): Todo {
        return if (depth > 0) {
            this.copy(
                title = "work",
                date = "2024-01-01",
                startTime = "12:00",
                endTime = "14:00",
                breakTime = "90",
                contents = listOf(Todo().createDummy(depth - 1)),
            )
        } else {
            this.copy(
                title = "work",
                date = "2024-01-01",
                startTime = "12:00",
                endTime = "14:00",
                breakTime = "90",
                contents = emptyList(),
            )
        }
    }

//    fun createStarDummy() =
//        this.copy(
//            title = "starWork",
//            date = "2024-01-01",
//            startTime = "14:00",
//            endTime = "15:00",
//            breakTime = "30",
//            contents = listOf(Schedule().createDummy(), Todo().createDummy()),
//        )

    fun createStarDummy(depth: Int = 3): Todo {
        return if (depth > 0) {
            this.copy(
                title = "starWork",
                date = "2024-01-01",
                startTime = "14:00",
                endTime = "15:00",
                breakTime = "30",
                contents = listOf(Schedule().createDummy(depth - 1), Todo().createDummy(depth - 1)),
            )
        } else {
            this.copy(
                title = "starWork",
                date = "2024-01-01",
                startTime = "14:00",
                endTime = "15:00",
                breakTime = "30",
                contents = emptyList(),
            )
        }
    }

    fun toMap(): Map<String, Any> {
        return mapOf(
            "title" to title,
            "date" to date,
            "startTime" to startTime,
            "endTime" to endTime,
            "breakTime" to breakTime,
            "contents" to contents,
        )
    }
}

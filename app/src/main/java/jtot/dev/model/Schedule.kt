package jtot.dev.model

import jtot.dev.utils.createStringDummy
import java.io.Serializable

data class Schedule(
    val title: String = "",
    val contents: List<Any> = listOf(),
) : Serializable {
//    fun createDummy(): Schedule {
//        return this.copy(
//            title = "2024-01-01",
//            contents = listOf(createStringDummy(), Todo().createDummy()),
//        )
//    }

    fun createDummy(depth: Int = 3): Schedule {
        return if (depth > 0) {
            this.copy(
                title = "2024-01-01",
                contents = listOf(createStringDummy(), Todo().createDummy(depth - 1)),
            )
        } else {
            this.copy(
                title = "2024-01-01",
                contents = emptyList(),
            )
        }
    }

    fun toMap(): Map<String, Any> {
        return mapOf(
            "title" to title,
            "contents" to contents,
        )
    }
}

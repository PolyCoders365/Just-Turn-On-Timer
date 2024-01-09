package jtot.dev.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Folder(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val docs: List<Document>,
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

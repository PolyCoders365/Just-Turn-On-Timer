package jtot.dev.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import jtot.dev.model.Folder

@Dao
interface FolderDao {
    @Insert
    suspend fun insert(folder: Folder)

    @Query("SELECT * FROM Folder")
    suspend fun getAllFolders(): List<Folder>
}

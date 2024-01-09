package jtot.dev.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import jtot.dev.model.Folder

@Database(entities = [Folder::class], version = 1)
abstract class FolderDataDB : RoomDatabase() {
    abstract fun folderDao(): FolderDao

    companion object {
        @Volatile
        private var INSTANCE: FolderDataDB? = null

        fun getDatabase(context: Context): FolderDataDB {
            return INSTANCE ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        FolderDataDB::class.java,
                        "folder_database",
                    ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

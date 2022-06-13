package pl.edu.pja.mp1.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

private const val DATABASE_FILENAME = "tasks"

@Database(entities = [TaskDTO::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract val tasks: TaskDAO

    companion object {
        fun open(context: Context): AppDatabase =
            Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DATABASE_FILENAME
            ).build()
    }
}
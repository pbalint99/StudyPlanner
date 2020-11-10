package hu.bme.aut.android.studyplanner.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    exportSchema = false,
    entities = [RoomTask::class]
)
abstract class TaskDatabase: RoomDatabase() {

    abstract fun taskDao(): TaskDao

}
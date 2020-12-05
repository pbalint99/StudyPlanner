package hu.bme.aut.android.studyplanner.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    exportSchema = false,
    entities = [RoomSubject::class]
)
abstract class SubjectDatabase: RoomDatabase() {

    abstract fun subjectDao(): SubjectDao

}
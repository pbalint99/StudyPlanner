package hu.bme.aut.android.studyplanner

import android.app.Application
import androidx.room.Room
import hu.bme.aut.android.studyplanner.database.TaskDatabase

class TaskApplication : Application() {

    companion object {
        lateinit var taskDatabase: TaskDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()

        taskDatabase = Room.databaseBuilder(
            applicationContext,
            TaskDatabase::class.java,
            "task_database"
        ).build()
    }

}
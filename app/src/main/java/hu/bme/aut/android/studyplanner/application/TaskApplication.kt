package hu.bme.aut.android.studyplanner.application

import android.app.Application
import androidx.room.Room
import hu.bme.aut.android.studyplanner.database.SubjectDatabase
import hu.bme.aut.android.studyplanner.database.TaskDatabase


class TaskApplication : Application() {

    companion object {
        lateinit var taskDatabase: TaskDatabase
            private set
        lateinit var subjectDatabase: SubjectDatabase
            private set
    }

//    private val mNotificationTime = Calendar.getInstance().timeInMillis + 5000 //Set after 5 seconds from the current time.
//    private var mNotified = false

    override fun onCreate() {
        super.onCreate()

        taskDatabase = Room.databaseBuilder(
            applicationContext,
            TaskDatabase::class.java,
            "task_database"
        ).build()

        subjectDatabase = Room.databaseBuilder(
            applicationContext,
            SubjectDatabase::class.java,
            "subject_database"
        ).build()

//        if (!mNotified) {
//            NotificationUtils().setNotification(mNotificationTime,this)
//        }
        val pref = applicationContext.getSharedPreferences("MyPref", 0) // 0 - for private mode
        pref.getInt("firstDay",0)
    }

}
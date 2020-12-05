package hu.bme.aut.android.studyplanner.application

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.room.Room
import hu.bme.aut.android.studyplanner.R
import hu.bme.aut.android.studyplanner.database.SubjectDatabase
import hu.bme.aut.android.studyplanner.database.TaskDatabase

class TaskApplication : Application() {

    companion object {
        lateinit var taskDatabase: TaskDatabase
            private set
        lateinit var subjectDatabase: SubjectDatabase
            private set
    }

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

        createNotificationChannel()

        //TODO: remove
        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(1, builder.build())
        }
    }

    var builder = NotificationCompat.Builder(this, "Task")
        .setSmallIcon(R.drawable.close)
        .setContentTitle("Task coming up")
        .setContentText("Don't forget!")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_desc)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("Task", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}
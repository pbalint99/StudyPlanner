package hu.bme.aut.android.studyplanner.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class RoomTask(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val type: Int = 0,
    val subject: String = "",
    val week: Int = 1,
    val day: Int = 1,
    val date: Int = 1
)
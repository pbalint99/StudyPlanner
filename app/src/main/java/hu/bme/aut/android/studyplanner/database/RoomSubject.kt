package hu.bme.aut.android.studyplanner.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subject")
data class RoomSubject(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String = ""
)
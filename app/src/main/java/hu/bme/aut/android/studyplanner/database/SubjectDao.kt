package hu.bme.aut.android.studyplanner.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SubjectDao {
    @Insert
    fun insertSubject(subject: RoomSubject)

    @Query("SELECT * FROM subject")
    fun getAllSubjects(): LiveData<List<RoomSubject>>

    @Query("SELECT * FROM subject WHERE id == :id")
    fun getSubjectById(id: Long?): RoomSubject?

    @Update
    fun updateSubject(subject: RoomSubject): Int

    @Delete
    fun deleteSubject(subject: RoomSubject)
}
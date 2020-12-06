package hu.bme.aut.android.studyplanner.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {
    @Insert
    fun insertTask(task: RoomTask)

    @Query("SELECT * FROM task ORDER BY week, day")
    fun getAllTasks(): LiveData<List<RoomTask>>

    @Query("SELECT * FROM task ORDER BY subject")
    fun getAllTasksBySubject(): LiveData<List<RoomTask>>

    @Query("SELECT * FROM task WHERE id == :id")
    fun getTaskById(id: Long?): RoomTask?

    @Update
    fun updateTask(task: RoomTask): Int

    @Delete
    fun deleteTask(task: RoomTask)

    @Query("DELETE FROM task")
    fun deleteAllTasks()
}
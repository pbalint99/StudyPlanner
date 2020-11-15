package hu.bme.aut.android.studyplanner.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {
    @Insert
    fun insertTask(task: RoomTask)

    @Query("SELECT * FROM task ORDER BY week")
    fun getAllTasks(): LiveData<List<RoomTask>>

    @Query("SELECT * FROM task WHERE id == :id")
    fun getTaskById(id: Long?): RoomTask?

    @Update
    fun updateTask(task: RoomTask): Int

    @Delete
    fun deleteTask(task: RoomTask)
}
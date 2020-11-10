package hu.bme.aut.android.studyplanner.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {
    @Insert
    fun insertTask(task: RoomTask)

    @Query("SELECT * FROM task")
    fun getAllTasks(): LiveData<List<RoomTask>>

    @Update
    fun updateTask(task: RoomTask): Int

    @Delete
    fun deleteTask(task: RoomTask)
}
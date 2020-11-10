package hu.bme.aut.android.studyplanner.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import hu.bme.aut.android.studyplanner.database.RoomTask
import hu.bme.aut.android.studyplanner.database.TaskDao
import hu.bme.aut.android.studyplanner.model.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(private val taskDao: TaskDao) {

    fun getAllTasks(): LiveData<List<Task>> {
        return taskDao.getAllTasks()
            .map {roomTasks ->
                roomTasks.map {roomTask ->
                    roomTask.toDomainModel() }
            }
    }

    suspend fun insert(task: Task) = withContext(Dispatchers.IO) {
        taskDao.insertTask(task.toRoomModel())
    }

    private fun RoomTask.toDomainModel(): Task {
        return Task(
            title = title
        )
    }

    private fun Task.toRoomModel(): RoomTask {
        return RoomTask(
            title = title
        )
    }
}
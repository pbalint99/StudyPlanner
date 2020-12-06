package hu.bme.aut.android.studyplanner.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.bme.aut.android.studyplanner.application.TaskApplication
import hu.bme.aut.android.studyplanner.database.TaskDao
import hu.bme.aut.android.studyplanner.model.Task
import hu.bme.aut.android.studyplanner.repository.Repository
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {

    private var repository: Repository

    var allTasks: LiveData<List<Task>>
    var taskDao: TaskDao = TaskApplication.taskDatabase.taskDao()
    var orderBySubject: Boolean = false

    init {
        repository = Repository(taskDao)
        allTasks = repository.getAllTasks()
    }

    fun insert(task: Task) = viewModelScope.launch {
        repository.insert(task)
    }

    fun delete(task: Task) = viewModelScope.launch {
        repository.delete(task)
    }

    fun deleteAt(position: Int) = viewModelScope.launch {
        val tasks=allTasks.value
        if(tasks!=null) {
            repository.delete(tasks[position])
        }
    }

    fun deleteAll()= viewModelScope.launch {
        repository.deleteAll()
    }

    fun getAllTasksBySubject()= viewModelScope.launch {
        allTasks=repository.getAllTasksBySubject()
    }

}
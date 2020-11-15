package hu.bme.aut.android.studyplanner.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.bme.aut.android.studyplanner.TaskApplication
import hu.bme.aut.android.studyplanner.model.Task
import hu.bme.aut.android.studyplanner.repository.Repository
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {

    private val repository: Repository

    val allTasks: LiveData<List<Task>>

    init {
        val taskDao = TaskApplication.taskDatabase.taskDao()
        repository = Repository(taskDao)
        allTasks = repository.getAllTasks()
    }

    fun insert(task: Task) = viewModelScope.launch {
        repository.insert(task)
    }

    fun delete(task: Task) = viewModelScope.launch {
        repository.delete(task)
    }
}
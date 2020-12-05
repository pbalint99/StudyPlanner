package hu.bme.aut.android.studyplanner.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.bme.aut.android.studyplanner.application.TaskApplication
import hu.bme.aut.android.studyplanner.model.Subject
import hu.bme.aut.android.studyplanner.repository.RepositoryS
import kotlinx.coroutines.launch

class SubjectViewModel : ViewModel() {

    private val repository: RepositoryS

    val allSubjects: LiveData<List<Subject>>

    init {
        val subjectDao = TaskApplication.subjectDatabase.subjectDao()
        repository = RepositoryS(subjectDao)
        allSubjects = repository.getAllSubjects()
    }

    fun insert(subject: Subject) = viewModelScope.launch {
        repository.insert(subject)
    }

    fun delete(subject: Subject) = viewModelScope.launch {
        repository.delete(subject)
    }

    fun deleteAt(position: Int) = viewModelScope.launch {
        val subjects=allSubjects.value
        if(subjects!=null) {
            repository.delete(subjects[position])
        }
    }
}
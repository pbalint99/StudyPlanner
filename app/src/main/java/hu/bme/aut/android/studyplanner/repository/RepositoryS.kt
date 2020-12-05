package hu.bme.aut.android.studyplanner.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import hu.bme.aut.android.studyplanner.database.RoomSubject
import hu.bme.aut.android.studyplanner.database.SubjectDao
import hu.bme.aut.android.studyplanner.model.Subject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepositoryS(private val subjectDao: SubjectDao) {

    fun getAllSubjects(): LiveData<List<Subject>> {
        return subjectDao.getAllSubjects()
            .map {roomSubjects ->
                roomSubjects.map {roomSubject ->
                    roomSubject.toDomainModel() }
            }
    }

    suspend fun insert(subject: Subject) = withContext(Dispatchers.IO) {
        subjectDao.insertSubject(subject.toRoomModel())
    }

    suspend fun delete(subject: Subject) = withContext(Dispatchers.IO) {
        val roomSubject = subjectDao.getSubjectById(subject.id) ?: return@withContext
        subjectDao.deleteSubject(roomSubject)
    }

    private fun RoomSubject.toDomainModel(): Subject {
        return Subject(
            id = id,
            title = title
        )
    }

    private fun Subject.toRoomModel(): RoomSubject {
        return RoomSubject(
            title = title
        )
    }
}
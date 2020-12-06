package hu.bme.aut.android.studyplanner.model

import javax.security.auth.Subject

class Task(
    val id: Long? = null,
    val title: String = "",
    var week: Int = 1,
    val day: Int = 1,
    val type: Int = 0,
    val subject: String = "",
    val date: Long = 1,
    val percent: Double = 0.0
) {

}
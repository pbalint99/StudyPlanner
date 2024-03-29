package hu.bme.aut.android.studyplanner.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.studyplanner.R
import hu.bme.aut.android.studyplanner.model.Subject
import kotlinx.android.synthetic.main.fragment_create_subject.*

class SubjectCreateFragment: DialogFragment() {
    private lateinit var listener: SubjectCreatedListener

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            listener = if (targetFragment != null) {
                targetFragment as SubjectCreatedListener
            } else {
                activity as SubjectCreatedListener
            }
        } catch (e: ClassCastException) {
            throw RuntimeException(e)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_subject, container, false)
        dialog?.setTitle(getString(R.string.create_subject_fragment_title))


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnCreateSubject.setOnClickListener{
            listener.onSubjectCreated(
                Subject(
                    title = subjectName.text.toString()
                )
            )

            dismiss()
        }
    }

    interface SubjectCreatedListener {
        fun onSubjectCreated(subject: Subject)
    }
}
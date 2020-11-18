package hu.bme.aut.android.studyplanner

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.studyplanner.model.Task
import kotlinx.android.synthetic.main.fragment_create.*

class TaskEditFragment: DialogFragment() {
    private lateinit var listener: TaskEditFragment.TaskEditedListener

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            listener = if (targetFragment != null) {
                targetFragment as TaskEditFragment.TaskEditedListener
            } else {
                activity as TaskEditFragment.TaskEditedListener
            }
        } catch (e: ClassCastException) {
            throw RuntimeException(e)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_create, container, false)
        dialog?.setTitle(getString(R.string.edit))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        npWeek.maxValue = 15;
        npWeek.minValue = 1;
        btnCreate.setOnClickListener{
            var type: Int = if(radioGroup.checkedRadioButtonId == radioHW.id) 0
            else 1

            listener.onTaskEdited(
                Task(
                title = etTitle.text.toString(),
                week = npWeek.value,
                type = type,
                subject = etSubject.text.toString()
            )
            )

            dismiss()
        }
    }
    interface TaskEditedListener {
        fun onTaskEdited(task: Task)
    }
}
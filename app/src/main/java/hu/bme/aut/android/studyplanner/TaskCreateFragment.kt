package hu.bme.aut.android.studyplanner

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.studyplanner.model.Task
import kotlinx.android.synthetic.main.fragment_create.*

class TaskCreateFragment: DialogFragment() {
    private lateinit var listener: TaskCreatedListener

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            listener = if (targetFragment != null) {
                targetFragment as TaskCreatedListener
            } else {
                activity as TaskCreatedListener
            }
        } catch (e: ClassCastException) {
            throw RuntimeException(e)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_create, container, false)
        dialog?.setTitle(getString(R.string.create))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        npWeek.setMaxValue(15);
        npWeek.setMinValue(1);
        btnCreate.setOnClickListener{
            listener.onTaskCreated(Task(
                title = etTitle.text.toString(),
                week = npWeek.value,
                type = etType.text.toString(),
                subject = etSubject.text.toString()
            ))

            dismiss()
        }
    }

    interface TaskCreatedListener {
        fun onTaskCreated(task: Task)
    }
}
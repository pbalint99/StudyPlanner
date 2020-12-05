package hu.bme.aut.android.studyplanner

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.studyplanner.model.Task
import kotlinx.android.synthetic.main.fragment_create.*


class TaskCreateFragment: DialogFragment(), AdapterView.OnItemSelectedListener {
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create, container, false)
        dialog?.setTitle(getString(hu.bme.aut.android.studyplanner.R.string.createFrag))

        val spinner = view.findViewById<Spinner>(R.id.spinner)
        val days = resources.getStringArray(R.array.Days)
        spinner.onItemSelectedListener = this
        val arrayAdapter = ArrayAdapter(activity!!.applicationContext, android.R.layout.simple_spinner_item, days)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        npWeek.maxValue = 15;
        npWeek.minValue = 1;

        btnCreate.setOnClickListener{
            var type: Int = if(radioGroup.checkedRadioButtonId == radioHW.id) 0
            else 1

            listener.onTaskCreated(
                Task(
                    title = etTitle.text.toString(),
                    week = npWeek.value,
                    type = type,
                    subject = etSubject.text.toString(),
                    day = spinner.selectedItemPosition
                )
            )

            dismiss()
        }
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {

    }

    override fun onNothingSelected(arg0: AdapterView<*>) {

    }

    interface TaskCreatedListener {
        fun onTaskCreated(task: Task)
    }
}
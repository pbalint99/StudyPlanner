package hu.bme.aut.android.studyplanner.fragment

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
import androidx.lifecycle.ViewModelProvider
import hu.bme.aut.android.studyplanner.R
import hu.bme.aut.android.studyplanner.model.Task
import hu.bme.aut.android.studyplanner.viewmodel.SubjectViewModel
import kotlinx.android.synthetic.main.fragment_create.*
import java.util.*


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
        dialog?.setTitle(getString(R.string.createFrag))

        val spinner = view.findViewById<Spinner>(R.id.spinner)
        val days = resources.getStringArray(R.array.Days)
        spinner.onItemSelectedListener = this
        val arrayAdapter = ArrayAdapter(
            activity!!.applicationContext,
            android.R.layout.simple_spinner_item,
            days
        )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter

        //Getting subjects for subject spinner
        var arrayAdapterS: ArrayAdapter<String>
        val spinnerS = view.findViewById<Spinner>(R.id.subjectSpinner)
        spinnerS.onItemSelectedListener = this
        val subjectNames : MutableList<String> = mutableListOf()
        val subjectViewModel : SubjectViewModel = ViewModelProvider(this).get(SubjectViewModel::class.java)
        subjectViewModel.allSubjects.observe(this, { subjects ->
            for (element in subjects) {
                subjectNames.add(element.title)
            }
            arrayAdapterS = ArrayAdapter(
                activity!!.applicationContext,
                android.R.layout.simple_spinner_item,
                subjectNames
            )
            arrayAdapterS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerS.adapter = arrayAdapterS
        })

//        arrayAdapterS.notifyDataSetChanged()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        npWeek.maxValue = 15;
        npWeek.minValue = 1;

        btnCreate.setOnClickListener{
            val type: Int = if(radioGroup.checkedRadioButtonId == radioHW.id) 0
            else 1

            val subject = if(subjectSpinner.selectedItem != null) subjectSpinner.selectedItem.toString()
            else "Error"

            val week = npWeek.value
            val day = spinner.selectedItemPosition

            val pref = context?.getSharedPreferences("MyPref", 0) // 0 - for private mode
            val firstDay = pref?.getLong("firstDay",0)
            val date = firstDay!! +(week-1)*604800000+day*86400000
            val dateString= Date(date).toString()

            listener.onTaskCreated(
                Task(
                    week = week,
                    type = type,
                    subject = subject,
                    day = day,
                    date = date
                )
            )


            dismiss()
        }
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
//        if(id.toInt()==R.id.spinner) {
//            Toast.makeText(context,"Works",Toast.LENGTH_LONG).show()
//        }
    }

    override fun onNothingSelected(arg0: AdapterView<*>) {
    }

    interface TaskCreatedListener {
        fun onTaskCreated(task: Task)
    }
}
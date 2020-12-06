package hu.bme.aut.android.studyplanner.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.studyplanner.R
import hu.bme.aut.android.studyplanner.model.Subject
import kotlinx.android.synthetic.main.fragment_create_subject.*
import kotlinx.android.synthetic.main.fragment_schedule.*
import java.util.*

class ScheduleFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_schedule, container, false)
        dialog?.setTitle(getString(R.string.schedule_title))

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnDone.setOnClickListener{
            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return@setOnClickListener
            with (sharedPref.edit()) {
                putInt("firstDay", (calendar.date/1000).toInt())
                apply()
            }
//            val date = Date(((calendar.date/1000).toInt()).toLong()*1000L)
//            Toast.makeText(context,date.toString(),Toast.LENGTH_LONG).show()

            dismiss()
        }
    }

}
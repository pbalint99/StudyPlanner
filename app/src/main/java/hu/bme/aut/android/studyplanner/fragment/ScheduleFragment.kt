package hu.bme.aut.android.studyplanner.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.datepicker.MaterialDatePicker.Builder.datePicker
import hu.bme.aut.android.studyplanner.R
import kotlinx.android.synthetic.main.fragment_schedule.*
import java.util.*


class ScheduleFragment : DialogFragment() {
    lateinit var selectedDate: Date

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

        myCalendar.firstDayOfWeek = Calendar.MONDAY

        btnDone.setOnClickListener{
            val pref: SharedPreferences? =
                context?.getSharedPreferences("MyPref", 0) // 0 - for private mode
            val editor = pref?.edit()

            val cal = Calendar.getInstance()
            cal[Calendar.MINUTE] = 0
            cal[Calendar.HOUR_OF_DAY] = 8
            cal[Calendar.DAY_OF_MONTH] = myCalendar.dayOfMonth
            cal[Calendar.MONTH] = myCalendar.month
            cal[Calendar.YEAR] = myCalendar.year
            editor?.putLong("firstDay", cal.timeInMillis); // Storing integer
            editor?.apply()

            dismiss()
        }
    }

}
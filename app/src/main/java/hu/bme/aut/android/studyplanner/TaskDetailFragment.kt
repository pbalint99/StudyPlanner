package hu.bme.aut.android.studyplanner

import android.app.Activity
import android.content.Intent
import android.content.Intent.getIntent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import hu.bme.aut.android.studyplanner.model.Task
import hu.bme.aut.android.studyplanner.viewmodel.TaskViewModel
import kotlinx.android.synthetic.main.activity_task_detail.*
import kotlinx.android.synthetic.main.task_detail.*
import java.util.*


/**
 * A fragment representing a single Task detail screen.
 * This fragment is either contained in a [TaskListActivity]
 * in two-pane mode (on tablets) or a [TaskDetailActivity]
 * on handsets.
 */
class TaskDetailFragment : Fragment() {
    private lateinit var taskViewModel: TaskViewModel
    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "ARG_ITEM_ID"

        fun newInstance(position: Int): TaskDetailFragment {
            val args = Bundle()
            args.putInt(ARG_ITEM_ID, position)

            val result = TaskDetailFragment()
            result.arguments = args
            return result
        }
    }

    /**
     * The dummy content this fragment is presenting.
     */
    private var selectedTask: Task? = null

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val rootView = inflater.inflate(R.layout.task_detail, container, false)

            // Show the dummy content as text in a TextView.
//            rootView.findViewById<TextView>(R.id.task_detail).text = selectedTask?.subject ?: "error"

            return rootView
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        val pos: Int = activity?.intent?.getIntExtra(ARG_ITEM_ID, 0) ?: 0
        taskViewModel.allTasks.observe(this, Observer { tasks ->
            selectedTask = tasks[pos]
            val type: String = if (selectedTask?.type == 0) " homework"
            else " exam"
            val title: String = (selectedTask?.subject ?: "") + type
            view.findViewById<TextView>(R.id.task_detail).text = title
            val day = when (selectedTask!!.day) {
                0 -> "Monday"
                1 -> "Tuesday"
                2 -> "Wednesday"
                3 -> "Thursday"
                4 -> "Friday"
                else -> ""
            }
            task_week.hint= selectedTask?.date?.let {
                selectedTask!!.week.toString()
            }
            task_day.hint = day

            save_button.setOnClickListener {
                if (task_week.text.toString() != "" || task_day.text.toString() != "") {
                    val week = if(task_week.text.toString() != "") task_week.text.toString().toInt()
                    else -1
                    val day = when (task_day.text.toString()) {
                        "Monday" -> 0
                        "Tuesday" -> 1
                        "Wednesday" -> 2
                        "Thursday" -> 3
                        "Friday" -> 4
                        else -> -1
                    }
                    if (week % 1 == 0 && week > 0 && week < 16) {
                        taskViewModel.deleteAt(pos)
                        val newTask: Task
                        if (day != -1) {
                            newTask = Task(
                                week = week,
                                type = selectedTask!!.type,
                                subject = selectedTask!!.subject,
                                day = day,
                                date = selectedTask!!.date
                            )
                        } else {
                            newTask = Task(
                                week = week,
                                type = selectedTask!!.type,
                                subject = selectedTask!!.subject,
                                day = selectedTask!!.day,
                                date = selectedTask!!.date
                            )
                        }
                        taskViewModel.insert(newTask)
                        activity?.finish()
                    } else if (day != -1) {
                        taskViewModel.deleteAt(pos)
                        val newTask = Task(
                            week = selectedTask!!.week,
                            type = selectedTask!!.type,
                            subject = selectedTask!!.subject,
                            day = day,
                            date = selectedTask!!.date
                        )
                        taskViewModel.insert(newTask)
                        activity?.finish()
                    } else {
                        Toast.makeText(context, "Impossible date", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Nothing changed", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }


}
package hu.bme.aut.android.studyplanner

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import hu.bme.aut.android.studyplanner.application.NotificationUtils
import hu.bme.aut.android.studyplanner.fragment.ScheduleFragment
import hu.bme.aut.android.studyplanner.fragment.SubjectCreateFragment
import hu.bme.aut.android.studyplanner.fragment.TaskCreateFragment
import hu.bme.aut.android.studyplanner.model.Subject

import hu.bme.aut.android.studyplanner.model.Task
import hu.bme.aut.android.studyplanner.viewmodel.SubjectViewModel
import hu.bme.aut.android.studyplanner.viewmodel.TaskViewModel
import kotlinx.android.synthetic.main.task_list.*
import java.util.*

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [TaskDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */

class TaskListActivity : AppCompatActivity(),  SimpleItemRecyclerViewAdapter.TaskItemClickListener, TaskCreateFragment.TaskCreatedListener, SubjectCreateFragment.SubjectCreatedListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var subjectViewModel: SubjectViewModel
    private lateinit var simpleItemRecyclerViewAdapter: SimpleItemRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = title

        if (findViewById<NestedScrollView>(R.id.task_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        setupRecyclerView(findViewById(R.id.task_list))

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        taskViewModel.allTasks.observe(this, Observer { tasks ->
            simpleItemRecyclerViewAdapter.addAll(tasks)
        })

        subjectViewModel = ViewModelProvider(this).get(SubjectViewModel::class.java)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
//        val demo = mutableListOf<Task>()
//        demo.add(0,Task("UWU"))
//        recyclerView.adapter = SimpleItemRecyclerViewAdapter(this, demo, twoPane)
        simpleItemRecyclerViewAdapter = SimpleItemRecyclerViewAdapter()
        simpleItemRecyclerViewAdapter.itemClickListener = this
        task_list.adapter = simpleItemRecyclerViewAdapter
    }

    override fun onItemClick(task: Task) {
        var pos: Int = 0
        val intent = Intent(this, TaskDetailActivity::class.java).apply {
            for (i in taskViewModel.allTasks.value!!.indices) {
                if(taskViewModel.allTasks.value!![i].id == task.id) {
                    pos = i
                }
            }
        }
        intent.putExtra(TaskDetailFragment.ARG_ITEM_ID, pos)
        startActivity(intent)
    }

    override fun onItemLongClick(position: Int, view: View): Boolean {
        simpleItemRecyclerViewAdapter.deleteRow(position)
        taskViewModel.deleteAt(position)
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.actionbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        val id = item.itemId

        if (id == R.id.createTask) {
            val taskCreateFragment = TaskCreateFragment()
            taskCreateFragment.show(supportFragmentManager, "CreateFragment")
            return true
        }
        if (id == R.id.createSubject) {
            val subjectCreateFragment = SubjectCreateFragment()
            subjectCreateFragment.show(supportFragmentManager, "CreateFragment")
            return true
        }
        if (id == R.id.setSchedule) {
            val scheduleFragment = ScheduleFragment()
            scheduleFragment.show(supportFragmentManager, "CreateFragment")
            return true
        }
        if (id == R.id.deleteAll) {
            taskViewModel.deleteAll()
            return true
        }
        if (id == R.id.orderTime) {
            taskViewModel.orderBySubject = false
            simpleItemRecyclerViewAdapter.notifyDataSetChanged()
            return true
        }
        if (id == R.id.orderSubject) {
            taskViewModel.getAllTasksBySubject()
            simpleItemRecyclerViewAdapter.notifyDataSetChanged()
            taskViewModel.allTasks.observe(this, Observer { tasks ->
                simpleItemRecyclerViewAdapter.addAll(tasks)
            })
            return true
        }
        if (id == R.id.average) {
            var percent=0.0
            var gradesList= mutableListOf<Int>()
            var grade = 0
            taskViewModel.allTasks.observe(this, Observer { tasks ->
                subjectViewModel.allSubjects.observe(this,{subjects ->
                    for (i in subjects.indices) {
                        for (j in tasks.indices) {
                            if (tasks[j].subject == subjects[i].title) {
                                percent += tasks[j].percent
                            }
                        }
                        grade = when {
                            percent >= 85 -> 5
                            percent >= 70 -> 4
                            percent >= 55 -> 3
                            percent >= 40  -> 2
                            else -> 1
                        }
                        gradesList.add(i,grade)
                        percent = 0.0
                    }
                    Toast.makeText(applicationContext,"Average: "+(gradesList.sum()/gradesList.size).toString(),Toast.LENGTH_LONG).show()
                })
            })

            return true
        }
        return super.onOptionsItemSelected(item)

    }

    override fun onTaskCreated(task: Task) {
        taskViewModel.insert(task)
        val newCalendar = Calendar.getInstance()
        if(task.date>newCalendar.timeInMillis) {
            NotificationUtils().setNotification(task.date - 86400000, this)
        }
        simpleItemRecyclerViewAdapter.notifyDataSetChanged()
    }

    override fun onSubjectCreated(subject: Subject) {
        subjectViewModel.insert(subject)

    }


}
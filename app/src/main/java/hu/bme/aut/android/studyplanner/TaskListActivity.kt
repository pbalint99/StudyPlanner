package hu.bme.aut.android.studyplanner

import android.content.Intent
import android.os.Bundle
import androidx.core.widget.NestedScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import hu.bme.aut.android.studyplanner.model.Task
import hu.bme.aut.android.studyplanner.viewmodel.TaskViewModel
import kotlinx.android.synthetic.main.task_list.*

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [TaskDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */

class TaskListActivity : AppCompatActivity(),  SimpleItemRecyclerViewAdapter.TaskItemClickListener, TaskCreateFragment.TaskCreatedListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var simpleItemRecyclerViewAdapter: SimpleItemRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = title

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            val taskCreateFragment = TaskCreateFragment()
            taskCreateFragment.show(supportFragmentManager, "TAG")
        }

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
    }

    override fun onTaskCreated(task: Task) {
        taskViewModel.insert(task)
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
        val intent = Intent(this, TaskDetailActivity::class.java).apply {
            //putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)
    }

    override fun onItemLongClick(position: Int, view: View): Boolean {
        simpleItemRecyclerViewAdapter.deleteRow(position)
        taskViewModel.deleteAt(position)
        return false
    }


}
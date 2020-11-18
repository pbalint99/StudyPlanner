package hu.bme.aut.android.studyplanner.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.studyplanner.R
import hu.bme.aut.android.studyplanner.model.Task

class SimpleItemRecyclerViewAdapter() : RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        //TODO
        //private val parentActivity: TaskListActivity
        //private val values: List<Task>
        private val twoPane: Boolean = false

        //private val onClickListener: View.OnClickListener
        var itemClickListener: TaskItemClickListener? = null
        private val taskList = mutableListOf<Task>()

//        init {
//            onClickListener = View.OnClickListener { v ->
//                val item = v.tag as Task
//                if (twoPane) {
//                    val fragment = TaskDetailFragment().apply {
//                        arguments = Bundle().apply {
//                            putString(TaskDetailFragment.ARG_ITEM_ID, item.title)
//                        }
//                    }
////                    parentActivity.supportFragmentManager
////                        .beginTransaction()
////                        .replace(R.id.task_detail_container, fragment)
////                        .commit()
//                } else {
//                    val intent = Intent(v.context, TaskDetailActivity::class.java).apply {
//                        putExtra(TaskDetailFragment.ARG_ITEM_ID, item.title)
//                    }
//                    v.context.startActivity(intent)
//                }
//            }
//        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.task_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val task = taskList[position]

            holder.task = task

            holder.titleView.text = task.title
            holder.weekView.text = task.week.toString()
            holder.subjectView.text = task.subject
            holder.typeImage.setImageResource(getTypeImage(task.type))
        }

        override fun getItemCount() = taskList.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val titleView: TextView = view.findViewById(R.id.listTitle)
            val weekView: TextView = view.findViewById(R.id.listWeek)
            val typeImage: ImageView = view.findViewById(R.id.iv)
            val subjectView: TextView = view.findViewById(R.id.listSubject)
            var task: Task? = null

            init {
                itemView.setOnClickListener {
                    task?.let { task -> itemClickListener?.onItemClick(task) }
                }

                itemView.setOnLongClickListener { view ->
                    //TODO Kiszedtem egy taskot a végéről
                    task?.let {task -> itemClickListener?.onItemLongClick(adapterPosition, view) }
                    true
                }
            }
        }

//        fun addItem(task: Task) {
//            val size = taskList.size
//            taskList.add(task)
//            notifyItemInserted(size)
//        }

        fun addAll(tasks: List<Task>) {
            taskList.clear()
            taskList.addAll(tasks)
            notifyDataSetChanged()
        }

        fun deleteRow(position: Int) {
            taskList.removeAt(position)
            notifyItemRemoved(position)
        }

        private fun getTypeImage(type: Int): Int {
            return when (type) {
                0 -> R.drawable.homework
                1 -> R.drawable.test
                else -> R.drawable.close
            }
        }

        interface TaskItemClickListener {
            fun onItemClick(task: Task)
            fun onItemLongClick(position: Int, view: View): Boolean
        }
    }
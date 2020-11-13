package hu.bme.aut.android.studyplanner

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
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
        }

        override fun getItemCount() = taskList.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val titleView: TextView = view.findViewById(R.id.listTitle)
            var task: Task? = null

//            init {
//                itemView.setOnClickListener {
//                    todo?.let { todo -> itemClickListener?.onItemClick(todo) }
//                }
//
//                itemView.setOnLongClickListener { view ->
//                    todo?.let {todo -> itemClickListener?.onItemLongClick(adapterPosition, view, todo) }
//                    true
//                }
//            }
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

        interface TaskItemClickListener {
            fun onItemClick(task: Task)
            fun onItemLongClick(position: Int, view: View): Boolean
        }
    }
package com.memory.memory_kotlin.view

import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.memory.memory_kotlin.R
import com.memory.memory_kotlin.TaskLoginRecyclerViewHolder
import com.memory.memory_kotlin.TasksFirebase

class TaskLoginRecyclerAdapter(private val context: Context,
                               private val itemClickListener: TaskLoginRecyclerViewHolder.ItemClickListener,
                               private val List: MutableList<TasksFirebase>
)
    : RecyclerView.Adapter<TaskLoginRecyclerViewHolder>() {

    private var mRecyclerView : RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        mRecyclerView = null

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskLoginRecyclerViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)

        view.setOnClickListener { view ->
            mRecyclerView?.let {
                itemClickListener.onItemClick(view, it.getChildAdapterPosition(view))
            }
        }

        return TaskLoginRecyclerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return List.size
    }

    override fun onBindViewHolder(holder: TaskLoginRecyclerViewHolder, position: Int) {
        val tasks = List?.get(position)
        holder.task.text = tasks?.learnContext
        holder.date.text = DateFormat.format("yyyy/MM/dd",tasks?.created_at)
    }
}
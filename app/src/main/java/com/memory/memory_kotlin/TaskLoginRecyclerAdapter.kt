package com.memory.memory_kotlin

import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TaskLoginRecyclerAdapter(private val context: Context,
                               private val itemClickListener: TodayToDoTaskLoginActivity,
                               private val List: MutableList<TasksFirebase>
)
    : RecyclerView.Adapter<TaskLoginRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskLoginRecyclerViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
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
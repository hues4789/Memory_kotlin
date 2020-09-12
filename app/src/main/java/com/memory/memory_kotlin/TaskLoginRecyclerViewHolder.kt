package com.memory.memory_kotlin

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskLoginRecyclerViewHolder(view: View): RecyclerView.ViewHolder(view) {
    // 独自に作成したListener
    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    val task: TextView = view.findViewById(R.id.task)
    val date: TextView = view.findViewById(R.id.create_dt)
}
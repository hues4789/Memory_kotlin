package com.memory.memory_kotlin

import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter

class Adapter(
    private val context: Context,
    //data
    private val List: OrderedRealmCollection<Tasks>?,
    private val itemClickListener: ViewHolder.ItemClickListener,
    private val autoUpdate: Boolean
):RealmRecyclerViewAdapter<Tasks,Adapter.ViewHolder>(List,autoUpdate){

    private var mRecyclerView : RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        mRecyclerView = null

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        interface ItemClickListener {
            fun onItemClick(view: View, position: Int)
        }

        val task: TextView = view.findViewById(R.id.task)
        val date: TextView = view.findViewById(R.id.create_dt)
    }

    //リストの取得件数
    override fun getItemCount(): Int {
        return List?.size ?: 0
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false)

        view.setOnClickListener { view ->
            mRecyclerView?.let {
                itemClickListener.onItemClick(view, it.getChildAdapterPosition(view))
            }
        }
        return ViewHolder(view)
    }
    //データを取り出して表示する
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tasks = List?.get(position)
        holder.task.text = tasks?.learnContext
        holder.date.text = DateFormat.format("yyyy/MM/dd",tasks?.created_at)
    }
}
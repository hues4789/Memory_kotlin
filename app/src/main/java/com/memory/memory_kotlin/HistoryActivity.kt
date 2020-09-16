package com.memory.memory_kotlin

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmResults
import kotlinx.android.synthetic.main.list_item.view.*

class HistoryActivity : AppCompatActivity(),Adapter.ViewHolder.ItemClickListener{

    private lateinit var mRealm : Realm

    var read: OrderedRealmCollection<Tasks> = RealmList()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = getString(R.string.histories)

        setContentView(R.layout.activity_today_to_do_task)

        Realm.init(this)
        mRealm = Realm.getDefaultInstance()

        read = readList()

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)

        //RecyclerViewのAdapter、layoutなどの設定
        val adapter = Adapter(this,read,this, true)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter


        val swipeToDismissTouchHelper by lazy {
            getSwipeToDismissTouchHelper(adapter)
        }

        swipeToDismissTouchHelper.attachToRecyclerView(recyclerView)
    }

    fun readList(): RealmResults<Tasks> {
        return mRealm.where(Tasks::class.java).findAll()
    }

    override fun onDestroy() {
        super.onDestroy()
        mRealm.close()
    }

    override fun onItemClick(view: View, position: Int) {
        if(view.create_dt.text.isEmpty()){
            view.task.text = read[position].learnContext
            val createDate = DateFormat.format("yyyy/MM/dd",read[position].created_at)
            view.create_dt.text = createDate
        }else {
            view.task.text = read[position].learn_details
            view.create_dt.text = ""
        }
    }

    private fun getSwipeToDismissTouchHelper(adapter: Adapter) =
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.ACTION_STATE_IDLE,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //ToDo 削除処理
                adapter.notifyItemChanged(direction)
            }

            override fun onChildDraw(
                        c: Canvas,
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        dX: Float,
                        dY: Float,
                        actionState: Int,
                        isCurrentlyActive: Boolean
            ) {
            if(dX<0) {
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                val itemView = viewHolder.itemView
                val background = ColorDrawable(Color.RED)
                val deleteIcon = AppCompatResources.getDrawable(
                    this@HistoryActivity,
                    R.drawable.ic_baseline_delete_24
                )
                val iconMarginVertical =
                    (viewHolder.itemView.height - deleteIcon!!.intrinsicHeight) / 2

                deleteIcon.setBounds(
                    itemView.left + iconMarginVertical,
                    itemView.top + iconMarginVertical,
                    itemView.left + iconMarginVertical + deleteIcon.intrinsicWidth,
                    itemView.bottom - iconMarginVertical
                )
                background.setBounds(
                    itemView.left,
                    itemView.top,
                    itemView.right + dX.toInt(),
                    itemView.bottom
                )
                background.draw(c)
                deleteIcon.draw(c)
            }
            }
        })
}
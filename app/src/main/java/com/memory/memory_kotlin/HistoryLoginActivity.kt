package com.memory.memory_kotlin

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.memory.memory_kotlin.view.TaskLoginRecyclerAdapter
import kotlinx.android.synthetic.main.list_item.view.*

class HistoryLoginActivity : AppCompatActivity(),TaskLoginRecyclerViewHolder.ItemClickListener  {

    private lateinit var auth: FirebaseAuth
    private var currentUser: FirebaseUser? = null

    private var taskList:MutableList<TasksFirebase> = ArrayList<TasksFirebase>()
    private var documentIdList:MutableList<String> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = getString(R.string.histories)

        auth = FirebaseAuth.getInstance()
        currentUser = FirebaseAuth.getInstance().currentUser

        //戻る
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setContentView(R.layout.activity_today_to_do_task)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)

        val loginId = auth.uid.toString()

        //DB取得
        val db = FirebaseFirestore.getInstance()
        db.collection("Tasks")
            .whereEqualTo("login_user",loginId)
            //降順
            .orderBy(TasksFirebase::created_at.name, Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                for(document in documents){
                    documentIdList.add(document.id)
                }
            }
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    if(it.result != null){
                        taskList = it.result!!.toObjects(TasksFirebase::class.java)
                        val adapter =
                            TaskLoginRecyclerAdapter(
                                this,
                                this,
                                taskList
                            )
                        recyclerView.setHasFixedSize(true)
                        recyclerView.layoutManager = LinearLayoutManager(this)
                        recyclerView.adapter = adapter

                        val swipeToDismissTouchHelper by lazy {
                            getSwipeToDismissTouchHelper(adapter)
                        }

                        swipeToDismissTouchHelper.attachToRecyclerView(recyclerView)
                    }
                } else {
                    Toast.makeText(applicationContext,"今日のタスクはありません。", Toast.LENGTH_LONG).show()
                }
            }
    }

    override fun onItemClick(view: View, position:Int){
        if(view.create_dt.text.isEmpty()){
            view.task.text = taskList[position].learnContext

            val createDate = DateFormat.format("yyyy/MM/dd",taskList[position].created_at)
            view.create_dt.text = createDate
        }else {
            view.task.text = taskList[position].learn_details
            view.create_dt.text = ""
        }
    }
    //メニュー処理
    override fun onCreateOptionsMenu(menu: Menu?):Boolean{
        super.onCreateOptionsMenu(menu)

        val inflater = menuInflater
        inflater.inflate(R.menu.task_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout_menu -> {
                auth.signOut()
                currentUser = null
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                return true
            }
            android.R.id.home ->{
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun getSwipeToDismissTouchHelper(adapter: TaskLoginRecyclerAdapter) =
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
                //DB削除
                val db = FirebaseFirestore.getInstance()
                db.collection("Tasks")
                    .document(documentIdList[viewHolder.adapterPosition])
                    .delete()

                val intent = Intent(this@HistoryLoginActivity, HistoryLoginActivity::class.java)
                startActivity(intent)
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
                        this@HistoryLoginActivity,
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
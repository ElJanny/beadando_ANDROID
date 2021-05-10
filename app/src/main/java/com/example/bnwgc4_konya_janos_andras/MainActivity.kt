package com.example.bnwgc4_konya_janos_andras

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_todo.*

lateinit var todoAdapter: TodoAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("HEELO","HEEEEEEEEEEELO")
        var db =  DBHandler(this)
        setContentView(R.layout.activity_main)
        todoAdapter = TodoAdapter(mutableListOf(),db)
        todoAdapter.seState(0)
        rvTodoItems.adapter = todoAdapter
        rvTodoItems.layoutManager = LinearLayoutManager(this)
        db.createTable()
        todoAdapter.getAllTodosFromDB(0)
        btnReadTodos.setOnClickListener{
            db.close()
            todoAdapter.clearList()
            val intent = Intent(this, kesz::class.java)
            startActivity(intent)
        }

        btnAddTodo.setOnClickListener{
            val todoTitle = etTodoTitle.text.toString()
            if(todoTitle.isNotEmpty()){
                val todo = Todo(todoTitle)
                todoAdapter.addTodo(todo)
                etTodoTitle.text.clear()
            }

        }

    }
}
package com.example.bnwgc4_konya_janos_andras

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_kesz.*


class kesz : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var db =  DBHandler(this)
        var tdoAdapter = TodoAdapter(mutableListOf(),db)
        tdoAdapter.seState(1)
        setContentView(R.layout.activity_kesz)
        SecondrvTodoItems.adapter = tdoAdapter
        SecondrvTodoItems.layoutManager = LinearLayoutManager(this)
        tdoAdapter.getAllTodosFromDB(1)
        btnDeleteDatabase.setOnClickListener({
            tdoAdapter.clearList()
            tdoAdapter.deleteDatabase()
        })
    }
}
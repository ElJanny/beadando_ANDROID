package com.example.bnwgc4_konya_janos_andras

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_todo.view.*


class TodoAdapter(
    private var todos:MutableList<Todo>,
    private var db: DBHandler
): RecyclerView.Adapter<TodoAdapter.TodoViewHolder>(){

    class TodoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
      return TodoViewHolder(
          LayoutInflater.from(parent.context).inflate(
              R.layout.item_todo,
              parent,
              false
          )
      )
    }

    fun addTodo(todo:Todo){
        todos.add(todo)
        notifyItemInserted(todos.size-1)
    }

    fun deleteDoneTodos(){
        addTodosToDB()
        todos.removeAll{ todo ->
            todo.isChecked
        }

        notifyDataSetChanged()
    }

    fun addTodosToDB() {

        val k:MutableList<Todo> =  todos
        k.removeAll{todo -> !todo.isChecked}
        db.insertData(k)

    }

    fun getAllTodosFromDB(){
        todos = db.readData()
        notifyDataSetChanged()
    }

    private fun toggleStrikeThrough(TodoelementId: TextView, isChecked: Boolean) {
        if(isChecked){
            TodoelementId.paintFlags = TodoelementId.paintFlags or STRIKE_THRU_TEXT_FLAG
        }else{
            TodoelementId.paintFlags = TodoelementId.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val curTodo=todos[position]
        holder.itemView.apply {
            TodoelementId.text = curTodo.title
            TodocheckboxId.isChecked = curTodo.isChecked
            toggleStrikeThrough(TodoelementId,curTodo.isChecked)
            TodocheckboxId.setOnCheckedChangeListener{_, isChecked ->
                toggleStrikeThrough(TodoelementId, isChecked)
                curTodo.isChecked= !curTodo.isChecked
            }
        }
    }

    override fun getItemCount(): Int {
        return todos.size
    }


}
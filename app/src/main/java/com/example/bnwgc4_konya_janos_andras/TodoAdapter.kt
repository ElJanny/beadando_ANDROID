package com.example.bnwgc4_konya_janos_andras

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.icu.text.Transliterator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_todo.view.*
import java.lang.Exception


class TodoAdapter(
    private var todos:MutableList<Todo>,
    private var db: DBHandler
): RecyclerView.Adapter<TodoAdapter.TodoViewHolder>(){
    var state: Int = 1 ;
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
        db.insertData(todo)
        todos.add(todo)
        notifyItemInserted(todos.size-1)
    }

    fun getAllTodosFromDB(number: Int){
        todos = db.readData(number)
        notifyDataSetChanged()
    }

    fun deleteDatabase(){
        db.deleteDataBase()
    }

    fun clearList(){
        todos.clear()
       notifyDataSetChanged()
    }

    fun removeTodo(todo: Todo,b: Boolean,position: Int){
        todos.remove(todo)
        notifyItemRemoved(position)
        //notifyDataSetChanged();


       /* val newPosition: Int = holder.getAdapterPosition()
        model.remove(newPosition)
        notifyItemRemoved(newPosition)
        notifyItemRangeChanged(newPosition, model.size())
    */}

    fun seState(number: Int){
    state = number
    }

     fun toggleStrikeThrough(TodoelementId: TextView, isChecked: Boolean,todo: Todo, position: Int) {
        if(isChecked){
            TodoelementId.paintFlags = TodoelementId.paintFlags or STRIKE_THRU_TEXT_FLAG
        }else{
            TodoelementId.paintFlags = TodoelementId.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }

         if(state == 0 && isChecked){
             removeTodo(todo,true,position)
             db.updateData(todo,isChecked)
             notifyDataSetChanged()
         }else if (state == 1 && !isChecked){
             removeTodo(todo,false,position)
             db.updateData(todo,isChecked)
             notifyDataSetChanged()
         }


    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val curTodo=todos[position]
        holder.itemView.apply {
            TodoelementId.text = curTodo.title
            TodocheckboxId.isChecked = curTodo.isChecked
            toggleStrikeThrough(TodoelementId,curTodo.isChecked, curTodo, position)
            TodocheckboxId.setOnCheckedChangeListener{_, isChecked ->
                toggleStrikeThrough(TodoelementId, isChecked, curTodo, position)
                curTodo.isChecked= !curTodo.isChecked

            }
        }

    }

    override fun getItemCount(): Int {
        return todos.size
    }


}
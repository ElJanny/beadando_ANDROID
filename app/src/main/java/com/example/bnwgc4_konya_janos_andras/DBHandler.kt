package com.example.bnwgc4_konya_janos_andras

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val DATABASE_NAME="MyDB"
val TABLE_NAME="Done_TODOS"
val COL_NAME="TODO_TEXT"
val COL_ID="Id"

class DBHandler(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null,1){
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable= "CREATE TABLE " + TABLE_NAME + "("+
                COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                COL_NAME+" VARCHAR(256))";
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun insertData(todo: MutableList<Todo>){
        val db =  this.writableDatabase
        var cv = ContentValues()
        todo.forEach{todo ->
            run {
                cv.put(COL_NAME, todo.title)
                var result = db.insert(TABLE_NAME, null, cv)
                if (result == -1.toLong()) {
                    Toast.makeText(context, "FAILED", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    fun readData() : MutableList<Todo>{
        var list : MutableList<Todo> = mutableListOf()
        val db = this.readableDatabase
        val query = "Select * from " + TABLE_NAME
        val result=db.rawQuery(query,null)
        if(result.moveToFirst()){
            do{
                var todo = Todo(result.getString(result.getColumnIndex(COL_NAME)))
                list.add(todo)
            }while(result.moveToNext())


        }
        return list
    }

}
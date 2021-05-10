package com.example.bnwgc4_konya_janos_andras

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast

val DATABASE_NAME="MyDB"
val TABLE_NAME="Done_TODOS"
val COL_NAME="TODO_TEXT"
val COL_ID="Id"
val IS_CHECKED= "is_checked"

class DBHandler(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null,1){
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable= "CREATE TABLE " + TABLE_NAME + "("+
                COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                COL_NAME+" VARCHAR(256), "+ IS_CHECKED + " INTEGER)";
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun createTable(){
        val db = this.writableDatabase
        val createTable= "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("+
                COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                COL_NAME+" VARCHAR(256), "+ IS_CHECKED + " INTEGER)";
        db.execSQL(createTable)
    }

    fun insertData(todo:Todo){
        val db =  this.writableDatabase
        var cv = ContentValues()

            run {
                cv.put(COL_NAME, todo.title)
                cv.put(IS_CHECKED, 0)
                var result = db.insert(TABLE_NAME, null, cv)
                if (result == -1.toLong()) {
                    Toast.makeText(context, "FAILED", Toast.LENGTH_SHORT).show()
                }

        }
    }

    fun updateData(todo: Todo,isChecked: Boolean)
    {
        val db = this.writableDatabase
        val checked: Int
        if(isChecked){
            checked = 1
        }else{
            checked = 0
        }
        val query = "UPDATE "+ TABLE_NAME+" SET "+ IS_CHECKED + " = " + checked + " WHERE " + COL_NAME + " LIKE '"+todo.title+"'"
        db.execSQL(query)
    }

    fun readData(checked: Int) : MutableList<Todo>{
        var list : MutableList<Todo> = mutableListOf()
        val db = this.readableDatabase
        val query = "Select * from " + TABLE_NAME + " WHERE "+ IS_CHECKED +"=="+checked+";"
        val result=db.rawQuery(query,null)
        if(result.moveToFirst()){
            do{
                var todo = Todo(result.getString(result.getColumnIndex(COL_NAME)))
                if(checked==0){
                    todo.isChecked = false
                }else {
                    todo.isChecked = true
                }
                list.add(todo)
            }while(result.moveToNext())
        }
        return list
    }


    fun deleteDataBase(){
        val db = this.writableDatabase
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
    }

}
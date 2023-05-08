package com.italkutalk.lab8

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//自訂建構子並繼承 SQLiteOpenHelper 類別
class DBHelper (
    context: Context,
    name: String = database,
    factory: SQLiteDatabase.CursorFactory? = null,
    version: Int = v
) : SQLiteOpenHelper(context, name, factory, version) {

    companion object {
        private const val database = "CSIE_Professors" //資料庫名稱
        private const val v = 1 //資料庫版本
    }

    override fun onCreate(db: SQLiteDatabase) {
        //建立 myTable 資料表，表內有 book 字串欄位和 price 整數欄位
        db.execSQL("CREATE TABLE Professors(teacher_id integer PRIMARY KEY AUTOINCREMENT, name text NOT NULL, lab text NOT NULL, phone text NOT NULL, email text)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int,
                           newVersion: Int) {
        //升級資料庫版本時，刪除舊資料表，並重新執行 onCreate()，建立新資料表
        db.execSQL("DROP TABLE IF EXISTS Professors")
        onCreate(db)
    }
}
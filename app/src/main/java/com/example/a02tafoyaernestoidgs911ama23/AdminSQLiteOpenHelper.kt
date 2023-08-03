package com.example.a02tafoyaernestoidgs911ama23

import android.content.Context
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper

class AdminSQLiteOpenHelper(
    context:Context?,
    name:String?,
    factory:CursorFactory?,
    version:Int,
    errorHandler: DatabaseErrorHandler
): SQLiteOpenHelper(context, name, factory, version, errorHandler) {
    override fun onCreate(BasedeDatos: SQLiteDatabase?) {
        BasedeDatos?.execSQL("CREATE TABLE temperature(id integer primary key autoincrement, value integer, d_t date_time )")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

}
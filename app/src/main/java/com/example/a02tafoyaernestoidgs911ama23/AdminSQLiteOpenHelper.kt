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
    version:Int
): SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(BasedeDatos: SQLiteDatabase?) {
        BasedeDatos?.execSQL("CREATE TABLE sensores(lectura integer primary key autoincrement not null, temperatura integer,humedad integer, fecha_hora date_time )")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

}
package com.sanketguru.myworth

import android.app.Application
import androidx.room.Room
import com.sanketguru.myworth.data.AppDatabase


/**
 * Created by Sanket Gurav on 1/2/2018.
 */

class MyWorthApp : Application() {
    private val dataBaseName = "databaseName"
    lateinit var db: AppDatabase
    override fun onCreate() {
        super.onCreate()
        db = getDataBase()
    }

    private fun getDataBase():AppDatabase = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, dataBaseName
    ).build()
}

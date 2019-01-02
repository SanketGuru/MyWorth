package com.sanketguru.myworth

import android.app.Application
import android.arch.persistence.room.Room
import com.sanketguru.myworth.data.AppDatabase


/**
 * Created by Sanket Gurav on 1/2/2018.
 */

class MyWorthApp : Application() {
     val dataBaseName="databaseName"
  lateinit var  db:AppDatabase
    override fun onCreate() {
        super.onCreate()
         db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, dataBaseName
        ).build()


                //    component.inject(this)
//        if (BuildConfig.DEBUG) {
//            Timber.plant(Timber.DebugTree())
//        }
     //   component.inject(this)

    }


}

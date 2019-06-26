package com.sanketguru.myworth

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class ActionReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null) {
            val data = intent.getStringExtra("data")
            Log.v( "Received data : ", data+    intent.action)
        }
    }
}
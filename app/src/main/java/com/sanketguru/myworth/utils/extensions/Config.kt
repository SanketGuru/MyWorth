package com.sanketguru.myworth.utils.extensions

import android.content.Context
import androidx.preference.PreferenceManager

object Config {
    fun getCurrency(ctx: Context?):String{
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx /* Activity context */)
        return sharedPreferences.getString("currency", "")
    }
}
package com.sanketguru.myworth.utils.extensions

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager

object Config {
    fun getCurrency(ctx: Context?):String{
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx /* Activity context */)
        return sharedPreferences.getString("currency", "")
    }

    fun getSavedMode(ctx: Context?):Boolean{
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx /* Activity context */)
        return sharedPreferences.getBoolean("theme", false)
    }

    fun getMode(value :Boolean): Int =  if (value)
        AppCompatDelegate.MODE_NIGHT_YES
    else
        AppCompatDelegate.MODE_NIGHT_NO
}
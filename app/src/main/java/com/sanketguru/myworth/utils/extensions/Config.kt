package com.sanketguru.myworth.utils.extensions

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import java.lang.NullPointerException

object Config {
    fun getCurrency(ctx: Context?):String = try {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx /* Activity context */)
            if (sharedPreferences!=null)sharedPreferences.getString("currency", "") else ""
        }
        catch (npe:NullPointerException)
        {
            ""
        }


    fun getSavedMode(ctx: Context?):Boolean =
        try {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx /* Activity context */)
             sharedPreferences?.getBoolean("theme", false) ?: false
        }
        catch (npe:NullPointerException)
        {
            false
        }



    fun getMode(value :Boolean): Int =  if (value)
        AppCompatDelegate.MODE_NIGHT_YES
    else
        AppCompatDelegate.MODE_NIGHT_NO
}
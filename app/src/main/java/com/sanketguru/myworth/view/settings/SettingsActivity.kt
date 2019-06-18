package com.sanketguru.myworth.view.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sanketguru.myworth.R

class SettingsActivity : AppCompatActivity(){

  override fun onCreate( savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_setting)
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.settings, SettingFragment())
                    .commit()
        } else {
            title = savedInstanceState.getCharSequence(TITLE_TAG)
        }
    }
    companion object{
        private const val TITLE_TAG = "settingsActivityTitle"
    }
}
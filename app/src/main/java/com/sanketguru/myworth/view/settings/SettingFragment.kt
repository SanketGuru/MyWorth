package com.sanketguru.myworth.view.settings

import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.sanketguru.myworth.R
import com.sanketguru.myworth.utils.extensions.Config.getMode


//https://medium.com/over-engineering/setting-up-a-material-components-theme-for-android-fbf7774da739
//Material Color tool
//https://material.io/tools/color/#!/?view.left=0&view.right=0
//Change Theme
//https://medium.com/mindorks/mastering-android-themes-chapter-4-591e03320182

//basic android
//https://android.jlelse.eu/learning-android-development-in-2018-part-1-83a514f6a205
class SettingFragment : PreferenceFragmentCompat()  {
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        view.setBackgroundColor(Color.
//        WHITE)//Todo Dark theme color needs to be set
//        super.onViewCreated(view, savedInstanceState)
//    }
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting_screen, rootKey)

    val editTextPreference = preferenceManager.findPreference<EditTextPreference>("currency")
    if (editTextPreference != null) {
        if(editTextPreference is EditTextPreference){
           // editTextPreference.setOnBindEditTextListener {  }
            editTextPreference.
                    setOnBindEditTextListener(
                            EditTextPreference.OnBindEditTextListener
                            { editText -> editText.filters= arrayOf(InputFilter.LengthFilter(3)) })


            // editTextPreference..filters= arrayOf(InputFilter.LengthFilter(3))
        }
        else{
         //   Toast.makeText(context,"No Found",Toast.LENGTH_LONG).show()
        }

        val switchThemeTextPreference = preferenceManager.findPreference<SwitchPreferenceCompat>("theme")
        if (switchThemeTextPreference != null) {
//            switchThemeTextPreference.switchTextOff="Dark"
//            switchThemeTextPreference.switchTextOn="Light"
            switchThemeTextPreference.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, newValue ->
                if (preference.key.contentEquals("theme")) {
                //    Log.v("Say", "preference : ${newValue}")
                    if (newValue is Boolean) {
                        AppCompatDelegate
                                .setDefaultNightMode(
                                        getMode(newValue)
                                )
                    }
                    //   AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    activity?.recreate()
                }

                true
            }
        }
    }

    }



}
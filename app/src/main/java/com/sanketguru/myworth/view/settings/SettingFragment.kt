package com.sanketguru.myworth.view.settings

import android.graphics.Color
import com.sanketguru.myworth.R
import android.os.Bundle
import android.view.View
import androidx.preference.PreferenceFragmentCompat

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
    }
}
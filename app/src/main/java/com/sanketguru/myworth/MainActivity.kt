package com.sanketguru.myworth

//import androidx.localbroadcastmanager.content.LocalBroadcastManager
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sanketguru.myworth.utils.extensions.Config
import com.sanketguru.myworth.view.SliderFragment
import com.sanketguru.myworth.view.settings.SettingsActivity

//Dark Theme
//https://proandroiddev.com/android-dark-theme-implementation-recap-4fcffb0c4bff
//https://medium.com/mindorks/mastering-android-themes-chapter-4-591e03320182
class MainActivity : AppCompatActivity(), AppCallBack {
    private val commonReceiver: BroadcastReceiver = ActionReceiver()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpTheme()
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            addFragment(SliderFragment(), SliderFragment.tagTitle)
        }
        //  registerReceiver(commonReceiver, IntentFilter())

    }

    private fun setUpTheme() {
//        AppCompatDelegate
//                .setDefaultNightMode(
//                        Config.getMode(Config.getSavedMode(this))
//                )

        if (Config.getSavedMode(this)) {
            if (AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate
                        .setDefaultNightMode(
                                Config.getMode(true)
                        )
                recreate()
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {

                //   addFragment(SettingFragment(), "Setting")
                val settingIntent = Intent(this, SettingsActivity::class.java)
                startActivity(settingIntent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onConfigurationChanged(configuration: Configuration) {
        super.onConfigurationChanged(configuration)
        //   Log.v("Say", "Current ${configuration.uiMode} : ${Configuration.UI_MODE_NIGHT_MASK} : ${configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK}")
        val currentNightMode = configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_NO -> {
                       Log.v("Say", "Light")
                recreate()
            } // Night mode is not active, we're using the light theme
            Configuration.UI_MODE_NIGHT_YES -> {
                       Log.v("Say", "Dark")
                recreate()
            } // Night mode is active, we're using dark theme
        }
    }

    override fun addFragment(fragment: androidx.fragment.app.Fragment, tag: String) {

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)

        val index = fragmentManager.backStackEntryCount
        if (index > 1) {
            try {
                val entry = fragmentManager.getBackStackEntryAt(index - 1)
                //Timber.d("Fragment On Top was %s", entry.name)
                if (entry != null) {
                    if (entry.name == tag) {
                        return
                    }
                }
            } catch (e: IndexOutOfBoundsException) {
                //   Timber.e(e, "addFragment")
                return
            }
        }
        // }

        fragmentTransaction.add(R.id.fragment, fragment)
        fragmentTransaction.addToBackStack(tag)
        fragmentTransaction.commit()
    }

    override fun removeFragment(fragment: androidx.fragment.app.Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.remove(fragment)
        fragmentTransaction.commit()
    }

    override fun replaceFragment(fragment: androidx.fragment.app.Fragment, tag: String) {

        val fragmentManager = supportFragmentManager
        fragmentManager.popBackStack()
        val fragmentTransaction = fragmentManager.beginTransaction()
        // fragmentTransaction.remove(currFrag)
        fragmentTransaction.add(R.id.fragment, fragment, tag)
        fragmentTransaction.addToBackStack(tag)
        fragmentTransaction.commit()
    }

    override fun popFragmentAt(name: String) {
        supportFragmentManager.popBackStack(name, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    override fun goHome() {
        val fragmentManager = supportFragmentManager
        var dd = fragmentManager.backStackEntryCount
        while (dd >= 2) {
            fragmentManager.popBackStack()
            dd--
        }

    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        // outPersistentState?.putString("say", "123")
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
        if (persistentState != null) {
            //      Log.v("Say", persistentState.getString("say"))
        }
    }

    override fun goTo(fragment: Class<androidx.fragment.app.Fragment>) {
        val fragmentManager = supportFragmentManager
        val fragList = fragmentManager.fragments

        for (af in fragList) {

            if (af.javaClass == fragment) {
            } else {
                fragmentManager.popBackStack()
            }
        }
    }

    override fun onBackPressed() {
        val entryCount = supportFragmentManager.backStackEntryCount
        if (entryCount <= 1) {
            //getOut of app
            val builder = MaterialAlertDialogBuilder(this)
            builder.setMessage(getString(R.string.exit_app_msg))
                    .setPositiveButton(android.R.string.yes) { dialog, which -> finish() }
                    .create().show()
        } else {
            supportFragmentManager.popBackStack()
        }
    }


}

interface AppCallBack {
    fun addFragment(fragment: androidx.fragment.app.Fragment, tag: String = "b")
    fun removeFragment(fragment: androidx.fragment.app.Fragment)
    fun replaceFragment(fragment: androidx.fragment.app.Fragment, tag: String = "b")
    fun popFragmentAt(name: String)
    fun goHome()
    fun goTo(fragment: Class<androidx.fragment.app.Fragment>)
}
package com.sanketguru.myworth

import android.os.Bundle
import android.os.PersistableBundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.sanketguru.myworth.view.SliderFragment

class MainActivity : AppCompatActivity(), AppCallBack {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //   setSupportActionBar(toolbar)
        if (savedInstanceState == null) {
            addFragment(SliderFragment(), SliderFragment.tagTitle)
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
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun addFragment(fragment: androidx.fragment.app.Fragment, tag: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
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
        var dd=fragmentManager.backStackEntryCount
        while (dd>=2){
            fragmentManager.popBackStack()
            dd--
        }
//        val fragList = fragmentManager.fragments
//        for (af in fragList) {
//            if (af is SliderFragment) {
//            } else {
//                fragmentManager.popBackStack()
//            }
//        }
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        outPersistentState?.putString("say", "123")
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
        if (persistentState != null) {
            Log.v("Say", persistentState.getString("say"))
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
            val builder = AlertDialog.Builder(this)
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
    fun popFragmentAt(string: String)
    fun goHome()
    fun goTo(fragment: Class<androidx.fragment.app.Fragment>)
}
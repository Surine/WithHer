package com.surine.withher.Activity

import android.os.Bundle
import android.preference.PreferenceFragment
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.surine.withher.Fragment.MainFragment
import com.surine.withher.R


/**
 * Created by surine on 2017/4/8.
 * the settingActivity extends baseactivity
 * in order to solve the problem about toolbar does not show on the window
 */


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //set v7 toolbar
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        setTitle(R.string.app_name)

        replaceFragment(MainFragment())

    }


    //overload
    private fun replaceFragment(prefFragment: PreferenceFragment) {
        val fragmentManager = fragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.setting_fragment, prefFragment)
        transaction.commit()
    }

    //overload
    private fun replaceFragment(fragment: Fragment) {
        val fm = supportFragmentManager
        val tran = fm.beginTransaction()
        tran.replace(R.id.setting_fragment, fragment)
        tran.commit()
    }

}

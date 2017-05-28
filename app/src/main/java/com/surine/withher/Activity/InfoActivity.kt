package com.surine.withher.Activity

import android.os.Bundle
import android.preference.PreferenceFragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.surine.withher.Fragment.ChatInfoFragment
import com.surine.withher.R

class InfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //set v7 toolbar
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        setTitle(R.string.app_name)

        replaceFragment(ChatInfoFragment())

    }


    //overload
    private fun replaceFragment(prefFragment: PreferenceFragment) {
        val fragmentManager = fragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.setting_fragment, prefFragment)
        transaction.commit()
    }

}

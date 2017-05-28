package com.surine.withher.Fragment

import android.content.Intent
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import com.surine.withher.Activity.InfoActivity
import com.surine.withher.R
import com.surine.withher.kotlin_ui.listdialog

/**
 * Created by surine on 2017/5/24.
 */

class MainFragment : PreferenceFragment(), Preference.OnPreferenceClickListener {
    var chatWidget: Preference? = null
    var about: Preference? = null
    //val: this is the menu
    val items = arrayOf<CharSequence>("关于", "Github")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.list)

        chatWidget = findPreference("chatWidget")
        about = findPreference("about")
        chatWidget!!.onPreferenceClickListener = this
        about!!.onPreferenceClickListener = this
    }

    override fun onPreferenceClick(preference: Preference): Boolean {
        if (preference === chatWidget) {
            startActivity(Intent(activity, InfoActivity::class.java))
        } else if (preference === about) {
            listdialog(items,"关于")
        }
        return false
    }
}

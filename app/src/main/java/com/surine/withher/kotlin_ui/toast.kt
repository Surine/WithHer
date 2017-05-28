package com.surine.withher.kotlin_ui

import android.app.Fragment
import android.widget.Toast

/**
 * Created by surine on 2017/5/28.
 * a fragment toast
 */
fun Fragment.toast(message:CharSequence, duration:Int= Toast.LENGTH_SHORT){
    Toast.makeText(activity,message,duration).show()
}

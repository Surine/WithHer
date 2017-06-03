package com.surine.withher.kotlin_ui

import android.app.AlertDialog
import android.app.Fragment
import android.content.Intent
import android.net.Uri
import com.surine.withher.R

/**
 * Created by surine on 2017/5/28.
 */

//normal dialog
fun Fragment.dialog(message:CharSequence, title: CharSequence){
    val builder = AlertDialog.Builder(activity);
    builder.setTitle(title).setMessage(message).setPositiveButton("确定",null).show()
}

//list dialog
fun Fragment.listdialog(items: Array<CharSequence>, title: String) {

    val builder = AlertDialog.Builder(activity);
    builder.setItems(items) { dialog, i ->
        if(i==1){
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://github.com/surine/")))
        }else if(i == 0){
            dialog(getString(R.string.about),"关于")
        }
    }.show()

}
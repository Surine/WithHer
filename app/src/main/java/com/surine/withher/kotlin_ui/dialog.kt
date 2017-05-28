package com.surine.withher.kotlin_ui

import android.app.AlertDialog
import android.app.Fragment
import android.content.Intent
import android.net.Uri

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
            dialog("经过第一版本的反馈，本版本已经修改了气泡，头像和一些奇怪的bug" +
                    "也兼容到了Android4.0，全部代码转换成Kotlin语言开发，如果在使用中" +
                    "遇到了什么问题请反馈给我。","关于")
        }
    }.show()

}
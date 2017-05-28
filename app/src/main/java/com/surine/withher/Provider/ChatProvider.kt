package com.surine.withher.Provider

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.RemoteViews
import com.surine.withher.Activity.InfoActivity
import com.surine.withher.R
import com.surine.withher.kotlin_io_util.my_getBitmap

/**
 * Created by surine on 2017/5/24.
 */

class ChatProvider : AppWidgetProvider() {

    // onUpdate() 在更新 widget 时，被执行，
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }


    // 当 widget 被初次添加 或者 当 widget 的大小被改变时，被调用
    override fun onAppWidgetOptionsChanged(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int, newOptions: Bundle) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
        Log.e("MyProvider", "onAppWidgetOptionsChanged")

    }

    // widget被删除时调用
    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        super.onDeleted(context, appWidgetIds)
        Log.e("MyProvider", "onDeleted")
    }

    // 第一个widget被创建时调用
    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        Log.e("MyProvider", "onEnabled")
    }

    // 最后一个widget被删除时调用
    override fun onDisabled(context: Context) {
        super.onDisabled(context)
        Log.e("MyProvider", "onDisabled")
    }

    // 接收广播的回调函数
    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        val remoteViews = RemoteViews(context.packageName, R.layout.widget_chat_layout)
        val manager = AppWidgetManager.getInstance(context)
        val my_intent = Intent(context,
                InfoActivity::class.java)
        val pendingintent = PendingIntent
                .getActivity(context, 0, my_intent, 0)
        remoteViews.setOnClickPendingIntent(R.id.layout,
                pendingintent)
        val prefs = PreferenceManager
                .getDefaultSharedPreferences(context)
        remoteViews.setImageViewBitmap(R.id.left_head, my_getBitmap(context,"boy.jpeg"))
        remoteViews.setImageViewBitmap(R.id.right_head,my_getBitmap(context,"girl.jpeg"))
        remoteViews.setTextViewText(R.id.left_msg, prefs.getString("boy_say", context.getString(R.string.normal)))
        remoteViews.setTextViewText(R.id.right_msg, prefs.getString("girl_say", context.getString(R.string.normal2)))
        // 以上几行相当于调用setOnClickListener方法，在点击时启动Main这个Activity


        //更新小部件
        manager.updateAppWidget(ComponentName(context, ChatProvider::class.java), remoteViews)

    }

    companion object {
        var MY_UPDATA_CHANGE = "com.surine.WithHer.MyProvider.MY_UPDATA_CHANGE"
    }

}

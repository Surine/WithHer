package com.surine.withher.Provider

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.RemoteViews
import com.surine.withher.Activity.MainActivity
import com.surine.withher.R
import com.surine.withher.kotlin_io_util.my_getBitmap
import com.surine.withher.kotlin_ui.toast


/**
 * Created by surine on 2017/5/24.
 */

class ChatProvider : AppWidgetProvider() {

    // onUpdate()
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        //get the  appwidgetids size
        val N = appWidgetIds.size;
        // Perform this loop procedure for each App Widget that belongs to this provider
        for (i in 0..N - 1) {
            val appWidgetId = appWidgetIds[i]

            // Create an Intent to launch ExampleActivity
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

            // Get the layout for the App Widget and attach an on-click listener
            // to the layout
            val views = RemoteViews(context.packageName, R.layout.widget_chat_layout)
            views.setOnClickPendingIntent(R.id.right_head, pendingIntent)

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
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
        val prefs = PreferenceManager
                .getDefaultSharedPreferences(context)

//        val drawable = GradientDrawable()
//        drawable.cornerRadius = 5f
//        drawable.setStroke(1, Color.parseColor("#cccccc"))
//        drawable.setColor(Color.parseColor("#eeeeee"))

        try {
            remoteViews.setInt(R.id.left_msg,"setBackgroundColor", Color.parseColor(prefs.getString("left_color","#50000000")));
            remoteViews.setInt(R.id.right_msg,"setBackgroundColor", Color.parseColor(prefs.getString("right_color","#50000000")));
            remoteViews.setTextColor(R.id.left_msg,Color.parseColor(prefs.getString("left_text_color","#50000000")));
            remoteViews.setTextColor(R.id.right_msg,Color.parseColor(prefs.getString("right_text_color","#50000000")));
        } catch(e: Exception) {
            toast(context,"颜色代码错误！")
        }
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

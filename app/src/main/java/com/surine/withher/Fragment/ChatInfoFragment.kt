package com.surine.withher.Fragment

import android.app.Activity.RESULT_CANCELED
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.preference.EditTextPreference
import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.PreferenceManager
import android.provider.MediaStore
import com.surine.withher.R
import com.surine.withher.kotlin_io_util.my_getBitmap
import com.surine.withher.kotlin_io_util.save_image
import com.surine.withher.kotlin_ui.dialog
import com.surine.withher.kotlin_ui.toast


/**
 * Created by surine on 2017/5/24.
 */

class ChatInfoFragment : PreferenceFragment(), Preference.OnPreferenceClickListener, SharedPreferences.OnSharedPreferenceChangeListener {
    private var boy_head: Preference? = null
    private var girl_head: Preference? = null
    private var boy_say: EditTextPreference? = null
    private var girl_say: EditTextPreference? = null
    private var create: Preference? = null
    private var more: Preference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.chat)
        activity.title = "情侣聊天小部件"
        findview()   //initview
        setLinsener()   //setlinstener
    }

    private fun setLinsener() {
        boy_head!!.onPreferenceClickListener = this
        girl_head!!.onPreferenceClickListener = this
        boy_say!!.onPreferenceClickListener = this
        girl_say!!.onPreferenceClickListener = this
        more!!.onPreferenceClickListener = this
        create!!.onPreferenceClickListener = this
    }

    private fun findview() {
        boy_head = findPreference("boy_head")
        girl_head = findPreference("girl_head")
        boy_say = findPreference("boy_say") as EditTextPreference
        girl_say = findPreference("girl_say") as EditTextPreference
        more = findPreference("more")
        create = findPreference("create")
        val prefs = PreferenceManager
                .getDefaultSharedPreferences(activity)
        boy_say!!.summary = prefs.getString("boy_say", "")
        girl_say!!.summary = prefs.getString("girl_say", "")

        try {
            boy_head!!.icon = BitmapDrawable(my_getBitmap(activity,"boy.jpeg"))
            girl_head!!.icon = BitmapDrawable(my_getBitmap(activity,"girl.jpeg"))
        } catch(e: Exception) {
        }
    }

    override fun onPreferenceClick(preference: Preference): Boolean {
        if (preference === boy_head) {
            choose_picture(1)
        } else if (preference === girl_head) {
            choose_picture(0)
        } else if (preference === more) {
            toast("暂时没有更多")
        } else if (preference === create) {
            dialog(getString(R.string.info),"使用说明")
        }

        return false
    }



    private fun choose_picture(i: Int) {
         val intent = Intent(Intent.ACTION_PICK)
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        startActivityForResult(intent, i)
    }


    override fun onResume() {
        super.onResume()
        val sharedPreferences = preferenceScreen.sharedPreferences
        // Set up a listener whenever a key changes  
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        // Unregister the listener whenever a key changes  
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, s: String) {
        if (s == "boy_say") {
            test("boy_say", sharedPreferences)
        } else if (s == "girl_say") {
            test("girl_say", sharedPreferences)
        }
    }

     fun test(say: String, sharedPreferences: SharedPreferences) {
        //get the sharedpreferces
        val get_name = sharedPreferences.getString(say, "")
        if (get_name == "") {
            toast("啥都没写呢还")
        } else {
            if (say == "boy_say") {
                boy_say!!.summary = get_name
            } else {
                girl_say!!.summary = get_name
            }
        }

        sendBorad()
    }

    //send the borad to update the widget
    private fun sendBorad() {
        val intent = Intent("com.surine.WithHer.MyProvider.MY_UPDATA_CHANGE")
        activity.sendBroadcast(intent)
    }


    //onactivityresult
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if(resultCode != RESULT_CANCELED) {
            when (requestCode) {
                1 -> HandLeImage(data!!, 1)   //show the  picture
                0 -> HandLeImage(data!!, 0)   //show the  picture
                3 -> save(GetBitMap(data!!), 3) //get bitmap and save
                2 -> save(GetBitMap(data!!), 2) //get bitmap and save
            }
        }
    }

    private fun save(getBitMap: Bitmap, i: Int){
        var filename: String? = null
        if (i == 3){
            filename = "boy.jpeg"
            save_image(filename,getBitMap)
        }else{
            filename = "girl.jpeg"
            save_image(filename,getBitMap)
        }
        sendBorad()
        changeShow(filename!!)
    }


    private fun  changeShow(filename: String) {
        if(filename.contains("boy"))
            //set the boy head icon show (use bitmapdrawable to change the bitmap to drawable)
            boy_head!!.icon = BitmapDrawable(my_getBitmap(activity,filename))
        else
            girl_head!!.icon = BitmapDrawable(my_getBitmap(activity,filename))
    }


    private fun GetBitMap(data: Intent?): Bitmap {
        // return data (crop)
        try {
            val bitmap = data!!.getParcelableExtra<Bitmap>("data")
            return bitmap
        } catch(e: Exception) {
            return Bitmap.createBitmap(200,200,Bitmap.Config.ARGB_8888)
        }
    }


    private fun HandLeImage(data: Intent?, i: Int) {
        // 从相册返回的数据
        if (data != null) {
            // get uri
            val uri = data.data
            //crop method
            cropPic(uri,i)
        }

    }

    private fun cropPic(uri: Uri, i: Int) {
        // 裁剪图片意图
        val intent = Intent("com.android.camera.action.CROP")
        intent.setDataAndType(uri, "image/*")
        intent.putExtra("crop", "true")
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 4)
        intent.putExtra("aspectY", 4)
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 200)
        intent.putExtra("outputY", 200)
        intent.putExtra("outputFormat", "JPEG")// 图片格式
        intent.putExtra("noFaceDetection", true)// 取消人脸识别
        intent.putExtra("return-data", true)
        // 开启一个带有返回值的Activity，
        if(i == 1)
        startActivityForResult(intent,3)
        else startActivityForResult(intent,2)
    }

}

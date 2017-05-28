package com.surine.withher.kotlin_io_util

import android.app.Fragment
import android.content.Context
import android.graphics.*
import java.io.*



/**
 * Created by surine on 2017/5/28.
 */
fun Fragment.save_image(filename: String, oldbitMap: Bitmap) {
    //the method to save the choose iamge
    var bitMap:Bitmap = isCircle(oldbitMap)
    var fOut: FileOutputStream? = null
    val f = File(activity.getFilesDir(),filename)
    try {
        f.createNewFile();
        fOut = FileOutputStream(f);
        bitMap.compress(Bitmap.CompressFormat.PNG, 80, fOut);
        fOut.flush();
        fOut.close();
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

fun  isCircle(oldbitMap: Bitmap): Bitmap {
    var length = if (oldbitMap.getWidth() < oldbitMap.getHeight()) oldbitMap.getWidth() else oldbitMap.getHeight()
    val paint = Paint()
    paint.setAntiAlias(true)
    val target = Bitmap.createBitmap(length, length, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(target)
    canvas.drawCircle((length / 2).toFloat(), (length / 2).toFloat(), (length / 2).toFloat(), paint)
    paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN))
    canvas.drawBitmap(oldbitMap,0.0f,0.0f,paint)
    return target
}


fun  my_getBitmap(activity: Context, filename: String): Bitmap? {
    //the method to get the file and change it to bitmap
    val bitmap: Bitmap?
    try {
        val string =activity.getFilesDir().toString()+"/"+filename;
        val fis = FileInputStream(string);
        bitmap = BitmapFactory.decodeStream(fis)
        return bitmap
    } catch(e: Exception) {
        return Bitmap.createBitmap(200,200,Bitmap.Config.ARGB_8888)
    }
}

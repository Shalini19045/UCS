package com.iiitd.ucsf.threads

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
import android.util.Log
import android.widget.ArrayAdapter
import com.bumptech.glide.load.engine.bitmap_recycle.IntegerArrayAdapter
import com.iiitd.ucsf.manager.DownloadManager
import com.iiitd.ucsf.utilities.Constants
import com.iiitd.ucsf.utilities.Utilities
import java.io.File
import java.net.URL
import java.util.concurrent.TimeUnit


class LongThread : Runnable {
    var threadNo = 0
    var handler: Handler? = null
    lateinit var fileUrl: String
    lateinit var filename : String
    var cts=Constants()
      constructor() {}
    constructor(  threadNo: Int, fileUrl: String?, handler: Handler?,fileName: String?) {
        this.threadNo = threadNo
        this.handler = handler

         if (fileUrl != null) {
            this.fileUrl = fileUrl
        }

        if (fileName != null) {
            this.filename=fileName
        }
    }

    override fun run() {
        Log.i(TAG, "Starting Thread : $threadNo")
        Looper.prepare();

        //getBitmap(fileUrl)
        val folder = File(Environment.getExternalStorageDirectory().toString() + "/" + "All_Audios")
        if (!folder.exists()) {
            folder.mkdirs()
        }
       // DownloadManager.initDownload( this,fileUrl, folder.absolutePath, filename)
        DownloadManager.initDownload( fileUrl, folder.absolutePath, filename)
        //Thread.sleep(cts.COUNT_OF_DATA.toLong()*10000)
      val value_count = Utilities.getcountOfdata()*1*10000.toLong()
        Log.v("vallll",value_count .toString())

        Thread.sleep(value_count)

        Log.i(TAG, "stopping Thread : $threadNo")
        sendMessage(threadNo, "Thread Completed")
/*
        if(DownloadManager.isThreadCompleted){
          sendMessage(threadNo, "Thread Completed")
            Log.i(TAG, "Thread Completed $threadNo")
        }*/



    }


    private fun sendMessage(what: Int, msg: String?) {
        val message = handler!!.obtainMessage(what, msg)
        message.sendToTarget()
    }

    private fun getBitmap(url: String?): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            // Download Image from URL
            val input = URL(url).openStream()
            // Decode Bitmap
            bitmap = BitmapFactory.decodeStream(input)
            // Do extra processing with the bitmap
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bitmap
    }

    companion object {
        const val TAG = "LongThread"
    }
}
package com.quixom.apps.deviceinfo.service

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.Service
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.IBinder
import android.support.annotation.Nullable
import android.widget.RemoteViews
import com.quixom.apps.deviceinfo.R
import com.quixom.apps.deviceinfo.Widget.MyWidgetProvider
import com.quixom.apps.deviceinfo.utilities.Methods
import java.text.DecimalFormat
import java.util.*


/**
 * Created by D46 on 3/26/2018.
 */

class AppWidgetService : Service() {
    var totalRamValue: Long? = null
    var freeRamValue: Long? = null
    var usedRamValue: Long? = null

    @Nullable
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val appWidgetManager: AppWidgetManager = AppWidgetManager.getInstance(this@AppWidgetService)
        val allWidgetIds = intent?.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS)

        val timer = Timer()
        val hourlyTask = object : TimerTask() {
            override fun run() {
                val cm: ConnectivityManager = this@AppWidgetService.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                showRAMUsage()
                val view = RemoteViews(packageName, R.layout.widget_layout)

                view.setTextViewText(R.id.tv_free_memory, "Free: " + formatSize(freeRamValue!!))
                view.setTextViewText(R.id.tv_total_memory, "Total: " + formatSize(totalRamValue!!))
                view.setTextViewText(R.id.tv_used_memory, "Used: " + formatSize(usedRamValue!!))
                view.setProgressBar(R.id.widgetProgressBar, 100, Methods.calculatePercentage(usedRamValue!!.toDouble(), totalRamValue!!.toDouble()).toFloat().toInt(), false)

                val theWidget = ComponentName(this@AppWidgetService, MyWidgetProvider::class.java!!)
                val manager = AppWidgetManager.getInstance(this@AppWidgetService)
                manager.updateAppWidget(theWidget, view)

            }
        }
        // schedule the task to run starting now and then every half-an-hour...
        timer.schedule(hourlyTask, 0L, 1000)

        return START_STICKY

    }

    @SuppressLint("SetTextI18n")
    private fun showRAMUsage() {
        totalRamValue = totalRamMemorySize()
        freeRamValue = freeRamMemorySize()
        usedRamValue = totalRamValue!! - freeRamValue!!

    }

    private fun totalRamMemorySize(): Long {
        val mi = ActivityManager.MemoryInfo()
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.getMemoryInfo(mi)
        return mi.totalMem
    }

    private fun freeRamMemorySize(): Long {
        val mi = ActivityManager.MemoryInfo()
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.getMemoryInfo(mi)
        return mi.availMem
    }

    private fun formatSize(size: Long): String {
        if (size <= 0)
            return "0"
        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        val digitGroups = (Math.log10(size.toDouble()) / Math.log10(1024.0)).toInt()
        return DecimalFormat("#,##0.#").format(size / Math.pow(1024.0, digitGroups.toDouble())) + " " + units[digitGroups]
    }

}
package com.quixom.apps.deviceinfo.Widget

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.quixom.apps.deviceinfo.R
import com.quixom.apps.deviceinfo.Splash
import com.quixom.apps.deviceinfo.service.AppWidgetService
import java.text.DecimalFormat


/**
 * Created by D46 on 3/23/2018.
 */

class MyWidgetProvider : AppWidgetProvider() {
    var totalRamValue: Long? = null
    var freeRamValue: Long? = null
    var usedRamValue: Long? = null
    private var pendingIntent: PendingIntent? = null

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager,
                          appWidgetIds: IntArray) {
        updateAppWidget(context, appWidgetManager)

              /*   val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
           val i = Intent(context, AppWidgetService::class.java)

           if (pendingIntent == null) {
               pendingIntent = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT)
           }
           manager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), 60000, pendingIntent)*/

        /* showRAMUsage(context)

         // Get all ids
         val thisWidget = ComponentName(context,
                 MyWidgetProvider::class.java)
         val allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget)
         for (widgetId in allWidgetIds) {


             val remoteViews = RemoteViews(context.packageName,
                     R.layout.widget_layout)

             // Set the text
             remoteViews.setTextViewText(R.id.tv_free_memory, "Free: " + formatSize(freeRamValue!!))
             remoteViews.setTextViewText(R.id.tv_total_memory, "Total: " + formatSize(totalRamValue!!))
             remoteViews.setTextViewText(R.id.tv_used_memory, "Used: " + formatSize(usedRamValue!!))

             // Register an onClickListener
             val intent = Intent(context, MyWidgetProvider::class.java)

             intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
             intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds)

             val pendingIntent = PendingIntent.getBroadcast(context,
                     0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
                         remoteViews.setOnClickPendingIntent(R.id.update, pendingIntent);
             appWidgetManager.updateAppWidget(widgetId, remoteViews)

             *//* val splashIntent = Intent(context, Splash::class.java)
             val pendingIntent = PendingIntent.getActivity(context, 0, splashIntent, 0)
             remoteViews.setOnClickPendingIntent(R.id.flwidgetParent, pendingIntent)*//*
        }*/
    }

    @SuppressLint("SetTextI18n")
    private fun showRAMUsage(context: Context) {
        totalRamValue = totalRamMemorySize(context)
        freeRamValue = freeRamMemorySize(context)
        usedRamValue = totalRamValue!! - freeRamValue!!

    }

    private fun totalRamMemorySize(context: Context): Long {
        val mi = ActivityManager.MemoryInfo()
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.getMemoryInfo(mi)
        return mi.totalMem
    }

    private fun freeRamMemorySize(context: Context): Long {
        val mi = ActivityManager.MemoryInfo()
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
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

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        val appWidgetManager = AppWidgetManager.getInstance(context?.applicationContext)
        if (appWidgetManager != null) {
            updateAppWidget(context, appWidgetManager)
        }
    }

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager?) {
        val thisWidget = ComponentName(context, MyWidgetProvider::class.java)
        val allWidgetIds = appWidgetManager?.getAppWidgetIds(thisWidget)
        val remoteViews = RemoteViews(context?.packageName, R.layout.widget_layout)

        if (allWidgetIds != null && allWidgetIds.isNotEmpty()) {
                val intent = Intent(context?.applicationContext, AppWidgetService::class.java)
                intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds)

                context?.stopService(intent)
                context?.startService(intent)

            val splashIntent = Intent(context, Splash::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, splashIntent, 0)
            remoteViews.setOnClickPendingIntent(R.id.flwidgetParent, pendingIntent)

            for (widgetId in allWidgetIds) {
                appWidgetManager.updateAppWidget(widgetId, remoteViews)
            }
        }
    }
}

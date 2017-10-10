package com.quixom.apps.deviceinfo.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.content.IntentFilter

import android.content.BroadcastReceiver
import android.content.Context
import android.support.annotation.Nullable
import com.quixom.apps.deviceinfo.utilities.BatteryStats
import java.util.concurrent.TimeUnit


/**
 * Created by akif on 10/6/17.
 */
abstract class BatteryTimeService: Service() {

    /**
     * Lists to store the tracked data for calculating charging and discharging time.
     */
    private var batteryDischargingTimes: ArrayList<Long>? = null
    private var batteryChargingTimes:ArrayList<Long>? = null
    /**
     * A broadcast receiver for tracking the level changes and the battery usage.
     */
    private val levelReceiver = object : BroadcastReceiver() {
        internal var oldDischargeLevel = 101
        internal var oldChargeLevel = 0
        internal var oldDischargeTime: Long = 0
        internal var oldChargeTime: Long = 0

        override fun onReceive(context: Context, intent: Intent) {

            val batteryStats = BatteryStats(intent)
            val charging = batteryStats.isCharging()
            val level = batteryStats.getLevel()

            if (!charging && level <= 100) {

                if (level < oldDischargeLevel) {

                    val time = System.currentTimeMillis()
                    if (oldDischargeTime != 0L) {
                        val diffTime = time - oldDischargeTime
                        batteryDischargingTimes!!.add(diffTime)
                        publishDischargingText(level)
                    } else {
                        onCalculatingDischargingTime()
                    }
                    oldDischargeTime = time
                    oldDischargeLevel = level
                }

                batteryChargingTimes!!.clear()
                oldChargeLevel = 0
                oldChargeTime = 0

            }

            if (charging) {

                if (oldChargeLevel < level) {

                    val time = System.currentTimeMillis()
                    if (oldChargeTime != 0L) {
                        val diffTime = time - oldChargeTime
                        batteryChargingTimes!!.add(diffTime)
                        publishChargingText(level)
                    } else {
                        onCalculatingChargingTime()
                    }
                    oldChargeTime = time
                    oldChargeLevel = level

                }

                if (level == 100) {
                    onFullBattery()
                }

                batteryDischargingTimes!!.clear()
                oldDischargeLevel = 100
                oldDischargeTime = 0

            }

        }
    }

    /**
     * Method called when the charging time is calculated.
     * The {@param hours} and {@param mins} remaining for full charge.
     */
    protected abstract fun onChargingTimePublish(hours: Int, mins: Int)

    /**
     * Method called when the charging time is being calculated.
     * Any status text indicating that "charging time is being calculated" can be used here.
     */
    protected abstract fun onCalculatingChargingTime()

    /**
     * Method called when the charging time is calculated.
     * The {@param days} , {@param hours} and {@param mins} remaining for the battery to drain.
     */
    protected abstract fun onDischargeTimePublish(days: Int, hours: Int, mins: Int)

    /**
     * Method called when the discharging time is being calculated.
     * Any status text indicating that "discharging time is being calculated" can be used here.
     */
    protected abstract fun onCalculatingDischargingTime()

    /**
     * Method called when the battery is fully charged.
     * Called only when the device is connected to charger and battery becomes full.
     */
    protected abstract fun onFullBattery()

    /**
     * Start the calculation when the activity is created.
     */
    override fun onCreate() {
        super.onCreate()

        batteryDischargingTimes = ArrayList()
        batteryChargingTimes = ArrayList()

        registerReceiver(levelReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

    }

    /**
     * Method to calculate the charging time based on the timely usage.
     */
    private fun publishChargingText(level: Int) {

        val average: Long
        var sum: Long = 0
        for (time in batteryChargingTimes!!) {

            sum += time!!

        }
        average = sum / batteryChargingTimes!!.size * (100 - level)

        //Since charging cannot take days
        //int days = (int) TimeUnit.MILLISECONDS.toDays(average);
        val hours = (TimeUnit.MILLISECONDS.toHours(average) % TimeUnit.DAYS.toHours(1)) as Int
        val mins = (TimeUnit.MILLISECONDS.toMinutes(average) % TimeUnit.HOURS.toMinutes(1)) as Int

        onChargingTimePublish(hours, mins)

    }

    /**
     * Method to calculate the discharging time based on the timely usage.
     */
    private fun publishDischargingText(level: Int) {

        val average: Long
        var sum: Long = 0
        for (time in batteryDischargingTimes!!) {

            sum += time

        }
        average = sum / batteryDischargingTimes!!.size * level

        val days = TimeUnit.MILLISECONDS.toDays(average) as Int
        val hours = (TimeUnit.MILLISECONDS.toHours(average) % TimeUnit.DAYS.toHours(1)) as Int
        val mins = (TimeUnit.MILLISECONDS.toMinutes(average) % TimeUnit.HOURS.toMinutes(1)) as Int

        onDischargeTimePublish(days, hours, mins)

    }

    /**
     * Returns START_STICKY by default. Method can be overridden to customize.
     */
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return Service.START_STICKY
    }

    /**
     * Unregister the level receiver onDestroy of the service to avoid leakage.
     */
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(levelReceiver)
    }

    /**
     * Binding not required by default. Can be overridden for binding.
     */
    @Nullable
    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}
package com.quixom.apps.deviceinfo.utilities

import android.content.Intent
import android.os.BatteryManager

/**
 * Created by akif on 10/6/17.
 */
class BatteryStats {
    /**
     * private instance to handle the battery intent.
     */
    private var batteryIntent: Intent? = null

    /**
     * Default constructor is not accessible.
     */
    private fun BatteryStats(){}


    /**
     * Constructor with Intent as parameter.
     *
     *
     * {@param batteryIntent} Returned when registering a null receiver with a Intent filter ( Changes listened once )
     * or an intent from onReceive of broadcast receiver.
     */
    constructor(intent: Intent) {
        this.batteryIntent = intent
    }


    /**
     * Method to check whether device is charging.
     */
    fun isCharging(): Boolean {

        val plugState = batteryIntent?.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)

        return plugState == BatteryManager.BATTERY_PLUGGED_AC ||
                plugState == BatteryManager.BATTERY_PLUGGED_USB || plugState == BatteryManager.BATTERY_PLUGGED_WIRELESS
    }

    /**
     * Method to get the battery level.
     */
    fun getLevel(): Int = batteryIntent!!.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
}
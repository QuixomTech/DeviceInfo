package com.example.akif.devinfo.Adapters

import android.hardware.Sensor
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.akif.devinfo.MainActivity
import com.example.akif.devinfo.R
import com.example.akif.devinfo.fragments.SensorDetailFragment
import com.example.akif.devinfo.models.SensorDATA
import kotlinx.android.synthetic.main.row_sensors.view.*
import kotlinx.android.synthetic.main.sensor_sub_view.view.*
import org.jetbrains.anko.imageResource
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.example.akif.devinfo.utilities.Methods
import java.io.ByteArrayOutputStream


/**
 * Created by akif on 9/26/17.
 */

class SensorAdaptor(internal var c: MainActivity, internal var sensorList: ArrayList<SensorDATA>) : RecyclerView.Adapter<SensorAdaptor.SensorVH>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SensorAdaptor.SensorVH? {
        var v = LayoutInflater.from(c).inflate(R.layout.row_sensors, parent, false)
        return SensorVH(v)
    }

    override fun onBindViewHolder(holder: SensorAdaptor.SensorVH?, position: Int) {
        holder?.bindData(sensorList[position], c)
    }

    override fun getItemCount(): Int {
        return sensorList.size
    }

    class SensorVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(sensorInfo: SensorDATA, c: MainActivity) {

            val tvSensorNameRow: TextView? = itemView.findViewById(R.id.tv_sensor_name_row)
            val ivSensorImage: ImageView? = itemView.findViewById(R.id.iv_sensor_image)

            tvSensorNameRow?.text = sensorInfo.sensorName

            if (sensorInfo.sensorType == Sensor.TYPE_ACCELEROMETER || sensorInfo.sensorType == Sensor.TYPE_ACCELEROMETER_UNCALIBRATED ||
                    sensorInfo.sensorType == Sensor.TYPE_LINEAR_ACCELERATION) {
                ivSensorImage?.setImageResource(R.drawable.ic_if_speedometer_172557)
            } else if (sensorInfo.sensorType == Sensor.TYPE_LIGHT) {
                ivSensorImage?.setImageResource(R.drawable.ic_if_vector_icons_81_1041639)
            } else if (sensorInfo.sensorType == Sensor.TYPE_STEP_COUNTER || sensorInfo.sensorType == Sensor.TYPE_STEP_DETECTOR) {
                ivSensorImage?.setImageResource(R.drawable.ic_if_running_172541)
            } else if (sensorInfo.sensorType == Sensor.TYPE_ROTATION_VECTOR || sensorInfo.sensorType == Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR
                    || sensorInfo.sensorType == Sensor.TYPE_GAME_ROTATION_VECTOR) {
                ivSensorImage?.setImageResource(R.drawable.ic_if_screen_rotation_326583)
            } else if (sensorInfo.sensorType == Sensor.TYPE_GRAVITY) {
                ivSensorImage?.setImageResource(R.drawable.ic_if_earth1_216620)
            } else if (sensorInfo.sensorType == Sensor.TYPE_MAGNETIC_FIELD) {
                ivSensorImage?.setImageResource(R.drawable.ic_inclined_magnet)
            } else if (sensorInfo.sensorType == Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED) {
                ivSensorImage?.setImageResource(R.drawable.ic_magnet_with_bolt)
            } else if (sensorInfo.sensorType == Sensor.TYPE_PROXIMITY) {
                ivSensorImage?.setImageResource(R.drawable.ic_if_ibeacon_proximity_1613770)
            } else if (sensorInfo.sensorType == Sensor.TYPE_ORIENTATION) {
                ivSensorImage?.setImageResource(R.drawable.ic_if_camping_nature_10_808486)
            } else if (sensorInfo.sensorType == Sensor.TYPE_GYROSCOPE || sensorInfo.sensorType == Sensor.TYPE_GYROSCOPE_UNCALIBRATED) {
                ivSensorImage?.setImageResource(R.drawable.ic_worldwide_communications)
            } else if (sensorInfo.sensorType == Sensor.TYPE_PRESSURE) {
                ivSensorImage?.setImageResource(R.drawable.ic_if_pressure)
            } else if (sensorInfo.sensorType == Sensor.TYPE_RELATIVE_HUMIDITY) {
                ivSensorImage?.setImageResource(R.drawable.ic_humidity)
            } else if (sensorInfo.sensorType == Sensor.TYPE_AMBIENT_TEMPERATURE) {
                ivSensorImage?.setImageResource(R.drawable.ic_temperature)
            } else if (sensorInfo.sensorType == Sensor.TYPE_HEART_BEAT || sensorInfo.sensorType == Sensor.TYPE_HEART_RATE) {
                ivSensorImage?.setImageResource(R.drawable.ic_cardiogram)
            } else {
                ivSensorImage?.setImageResource(R.drawable.ic_speedometer)
            }

            itemView.setOnClickListener({
                val sensorDetailFragment = SensorDetailFragment()
                val drawable = ivSensorImage?.drawable
                val bitmap: Bitmap? = Methods.getBitmapFromVectorDrawable(c, drawable)

                val stream = ByteArrayOutputStream()
                bitmap?.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val byteArray = stream.toByteArray()

                c.fragmentUtil.addFragment(sensorDetailFragment.getInstance(sensorInfo.sensorName, sensorInfo.sensorType, byteArray), true, true)
            })
        }
    }
}
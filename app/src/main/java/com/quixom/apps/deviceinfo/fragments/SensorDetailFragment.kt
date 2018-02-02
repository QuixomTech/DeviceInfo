package com.quixom.apps.deviceinfo.fragments

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.quixom.apps.deviceinfo.R
import com.quixom.apps.deviceinfo.utilities.KeyUtil
import com.quixom.apps.deviceinfo.utilities.Methods
import java.text.DecimalFormat
import android.graphics.BitmapFactory



class SensorDetailFragment : BaseFragment(), SensorEventListener {

    var ivMenuSensor: ImageView? = null
    var ivBack: ImageView? = null
    var tvTitle: TextView? = null
    var ivSensorIC: ImageView? = null
    var tvXAxis: TextView? = null
    var tvYAxis: TextView? = null
    var tvZAxis: TextView? = null
    var tvSensorName: TextView? = null
    var tvIntType: TextView? = null
    var tvVendor: TextView? = null
    var tvVersion: TextView? = null
    var tvResolution: TextView? = null
    var tvPower: TextView? = null
    var tvMaximumRange: TextView? = null
    var tvSensorId: TextView? = null
    var tvIsDynamic: TextView? = null
    var tvIsWakeUpSensor: TextView? = null
    var tvReportingMode: TextView? = null
    var llTop: LinearLayout? = null

    var sensorName: String? = ""
    var sensorType: Int? = 0
    var sensorIcon: Int? = 0
    var sensorManager: SensorManager? = null

    fun getInstance(mode: String, type: Int, imageBytes: ByteArray): SensorDetailFragment {
        val sensorDetailFragment = SensorDetailFragment()
        val bundle = Bundle()
        bundle.putString(KeyUtil.KEY_SENSOR_NAME, mode)
        bundle.putInt(KeyUtil.KEY_SENSOR_TYPE, type)
        bundle.putByteArray(KeyUtil.KEY_SENSOR_ICON, imageBytes)
        sensorDetailFragment.arguments = bundle
        return sensorDetailFragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sensors_detail, container, false)

        ivMenuSensor = view.findViewById(R.id.iv_menu)
        ivBack = view.findViewById(R.id.iv_back)
        tvTitle = view.findViewById(R.id.tv_title)
        ivSensorIC = view.findViewById(R.id.iv_sensor_ic)
        tvXAxis = view.findViewById(R.id.tv_x)
        tvYAxis = view.findViewById(R.id.tv_y)
        tvZAxis = view.findViewById(R.id.tv_z)
        tvSensorName = view.findViewById(R.id.tv_sensor_name)
        tvIntType = view.findViewById(R.id.tv_int_type)
        tvVendor = view.findViewById(R.id.tv_vendor)
        tvVersion = view.findViewById(R.id.tv_version)
        tvResolution = view.findViewById(R.id.tv_resolution)
        tvPower = view.findViewById(R.id.tv_power)
        tvMaximumRange = view.findViewById(R.id.tv_maximum_range)
        tvSensorId = view.findViewById(R.id.tv_sensor_id)
        tvIsWakeUpSensor = view.findViewById(R.id.tv_is_wakeup_sensor)
        tvIsDynamic = view.findViewById(R.id.tv_is_dynamic_sensor)
        tvReportingMode = view.findViewById(R.id.tv_reporting_mode)
        llTop = view.findViewById(R.id.ll_top)
        return view
    }

    @TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mActivity.drawerdisable(true)
        getBundleData()
        initToolbar()
        sensorManager = mActivity.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager?.registerListener(this, sensorType?.let { sensorManager?.getDefaultSensor(it) }, SensorManager.SENSOR_DELAY_NORMAL)
        displaySensorsDetails(sensorManager!!)
    }

    override fun onResume() {
        super.onResume()
        sensorManager?.registerListener(this, sensorType?.let { sensorManager?.getDefaultSensor(it) }, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(this)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden && isAdded) {
            initToolbar()
        }
    }

    private fun initToolbar() {
        ivMenuSensor?.visibility = View.GONE
        ivBack?.visibility = View.VISIBLE
        tvTitle?.text = sensorName
        ivBack?.setOnClickListener {
            mActivity.onBackPressed()
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onSensorChanged(sensorEvent: SensorEvent?) {
        sensorEvent?.let { displayAccelerometer(it) }
    }

    /**
     * Get data from bundle
     */
    private fun getBundleData() {
        val bundle = arguments
        if (bundle != null) {
            if (bundle.containsKey(KeyUtil.KEY_SENSOR_NAME)) {
                sensorName = bundle.getString(KeyUtil.KEY_SENSOR_NAME)
                tvSensorName?.text = sensorName
            }
            if (bundle.containsKey(KeyUtil.KEY_SENSOR_TYPE)) {
                sensorType = bundle.getInt(KeyUtil.KEY_SENSOR_TYPE)
            }
            if (bundle.containsKey(KeyUtil.KEY_SENSOR_ICON)) {
                val byteArray: ByteArray? = bundle.getByteArray(KeyUtil.KEY_SENSOR_ICON)
                val bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size)
                if (bmp != null) {
                    ivSensorIC?.setImageBitmap(bmp)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun displayAccelerometer(event: SensorEvent) {
        val formatter = DecimalFormat("#0.00")
        /*** Accelerometer sensors
         * Gravity sensor */
        if (sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.type === sensorType
                || sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER_UNCALIBRATED)?.type === sensorType
                || sensorManager?.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)?.type === sensorType
                || sensorManager?.getDefaultSensor(Sensor.TYPE_GRAVITY)?.type === sensorType) {

            llTop?.visibility = View.VISIBLE

            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            tvXAxis?.text = (Html.fromHtml("X: " + formatter.format(x) + mResources.getString(R.string.ms) + "<small><sup>2</sup></small>"))
            tvYAxis?.text = (Html.fromHtml("Y: " + formatter.format(y) + mResources.getString(R.string.ms) + "<small><sup>2</sup></small>"))
            tvZAxis?.text = (Html.fromHtml("Z: " + formatter.format(z) + mResources.getString(R.string.ms) + "<small><sup>2</sup></small>"))
        }
        /*** Magnetic sensors */
        else if (sensorManager?.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)?.type === sensorType
                || sensorManager?.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED)?.type === sensorType) {

            llTop?.visibility = View.VISIBLE
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            if (sensorManager?.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)?.type === (sensorType)) {
                tvXAxis?.text = ("X: " + formatter.format(x) + mResources.getString(R.string.mu_tesla))
                tvYAxis?.text = ("Y: " + formatter.format(y) + mResources.getString(R.string.mu_tesla))
                tvZAxis?.text = ("Z: " + formatter.format(z) + mResources.getString(R.string.mu_tesla))
            } else if (sensorManager?.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED)?.type === (sensorType)) {
                tvXAxis?.text = (mResources.getString(R.string.geomagnetic_field) + "X: " + formatter.format(x) + mResources.getString(R.string.mu_tesla))
                tvYAxis?.text = ("Y: " + formatter.format(y) + mResources.getString(R.string.mu_tesla))
                tvZAxis?.text = ("Z: " + formatter.format(z) + mResources.getString(R.string.mu_tesla))
            }

        }
        /*** Gyroscope sensors */
        else if (sensorManager?.getDefaultSensor(Sensor.TYPE_GYROSCOPE)?.type === (sensorType)
                || sensorManager?.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED)?.type === (sensorType)) {

            llTop?.visibility = View.VISIBLE
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            tvXAxis?.text = Methods.getSpannableSensorText(mActivity, mResources.getString(R.string.angular_speed) + "X: " + formatter.format(x) + mResources.getString(R.string.rad))
            tvYAxis?.text = ("Y: " + formatter.format(y) + mResources.getString(R.string.rad))
            tvZAxis?.text = ("Z: " + formatter.format(z) + mResources.getString(R.string.rad))

        }
        /*** Rotation sensors */
        else if (sensorManager?.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR)?.type === (sensorType)
                || sensorManager?.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR)?.type === (sensorType)
                || sensorManager?.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR)?.type === (sensorType)
                || sensorManager?.getDefaultSensor(Sensor.TYPE_ORIENTATION)?.type === (sensorType)
                || sensorManager?.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)?.type === (sensorType)) {
            llTop?.visibility = View.VISIBLE
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            if (sensorManager?.getDefaultSensor(Sensor.TYPE_ORIENTATION)?.type === sensorType) {
                tvXAxis?.text = ("X: " + formatter.format(x) + mResources.getString(R.string.degree_icon))
                tvYAxis?.text = ("Y: " + formatter.format(y) + mResources.getString(R.string.degree_icon))
                tvZAxis?.text = ("Z: " + formatter.format(z) + mResources.getString(R.string.degree_icon))
            } else {
                tvXAxis?.text = ("X: " + formatter.format(x))
                tvYAxis?.text = ("Y: " + formatter.format(y))
                tvZAxis?.text = ("Z: " + formatter.format(z))
            }

        }
        /*** Pressure sensor (Barometer) */
        else if (sensorManager?.getDefaultSensor(Sensor.TYPE_PRESSURE)?.type === (sensorType)) {
            tvXAxis?.text = (mResources.getString(R.string.pressure) + event.values[0] + mResources.getString(R.string.hpa))
        }
        /*** Step counter sensor
        Proximity sensor
        Light sensor */
        else if (sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)?.type == sensorType
                || sensorManager?.getDefaultSensor(Sensor.TYPE_PROXIMITY)?.type == sensorType
                || sensorManager?.getDefaultSensor(Sensor.TYPE_LIGHT)?.type == sensorType
                || sensorManager?.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)?.type == sensorType
                || sensorManager?.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)?.type == sensorType) {

            llTop?.visibility = View.VISIBLE

            if (event.sensor.type == Sensor.TYPE_RELATIVE_HUMIDITY && event.values[0] < sensorManager?.getDefaultSensor(Sensor.TYPE_PROXIMITY)?.maximumRange!!) {
                tvXAxis?.text = (mResources.getString(R.string.proximity_sensor) + event.values[0].toString() + mResources.getString(R.string.cm))
            } else {
                tvXAxis?.text = (mResources.getString(R.string.proximity_sensor) + event.values[0].toString() + mResources.getString(R.string.cm))
            }

            if (event.sensor.type == Sensor.TYPE_RELATIVE_HUMIDITY) {
                tvXAxis?.text = (mResources.getString(R.string.humidity_sensor) + event.values[0].toString() + mResources.getString(R.string.percentage))
                KeyUtil.KEY_LAST_KNOWN_HUMIDITY = event.values[0]
            }

            if (event.sensor.type == Sensor.TYPE_LIGHT) {
                tvXAxis?.text = (mResources.getString(R.string.illuminance) + event.values[0].toString() + mResources.getString(R.string.lx))
            }

            if (event.sensor.type == Sensor.TYPE_AMBIENT_TEMPERATURE && KeyUtil.KEY_LAST_KNOWN_HUMIDITY != 0f) {
                val temperature = event.values[0]
                val absoluteHumidity = Methods.calculateAbsoluteHumidity(temperature, KeyUtil.KEY_LAST_KNOWN_HUMIDITY)
                tvXAxis?.text = (mResources.getString(R.string.absolute_humidity_temperature_sensor) + formatter.format(absoluteHumidity) + mResources.getString(R.string.percentage))
                val dewPoint = Methods.calculateDewPoint(temperature, KeyUtil.KEY_LAST_KNOWN_HUMIDITY)
                tvYAxis?.text = (mResources.getString(R.string.due_point_temperature) + formatter.format(dewPoint) + mResources.getString(R.string.percentage))
            }

            if (event.sensor.type == Sensor.TYPE_STEP_COUNTER) {
                tvXAxis?.text = (mResources.getString(R.string.steps) + event.values[0].toString())
            }
        }
        /*** Other sensors */
        else {
            llTop?.visibility = View.VISIBLE
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
    private fun displaySensorsDetails(sensorManager: SensorManager) {
        tvIntType?.text = sensorType?.let { sensorManager.getDefaultSensor(it)?.stringType }
        tvVendor?.text = sensorType?.let { sensorManager.getDefaultSensor(it)?.vendor }
        tvVersion?.text = sensorType?.let { sensorManager.getDefaultSensor(it)?.version.toString() }
        tvResolution?.text = sensorType?.let { Html.fromHtml(sensorManager.getDefaultSensor(it)?.resolution.toString() + " m/s" + "<small><sup>2</sup></small>") }
        tvPower?.text = sensorType?.let { sensorManager.getDefaultSensor(it)?.power.toString() + mResources.getString(R.string.ma) }
        tvMaximumRange?.text = sensorType?.let { Html.fromHtml(sensorManager.getDefaultSensor(it)?.maximumRange.toString() + " m/s" + "<small><sup>2</sup></small>") }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvSensorId?.text = sensorType?.let { sensorManager.getDefaultSensor(it)?.id.toString() }
            tvIsDynamic?.text = sensorType?.let { sensorManager.getDefaultSensor(it)?.isDynamicSensor.toString() }
            tvIsWakeUpSensor?.text = sensorType?.let { sensorManager.getDefaultSensor(it)?.isWakeUpSensor.toString() }
            tvReportingMode?.text = sensorType?.let { sensorManager.getDefaultSensor(it)?.reportingMode.toString() }
        } else {
            tvSensorId?.text = "-"
            tvIsDynamic?.text = "-"
            tvIsWakeUpSensor?.text = "-"
            tvReportingMode?.text = "-"
        }
    }
}
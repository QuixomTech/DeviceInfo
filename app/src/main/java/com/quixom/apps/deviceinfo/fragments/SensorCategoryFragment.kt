package com.quixom.apps.deviceinfo.fragments

import android.annotation.SuppressLint
import android.content.Context.SENSOR_SERVICE
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.quixom.apps.deviceinfo.R
import com.quixom.apps.deviceinfo.adapters.SensorAdaptor
import com.quixom.apps.deviceinfo.models.SensorDATA

class SensorCategoryFragment : BaseFragment() {

    var ivMenu: ImageView? = null
    var ivBack: ImageView? = null
    var tvTitle: TextView? = null

    var coordinateLayout: CoordinatorLayout? = null

    var rvSensorsList: RecyclerView? = null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_sensors_categories, container, false)

        ivMenu = view.findViewById(R.id.iv_menu)
        ivBack = view.findViewById(R.id.iv_back)
        tvTitle = view.findViewById(R.id.tv_title)
        rvSensorsList = view.findViewById(R.id.rv_sensors_list)
        coordinateLayout = view.findViewById(R.id.coordinatorLayout)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initToolbar()
        rvSensorsList?.layoutManager = LinearLayoutManager(mActivity)
        rvSensorsList?.hasFixedSize()
        initSensorsList()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden && isAdded) {
            initToolbar()
            mActivity.drawerdisable(false)
        }
    }

    private fun initToolbar() {
        ivMenu?.visibility = View.VISIBLE
        ivBack?.visibility = View.GONE
        tvTitle?.text = mResources.getString(R.string.sensors)
        ivMenu?.setOnClickListener {
            mActivity.openDrawer()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initSensorsList() {
        val lists = ArrayList<SensorDATA>()

        val sm = mActivity.getSystemService(SENSOR_SERVICE) as SensorManager
        val list = sm.getSensorList(Sensor.TYPE_ALL)

        if (list.size > 1) {
            snackBarCustom(coordinateLayout!!, list.size.toString() + " " + mResources.getString(R.string.available_sensor))
        } else {
            snackBarCustom(coordinateLayout!!, list.size.toString() + " " + mResources.getString(R.string.available_sensor))
        }

        for (s in list) {
            lists.add(SensorDATA(s.name, s.type))
        }

        //creating our adapter
        val adapter = SensorAdaptor(mActivity, lists)

        //now adding the adapter to RecyclerView
        rvSensorsList?.adapter = adapter
    }

    /**
     * Display the Snackbar with the [Snackbar.LENGTH_SHORT] duration.
     *
     * @param message the message text.
     */

    private fun snackBarCustom(view: View, message: String) {
        val mSnackBar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        val view: View? = mSnackBar.view
        val mainTextView = mSnackBar.view.findViewById<View>(android.support.design.R.id.snackbar_text) as TextView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            mainTextView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        else
            mainTextView.gravity = Gravity.CENTER_HORIZONTAL
        mainTextView.gravity = Gravity.CENTER_HORIZONTAL
        mainTextView.setTextColor(Color.WHITE)
        view?.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.colorAccent))


        mSnackBar.show()
    }

}


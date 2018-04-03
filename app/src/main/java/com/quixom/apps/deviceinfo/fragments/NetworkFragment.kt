package com.quixom.apps.deviceinfo.fragments

import android.annotation.SuppressLint
import android.content.Context.WIFI_SERVICE
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import com.quixom.apps.deviceinfo.R
import com.quixom.apps.deviceinfo.utilities.Methods
import kotlinx.android.synthetic.main.fragment_network.*


class NetworkFragment : BaseFragment() {

    var ivMenu: ImageView? = null
    var ivBackNet: ImageView? = null
    var tvTitle: TextView? = null
    var tvConnectionStatus: TextView? = null
    var tvDataType: TextView? = null
    var tvNetworkType: TextView? = null
    var tvIpAddress: TextView? = null
    var tvMACAddress: TextView? = null
    var tvSSID: TextView? = null
    var tvLinkSpeed: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        val view = inflater.inflate(R.layout.fragment_network, container, false)
        val contextThemeWrapper = ContextThemeWrapper(activity, R.style.NetworkTheme)
        val localInflater = inflater.cloneInContext(contextThemeWrapper)
        val view = localInflater.inflate(R.layout.fragment_network, container, false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity!!.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.dark_sky_blue)
            window.navigationBarColor = resources.getColor(R.color.dark_sky_blue)

        }
        ivMenu = view.findViewById(R.id.iv_menu)
        ivBackNet = view.findViewById(R.id.iv_back)
        tvTitle = view.findViewById(R.id.tv_title)
        tvConnectionStatus = view.findViewById(R.id.tv_connection_status)
        tvDataType = view.findViewById(R.id.tv_data_type)
        tvNetworkType = view.findViewById(R.id.tv_network_type)
        tvIpAddress = view.findViewById(R.id.tv_ip_address)
        tvMACAddress = view.findViewById(R.id.tv_mac_address)
        tvSSID = view.findViewById(R.id.tv_ssid)
        tvLinkSpeed = view.findViewById(R.id.tv_link_speed)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initToolbar()
        getNetworkInfo()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden && isAdded) {
            initToolbar()
        }
    }

    private fun initToolbar(): Unit {
        ivMenu?.visibility = View.VISIBLE
        ivBackNet?.visibility = View.GONE
        tvTitle?.text = mResources.getString(R.string.network)
        ivMenu?.setOnClickListener {
            mActivity.openDrawer()
        }
    }

    @SuppressLint("SetTextI18n", "WifiManagerLeak")
    private fun getNetworkInfo(): Unit {

        if (Methods.isNetworkConnected(mActivity)) {
            tvConnectionStatus?.text = mResources.getString(R.string.connect)
            animationView.visibility = View.VISIBLE
            tvIpAddress?.text = Methods.getIPAddress(true)
        } else {
            animationView.visibility = View.GONE
            tvConnectionStatus?.text = mResources.getString(R.string.disconnect)
            tvIpAddress?.text = mResources.getString(R.string.unavailable)
        }

        if (Methods.isWifiConnected(mActivity) == mResources.getString(R.string.wifi)) {
            val wifiManager = mActivity.getSystemService(WIFI_SERVICE) as WifiManager
            val wifiInfo = wifiManager.connectionInfo
            tvDataType?.text = mResources.getString(R.string.wifi)
            tvNetworkType?.text = mResources.getString(R.string.wifi)
            tvSSID?.text = wifiInfo.ssid
            tvMACAddress?.text = Methods.getMACAddress("wlan0")
            tvLinkSpeed?.text = wifiInfo.linkSpeed.toString()+mResources.getString(R.string.mbps)
        }
        else if (Methods.isWifiConnected(mActivity) == mResources.getString(R.string.network)) {
            tvDataType?.text = mResources.getString(R.string.network)
            tvNetworkType?.text = mResources.getString(R.string.network)
            tvSSID?.text = mResources.getString(R.string.unavailable)
            tvMACAddress?.text = Methods.getMACAddress("eth0")
            tvLinkSpeed?.text = mResources.getString(R.string.unavailable)
        }
        else {
            tvDataType?.text = mResources.getString(R.string.unavailable)
            tvNetworkType?.text = mResources.getString(R.string.unavailable)
        }
    }
}
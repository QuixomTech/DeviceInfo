package com.quixom.apps.deviceinfo.fragments

import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import com.quixom.apps.deviceinfo.R
import kotlinx.android.synthetic.main.fragment_phone_features.*

class PhoneFeaturesFragment : BaseFragment() {

    var packageManager: PackageManager? = null

    var ivMenu: ImageView? = null
    var ivBack: ImageView? = null
    var tvTitle: TextView? = null

    var tvWifi: TextView? = null
    var tvWifiDirect: TextView? = null
    var tvBluetooth: TextView? = null
    var tvBluetoothLE: TextView? = null
    var tvGPS: TextView? = null
    var tvCameraFlash: TextView? = null
    var tvCameraFront: TextView? = null
    var tvMicrophone: TextView? = null
    var tvNFC: TextView? = null
    var tvUSBHost: TextView? = null
    var tvUSBAccessory: TextView? = null
    var tvMultitouch: TextView? = null
    var tvAudioLowLatency: TextView? = null
    var tvAudioOutput: TextView? = null
    var tvProfessionalAudio: TextView? = null
    var tvConsumerIR: TextView? = null
    var tvGamepadSupport: TextView? = null
    var tvHIFISensor: TextView? = null
    var tvPrinting: TextView? = null
    var tvCDMA: TextView? = null
    var tvGSM: TextView? = null
    var tvFingerprint: TextView? = null
    var tvAppWidgets: TextView? = null
    var tvSIP: TextView? = null
    var tvSIPBasedVOIP: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        val view = inflater.inflate(R.layout.fragment_phone_features, container, false)
        val contextThemeWrapper = ContextThemeWrapper(activity, R.style.FeaturesTheme)
        val localInflater = inflater.cloneInContext(contextThemeWrapper)
        val view = localInflater.inflate(R.layout.fragment_phone_features, container, false)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity!!.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.dark_brown)
            window.navigationBarColor = resources.getColor(R.color.dark_brown)

        }
        ivMenu = view.findViewById(R.id.iv_menu)
        ivBack = view.findViewById(R.id.iv_back)
        tvTitle = view.findViewById(R.id.tv_title)

        tvWifi = view.findViewById(R.id.tv_wifi)
        tvWifiDirect = view.findViewById(R.id.tv_wifi_direct)
        tvBluetooth = view.findViewById(R.id.tv_bluetooth)
        tvBluetoothLE = view.findViewById(R.id.tv_bluetooth_le)
        tvGPS = view.findViewById(R.id.tv_gps)
        tvCameraFlash = view.findViewById(R.id.tv_camera_flash)
        tvCameraFront = view.findViewById(R.id.tv_camera_front)
        tvMicrophone = view.findViewById(R.id.tv_microphone)
        tvNFC = view.findViewById(R.id.tv_nfc)
        tvUSBHost = view.findViewById(R.id.tv_usb_host)
        tvUSBAccessory = view.findViewById(R.id.tv_usb_accessory)
        tvMultitouch = view.findViewById(R.id.tv_multitouch)
        tvAudioLowLatency = view.findViewById(R.id.tv_audio_low_latency)
        tvAudioOutput = view.findViewById(R.id.tv_audio_output)
        tvProfessionalAudio = view.findViewById(R.id.tv_professional_audio)
        tvConsumerIR = view.findViewById(R.id.tv_consumer_ir)
        tvGamepadSupport = view.findViewById(R.id.tv_gamepad_support)
        tvHIFISensor = view.findViewById(R.id.tv_hifi_sensor)
        tvPrinting = view.findViewById(R.id.tv_printing)
        tvCDMA = view.findViewById(R.id.tv_cdma)
        tvGSM = view.findViewById(R.id.tv_gsm)
        tvFingerprint = view.findViewById(R.id.tv_fingerprint)
        tvAppWidgets = view.findViewById(R.id.tv_app_widgets)
        tvSIP = view.findViewById(R.id.tv_sip)
        tvSIPBasedVOIP = view.findViewById(R.id.tv_sip_based_voip)

        packageManager = mActivity.packageManager
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initToolbar()
        getDeviceFeatures()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden && isAdded) {
            initToolbar()
        }
    }

    private fun initToolbar(): Unit {
        ivMenu?.visibility = View.VISIBLE
        ivBack?.visibility = View.GONE
        tvTitle?.text = mResources.getString(R.string.features)
        ivMenu?.setOnClickListener {
            mActivity.openDrawer()
        }
    }

    private fun getDeviceFeatures(): Unit {
        /** WIFI feature */
        val connManager = mActivity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)

        // WIFI
        if (mWifi.isAvailable) {
            tvWifi?.text = mResources.getString(R.string.available)
        } else {
            tvWifi?.text = mResources.getString(R.string.not_supported)
        }

        // WIFI Direct
        if (packageManager?.hasSystemFeature(PackageManager.FEATURE_WIFI_DIRECT)!!) {
            tvWifiDirect?.text = mResources.getString(R.string.available)
        } else {
            tvWifiDirect?.text = mResources.getString(R.string.not_supported)
        }

        // Bluetooth
        if (packageManager?.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH)!!) {
            tvBluetooth?.text = mResources.getString(R.string.available)
        } else {
            tvBluetooth?.text = mResources.getString(R.string.not_supported)
        }

        // Bluetooth LE
        if (packageManager?.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)!!) {
            tvBluetoothLE?.text = mResources.getString(R.string.available)
        } else {
            tvBluetoothLE?.text = mResources.getString(R.string.not_supported)
        }

        // GPS
        if (packageManager?.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS)!!) {
            tvGPS?.text = mResources.getString(R.string.available)
        } else {
            tvGPS?.text = mResources.getString(R.string.not_supported)
        }

        // Camera Flash
        if (packageManager?.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)!!) {
            tvCameraFlash?.text = mResources.getString(R.string.available)
        } else {
            tvCameraFlash?.text = mResources.getString(R.string.not_supported)
        }

        // Camera Front
        if (packageManager?.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)!!) {
            tvCameraFront?.text = mResources.getString(R.string.available)
        } else {
            tvCameraFront?.text = mResources.getString(R.string.not_supported)
        }

        // Microphone
        if (packageManager?.hasSystemFeature(PackageManager.FEATURE_MICROPHONE)!!) {
            tvMicrophone?.text = mResources.getString(R.string.available)
        } else {
            tvMicrophone?.text = mResources.getString(R.string.not_supported)
        }

        // NFC
        if (packageManager?.hasSystemFeature(PackageManager.FEATURE_NFC)!!) {
            tvNFC?.text = mResources.getString(R.string.available)
        } else {
            tvNFC?.text = mResources.getString(R.string.not_supported)
        }

        // USB Host
        if (packageManager?.hasSystemFeature(PackageManager.FEATURE_USB_HOST)!!) {
            tv_usb_host?.text = mResources.getString(R.string.available)
        } else {
            tv_usb_host?.text = mResources.getString(R.string.not_supported)
        }

        // USB Accessory
        if (packageManager?.hasSystemFeature(PackageManager.FEATURE_USB_ACCESSORY)!!) {
            tv_usb_accessory?.text = mResources.getString(R.string.available)
        } else {
            tv_usb_accessory?.text = mResources.getString(R.string.not_supported)
        }

        // Multitouch
        if (packageManager?.hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH)!!) {
            tvMultitouch?.text = mResources.getString(R.string.available)
        } else {
            tvMultitouch?.text = mResources.getString(R.string.not_supported)
        }

        // Audio low-latency
        if (packageManager?.hasSystemFeature(PackageManager.FEATURE_AUDIO_LOW_LATENCY)!!) {
            tvAudioLowLatency?.text = mResources.getString(R.string.available)
        } else {
            tvAudioLowLatency?.text = mResources.getString(R.string.not_supported)
        }

        // Audio Output
        if (packageManager?.hasSystemFeature(PackageManager.FEATURE_AUDIO_OUTPUT)!!) {
            tvAudioOutput?.text = mResources.getString(R.string.available)
        } else {
            tvAudioOutput?.text = mResources.getString(R.string.not_supported)
        }

        // Professional Audio
        if (packageManager?.hasSystemFeature(PackageManager.FEATURE_AUDIO_PRO)!!) {
            tvProfessionalAudio?.text = mResources.getString(R.string.available)
        } else {
            tvProfessionalAudio?.text = mResources.getString(R.string.not_supported)
        }

        // Consumer IR
        if (packageManager?.hasSystemFeature(PackageManager.FEATURE_CONSUMER_IR)!!) {
            tvConsumerIR?.text = mResources.getString(R.string.available)
        } else {
            tvConsumerIR?.text = mResources.getString(R.string.not_supported)
        }

        // Gamepad Support
        if (packageManager?.hasSystemFeature(PackageManager.FEATURE_GAMEPAD)!!) {
            tvGamepadSupport?.text = mResources.getString(R.string.available)
        } else {
            tvGamepadSupport?.text = mResources.getString(R.string.not_supported)
        }

        // HIFI Sensor
        if (packageManager?.hasSystemFeature(PackageManager.FEATURE_HIFI_SENSORS)!!) {
            tvHIFISensor?.text = mResources.getString(R.string.available)
        } else {
            tvHIFISensor?.text = mResources.getString(R.string.not_supported)
        }

        // Printing
        if (packageManager?.hasSystemFeature(PackageManager.FEATURE_PRINTING)!!) {
            tvPrinting?.text = mResources.getString(R.string.available)
        } else {
            tvPrinting?.text = mResources.getString(R.string.not_supported)
        }

        // CDMA
        if (packageManager?.hasSystemFeature(PackageManager.FEATURE_TELEPHONY_CDMA)!!) {
            tvCDMA?.text = mResources.getString(R.string.available)
        } else {
            tvCDMA?.text = mResources.getString(R.string.not_supported)
        }

        // GSM
        if (packageManager?.hasSystemFeature(PackageManager.FEATURE_TELEPHONY_GSM)!!) {
            tvGSM?.text = mResources.getString(R.string.available)
        } else {
            tvGSM?.text = mResources.getString(R.string.not_supported)
        }

        // Finger-print
        if (packageManager?.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)!!) {
            tvFingerprint?.text = mResources.getString(R.string.available)
        } else {
            tvFingerprint?.text = mResources.getString(R.string.not_supported)
        }

        // App Widgets
        if (packageManager?.hasSystemFeature(PackageManager.FEATURE_APP_WIDGETS)!!) {
            tvAppWidgets?.text = mResources.getString(R.string.available)
        } else {
            tvAppWidgets?.text = mResources.getString(R.string.not_supported)
        }

        // SIP
        if (packageManager?.hasSystemFeature(PackageManager.FEATURE_SIP)!!) {
            tvSIP?.text = mResources.getString(R.string.available)
        } else {
            tvSIP?.text = mResources.getString(R.string.not_supported)
        }

        // SIP based VOIP
        if (packageManager?.hasSystemFeature(PackageManager.FEATURE_SIP_VOIP)!!) {
            tvSIPBasedVOIP?.text = mResources.getString(R.string.available)
        } else {
            tvSIPBasedVOIP?.text = mResources.getString(R.string.not_supported)
        }
    }
}
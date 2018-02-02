package com.quixom.apps.deviceinfo.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.LinearLayoutManager
import android.telephony.TelephonyManager
import android.telephony.gsm.GsmCellLocation
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.quixom.apps.deviceinfo.R
import com.quixom.apps.deviceinfo.adapters.SimAdapter
import com.quixom.apps.deviceinfo.models.SimInfo
import com.quixom.apps.deviceinfo.utilities.KeyUtil
import kotlinx.android.synthetic.main.fragment_sim.*
import kotlinx.android.synthetic.main.toolbar_ui.*
import android.telephony.SubscriptionManager
import com.quixom.apps.deviceinfo.MainActivity


/**
 * A simple [SimFragment] subclass.
 */
class SimFragment : BaseFragment() {

    private var telephonyManager: TelephonyManager? = null
    private var simInfoDataList: ArrayList<SimInfo>? = ArrayList<SimInfo>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_sim, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        telephonyManager = mActivity.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager?

        rvSimData?.layoutManager = LinearLayoutManager(mActivity)
        rvSimData?.hasFixedSize()
        initToolbar()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        checkCameraPermission()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden && isAdded) {
            initToolbar()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    /**
     * this method will show permission pop up messages to user.
     */
    private fun checkCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val hasWriteCameraPermission = mActivity.checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
            if (hasWriteCameraPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.READ_PHONE_STATE), KeyUtil.KEY_READ_PHONE_STATE)
            } else {
                retrieveSimInformation(telephonyManager!!)
            }
        } else {
            retrieveSimInformation(telephonyManager!!)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            KeyUtil.KEY_READ_PHONE_STATE -> if (permissions.isNotEmpty()) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    retrieveSimInformation(telephonyManager!!)
                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.GET_ACCOUNTS)) {
                        Toast.makeText(mActivity, "Need to grant account Permission", Toast.LENGTH_LONG).show()
                    }
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingPermission", "HardwareIds")

    private fun retrieveSimInformation(telephonyManager: TelephonyManager) {
        if (telephonyManager != null && isSimAvailable(mActivity, 0) && telephonyManager.simState == TelephonyManager.SIM_STATE_READY) {

            cvSimDataParent.visibility = View.VISIBLE
            if (llEmptyState.isShown) {
                llEmptyState.visibility = View.GONE
            }

            simInfoDataList?.add(SimInfo("SIM 1 state", simState(telephonyManager.simState)))
            simInfoDataList?.add(SimInfo("Integrated circuit card identifier (ICCID)", telephonyManager.simSerialNumber!!))
            simInfoDataList?.add(SimInfo("Unique device ID (IMEI or MEID/ESN for CDMA)", telephonyManager.getImei(0)))
            simInfoDataList?.add(SimInfo("International mobile subscriber identity (IMSI)", telephonyManager.subscriberId))
            simInfoDataList?.add(SimInfo("Service provider name (SPN)", telephonyManager.simOperatorName))
            simInfoDataList?.add(SimInfo("Mobile country code (MCC)", telephonyManager.networkCountryIso))
            simInfoDataList?.add(SimInfo("Mobile operator name", telephonyManager.networkOperatorName))
            simInfoDataList?.add(SimInfo("Network type", networkType(telephonyManager.networkType)))

            simInfoDataList?.add(SimInfo("Mobile country code + mobile network code (MCC+MNC)", telephonyManager.simOperator))
            simInfoDataList?.add(SimInfo("Mobile station international subscriber directory number (MSISDN)", telephonyManager.line1Number))

            /*  if (isSimAvailable(mActivity, 1)) {
                    simInfoDataList?.add(SimInfo("", ""))
                        simInfoDataList?.add(SimInfo("SIM 2 state", simState(getDeviceIdBySlot(mActivity, "getSimState", 1).toInt())))
                        simInfoDataList?.add(SimInfo("Unique device ID (IMEI or MEID/ESN for CDMA)", telephonyManager.getImei(1)))
                        simInfoDataList?.add(SimInfo("Integrated circuit card identifier (ICCID)", getDeviceIdBySlot(mActivity, "getSimSerialNumber", 1)))
                        simInfoDataList?.add(SimInfo("International mobile subscriber identity (IMSI)", ""+getDeviceIdBySlot(mActivity, "getSubscriberId", 1)))
                        simInfoDataList?.add(SimInfo("Service provider name (SPN)", getDeviceIdBySlot(mActivity, "getSimOperatorName", 1)))
                        simInfoDataList?.add(SimInfo("Mobile country code (MCC)", getDeviceIdBySlot(mActivity, "getNetworkCountryIso", 1)))
                        simInfoDataList?.add(SimInfo("Mobile operator name", ""+getDeviceIdBySlot(mActivity, "getNetworkOperatorName", 1)))
                        simInfoDataList?.add(SimInfo("Network type", networkType(getDeviceIdBySlot(mActivity, "getNetworkType", 1).toInt())))
                        simInfoDataList?.add(SimInfo("Mobile country code + mobile network code (MCC+MNC)", ""+getDeviceIdBySlot(mActivity, "getSimOperator", 1)))
                        simInfoDataList?.add(SimInfo("Mobile station international subscriber directory number (MSISDN)", ""+getDeviceIdBySlot(mActivity, "getLine1Number", 1)))
                }*/

            //creating our adapter
            val adapter = simInfoDataList?.let { SimAdapter(mActivity, it) }
            rvSimData?.adapter = adapter
        } else {
            cvSimDataParent.visibility = View.GONE
            llEmptyState.visibility = View.VISIBLE
        }
    }

    private fun initToolbar() {
        mActivity.iv_menu?.visibility = View.VISIBLE
        mActivity.iv_back?.visibility = View.GONE
        mActivity.tv_title?.text = mResources.getString(R.string.sim)
        mActivity.iv_menu?.setOnClickListener {
            mActivity.openDrawer()
        }
    }

    @Throws(DIMethodNotFoundException::class)
    private fun getDeviceIdBySlot(context: MainActivity, predictedMethodName: String, slotID: Int): String {
        var imei: String? = null
        val telephony = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        try {

            val telephonyClass = Class.forName(telephony.javaClass.name)

            val parameter = arrayOfNulls<Class<*>>(1)
            parameter[0] = Int::class.javaPrimitiveType
            val getSimID = telephonyClass.getMethod(predictedMethodName, *parameter)

            val obParameter = arrayOfNulls<Any>(1)
            obParameter[0] = slotID
            val ob_phone = getSimID.invoke(telephony, *obParameter)

            return ob_phone?.toString() ?: "No record"

        } catch (e: Exception) {
            e.printStackTrace()
            throw DIMethodNotFoundException(predictedMethodName)
        }
    }


    @Throws(DIMethodNotFoundException::class)
    private fun getCellLocBySlot(context: Context, predictedMethodName: String, slotID: Int): GsmCellLocation? {

        var cloc: GsmCellLocation? = null
        val telephony = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        try {
            val telephonyClass = Class.forName(telephony.javaClass.name)
            val parameter = arrayOfNulls<Class<*>>(1)
            parameter[0] = Int::class.javaPrimitiveType
            val getSimID = telephonyClass.getMethod(predictedMethodName, *parameter)

            val obParameter = arrayOfNulls<Any>(1)
            obParameter[0] = slotID
            val obPhone = getSimID.invoke(telephony, *obParameter)

            if (obPhone != null) {
                cloc = obPhone as GsmCellLocation

            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw DIMethodNotFoundException(predictedMethodName)
        }

        return cloc
    }

    private fun simState(simState: Int): String {
        return when (simState) {
            0 -> "UNKNOWN"
            1 -> "ABSENT"
            2 -> "REQUIRED"
            3 -> "PUK_REQUIRED"
            4 -> "NETWORK_LOCKED"
            5 -> "READY"
            6 -> "NOT_READY"
            7 -> "PERM_DISABLED"
            8 -> "CARD_IO_ERROR"
            else -> "??? " + simState
        }
    }

    private fun networkType(simState: Int): String {
        return when (simState) {
            TelephonyManager.NETWORK_TYPE_GPRS -> "GPRS"
            TelephonyManager.NETWORK_TYPE_EDGE -> "EDGE"
            TelephonyManager.NETWORK_TYPE_CDMA -> "CDMA"
            TelephonyManager.NETWORK_TYPE_1xRTT -> "1xRTT"
            TelephonyManager.NETWORK_TYPE_IDEN -> "IDEN"

            TelephonyManager.NETWORK_TYPE_UMTS -> "UMTS"
            TelephonyManager.NETWORK_TYPE_EVDO_0 -> "EVDO 0"
            TelephonyManager.NETWORK_TYPE_EVDO_A -> "EVDO A"
            TelephonyManager.NETWORK_TYPE_HSDPA -> "HSDPA"
            TelephonyManager.NETWORK_TYPE_HSUPA -> "HSUPA"
            TelephonyManager.NETWORK_TYPE_HSPA -> "HSPA"
            TelephonyManager.NETWORK_TYPE_EVDO_B -> "EVDO B"
            TelephonyManager.NETWORK_TYPE_EHRPD -> "EHRPD"
            TelephonyManager.NETWORK_TYPE_HSPAP -> "HSPAP"

            TelephonyManager.NETWORK_TYPE_LTE -> "LTE"
            else -> "Unknown"
        }
    }

    private class DIMethodNotFoundException(info: String) : Exception(info) {
        companion object {
            private val serialVersionUID = -996812356902545308L
        }
    }

    @SuppressLint("MissingPermission", "HardwareIds")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    private fun isSimAvailable(context: MainActivity, slotId: Int): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            val sManager = context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager
            val infoSim = sManager.getActiveSubscriptionInfoForSimSlotIndex(slotId)
            if (infoSim != null) {
                return true
            }
        } else {
            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            if (telephonyManager.simSerialNumber != null) {
                return true
            }
        }
        return false
    }
}

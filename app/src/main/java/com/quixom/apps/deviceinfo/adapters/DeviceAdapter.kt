package com.quixom.apps.deviceinfo.adapters

import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.quixom.apps.deviceinfo.MainActivity
import com.quixom.apps.deviceinfo.R
import com.quixom.apps.deviceinfo.models.DeviceInfo
import com.quixom.apps.deviceinfo.utilities.Methods


/**
 * Created by akif on 9/21/17.
 */

class DeviceAdapter(internal var flag: Int?, internal var appslist: ArrayList<DeviceInfo>, internal var mActivity: MainActivity) : RecyclerView.Adapter<DeviceAdapter.DeviceVH>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DeviceAdapter.DeviceVH {
        val itemView = LayoutInflater.from(mActivity).inflate(R.layout.row_infomation, parent, false)
        return DeviceVH(itemView)
    }

    override fun onBindViewHolder(holder: DeviceAdapter.DeviceVH?, position: Int) {
        holder?.bindData(appslist[position], mActivity)
    }

    override fun getItemCount(): Int {
        return appslist.size
    }

    inner class DeviceVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(deviceInfo: DeviceInfo, c: MainActivity) {

            val ivAppLogo: ImageView? = itemView.findViewById(R.id.iv_app_icon)
            val tvAppname: TextView? = itemView.findViewById(R.id.tv_app_name)
            val tvAppPackageName: TextView? = itemView.findViewById(R.id.tv_app_package_name)

            tvAppname?.text = deviceInfo.appLable
            tvAppPackageName?.text = deviceInfo.packageName
            ivAppLogo?.setImageDrawable(deviceInfo.appLogo)

            itemView.setOnClickListener({
                Methods.avoidDoubleClicks(itemView)

                val launchIntent = mActivity.packageManager.getLaunchIntentForPackage(deviceInfo.packageName)
                if (launchIntent != null) {
                    startActivity(mActivity, launchIntent, null)
                }
            })
        }
    }
}
